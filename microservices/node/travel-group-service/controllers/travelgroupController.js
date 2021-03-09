const ErrorResponse = require('../utils/errorResponse');
const asyncHandler = require('../middleware/async');
const Travelgroup = require('../models/travelgroup');
const User = require('../models/User');
const mongoose = require('mongoose');


//@desc Get all travelgroups
//@route GET /api/v1/travelgroup/read
//@access Private
exports.getAllTravelgroups = asyncHandler(async (req, res, next) => {
    const travelgroups = await Travelgroup.find().sort({createdAt: -1});
    if (!travelgroups) {
        return next(
            new ErrorResponse('No travelgroups found', 404)
        );
    }
    //travelgroups.sort('-createdAt');
    res.status(200).json(
        {
            success: true,
            count: travelgroups.length,
            data: travelgroups
        });

});

//@desc Get single travelgroup by id
//@route GET v1/travelgroup/read/:id
//@access Private
exports.getSingleTravelgroup = asyncHandler(async (req, res, next) => {
    const travelgroup = await Travelgroup.findById(req.params.id);
    if (!travelgroup) {
        return next(
            new ErrorResponse(
                `Travelgroup not found with id of ${req.params.id}`,
                400
            )
        );
    }
    res.status(200).json({ success: true, data: travelgroup });
});


//@desc Get travelgroups that userId is in
//@route GET v1/travelgroups/read/groups_in/:userId
//@access Private
exports.getTravelgroupsUserIdIn = asyncHandler(async (req, res, next) => {

    //Firstly, check if userId is a valid id
    const user = await User.findById(req.params.userId);
    if (!user) {
        new ErrorResponse(
                `The user with Id ${req.params.userId} is not a valid userId`,
                400
            )
    }

    const travelgroups = await Travelgroup.find();
    if (!travelgroups || travelgroups.length == 0) {
        return next(
            new ErrorResponse('No travelgroups found')
        );
    }
    let isInMembers;
    let isInManagers;
    const travelgroupsByUserId = travelgroups.filter(tp => {
        //Check if userId in groupMembers
        if (tp.groupMembers) {
            isInMembers = tp.groupMembers.includes(req.params.userId);
        }
        //Check if userId in groupManagers2
        if (tp.groupManagers) {
            isInManagers = tp.groupManagers.includes(req.params.userId);
        }
        return isInMembers || isInManagers || (req.params.userId === tp.groupOwner.toString());
    });
    if (!travelgroupsByUserId || travelgroupsByUserId.length == 0) {
        return next(
            new ErrorResponse(`No travelgroups found with userId of ${req.params.userId}`)
        );
    }
    res.status(200).json(
        {
            success: true,
            count: travelgroupsByUserId.length,
            data: travelgroupsByUserId
        });

});


//@desc Get travelgroups that userId is not in
//@route GET v1/travelgroups/read/groups_notin/:userId
//@access Private
exports.getAllTravelgroupsUserIdNotIn = asyncHandler(async (req, res, next) => {
    const travelgroups = await Travelgroup.find().sort({createdAt: -1});
    if (!travelgroups) {
        return next(
            new ErrorResponse('No travelgroups found')
        );
    }
    const user = await User.findById(req.params.userId);
    if (!user) {
        new ErrorResponse(
                `The user with Id ${req.params.userId} is not a valid userId`,
                400
            )
    }
    let isInMembers;
    let isInManagers;
    const travelgroupsNotIn = travelgroups.filter(tp => {
        if (tp.groupMembers) {
            isInMembers = tp.groupMembers.includes(mongoose.Types.ObjectId(req.params.userId));
        }
        if (tp.groupManagers) {
            isInManagers = tp.groupManagers.includes(mongoose.Types.ObjectId(req.params.userId));
        }
        !isInManagers && !isInMembers && (tp.groupOwner.toString() !== req.params.userId);
    });
    if (!travelgroupsNotIn) {
        return next(
            new ErrorResponse(`No travelgroups found`)
        );
    }
    res.status(200).json({ success: true, data: travelgroupsNotIn });
})

//@desc Add a travelgroup
//@route POST v1/travelgroups/create/:userId
//@access Private
exports.createTravegroup = asyncHandler(async (req, res, next) => {
    const user = await User.findById(req.params.userId);
    if (!user) {
        return next(
            new ErrorResponse(
                `The user with UserId ${req.params.userId} is not a valid userId`,
                400
            )
        );
    }
    req.body.groupOwner = req.params.userId;
    // 1 indicates active
    // 0 indicates inactive
    req.body.status = 1;
    const travelgroup = await Travelgroup.create(req.body);
    res.status(201).json({
        success: true,
        data: travelgroup
    })
});

//@desc Add a member
//@route PUT v1/travelgroups/update/addmember/:userId/:groupId/:memberId
//@access groupOwer or groupManagers
exports.addMemberToTravelgroup = asyncHandler(async (req, res, next) => {

    if (req.params.userId === req.params.memberId) {
        return next(
            new ErrorResponse(
                'Duplicate userId',
                400
            )
        )
    }
    const user = await User.findById(req.params.userId);
   
    if (!user) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.userId} is not a valid userId`,
                400
            )
        );
    }
    const member = await User.findById(req.params.memberId);
    if (!member) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.memberId} is not a valid userId`,
                400
            )
        );
    }


    let travelgroup = await Travelgroup.findById(req.params.groupId);
    if (!travelgroup) {
        return next(
            new ErrorResponse(
                `The travelgroup with Id ${req.params.groupId} is not a valid groupId`,
                400
            )
        )
    }

    let isMemberIdInGroup;
    let isManagerOrOwner;
    isManagerOrOwner = travelgroup.groupOwner.toString() === req.params.userId || travelgroup.groupManagers.includes(mongoose.Types.ObjectId(req.params.userId));
    isMemberIdInGroup = travelgroup.groupMembers.includes(mongoose.Types.ObjectId(req.params.memberId)) || travelgroup.groupManagers.includes(mongoose.Types.ObjectId(req.params.memberId));

    //firstly, check if userId is in the groupManagers or groupOwner
    if (!isManagerOrOwner) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.userId} doesn't have the authority to add members`,
                401
            )
        )
    }
    if (isMemberIdInGroup) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.memberId} is already in the group`,
                400
            )
        )
    }

    
    const groupMembers = travelgroup.groupMembers;
    groupMembers.push(mongoose.Types.ObjectId(req.params.memberId));
    req.body.groupMembers = groupMembers;
    await Travelgroup.findByIdAndUpdate(req.params.groupId, req.body, {
        new: true,
        runValidators: true
    });
    res.status(200).json({ success: true });

});

//@desc Downgrade a manager
//@route PUT v1/travelgroups/update/downgrademanager/:userId/:groupId/:managerId
//@access groupOwer
exports.downgradeManager = asyncHandler(async (req, res, next) => {

    
    if (req.params.userId === req.params.managerId) {
        return next(
            new ErrorResponse(
                'Duplicate userId',
                400
            )
        )
    }

    const user = await User.findById(req.params.userId);
    if (!user) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.userId} is not a valid userId`,
                400
            )
        );
    }

    const manager = await User.findById(req.params.managerId);
    if (!manager) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.managerId} is not a valid userId`,
                400
            )
        );
    }

    const travelgroup = await Travelgroup.findById(req.params.groupId);
    if (!travelgroup) {
        return next(
            new ErrorResponse(
                `The travelgroup with Id ${req.params.groupId} is not a valid groupId`,
                400
            )
        )
    }

    let isOwner;
    let isManager;
    isOwner = travelgroup.groupOwner.toString() === req.params.userId;
    isManager = travelgroup.groupManagers.includes(mongoose.Types.ObjectId(req.params.managerId));
    if (!isOwner) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.userId} doesn't have the authority to add members`,
                401
            )
        )
    }

    if (!isManager) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.managerId} is not a manager of this travegroup`,
                400
            )
        )
    }

    const managers = travelgroup.groupManagers;

    req.body.groupManagers = managers.filter(m =>
        m.toString() !== req.params.managerId);


    const members = travelgroup.groupMembers;
    members.push(req.params.managerId);
    req.body.groupMembers = members;
    await Travelgroup.findByIdAndUpdate(req.params.groupId, req.body, {
        new: true,
        runValidators: true
    });
    
    res.status(200).json({ success: true });
})


//@desc Add a manager or upgrade a member to manager
//@route PUT v1/travelgroups/update/addmanager/:userId/:groupId/:managerId
//@access groupOwner
exports.addManagerToTravelgroup = asyncHandler(async (req, res, next) => {
     if (req.params.userId === req.params.managerId) {
        return next(
            new ErrorResponse(
                'Duplicate userId',
                400
            )
        )
    }
    const user = await User.findById(req.params.userId);
    let isGroupowner = false;
    let isManagerIdInMembers = false;
    let isManagerIdInManagers = false;
    if (!user) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.userId} is not a valid userId`,
                400
            )
        );
    }
    const manager = await User.findById(req.params.managerId);
    if (!manager) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.managerId} is not a valid userId`,
                400
            )
        );
    }


    const travelgroup = await Travelgroup.findById(req.params.groupId);
    if (!travelgroup) {
        return next(
            new ErrorResponse(
                `The travelgroup with Id ${req.params.groupId} is not a valid groupId`,
                400
            )
        )
    }

    isGroupowner = travelgroup.groupOwner.toString() === req.params.userId;
    isManagerIdInMembers = travelgroup.groupMembers.includes(mongoose.Types.ObjectId(req.params.managerId));
    isManagerIdInManagers = travelgroup.groupManagers.includes(mongoose.Types.ObjectId(req.params.managerId));

    //Firstly, check if userId is in groupOwner
    if (!isGroupowner) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.userId} doesn't have the authority to add members`,
                401
            )
        )
    }
    //Secondly, check if managerId waiting to be added is alreay a manager of this group
    if (isManagerIdInManagers) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.managerId} is already a manager of this group`,
                400
            )
        )
    }

    // If managerId is in groupMembers, remove this id from the groupMembers
    if (isManagerIdInMembers) {
        //let members = [...travelgroup.groupMembers];
        const members = travelgroup.groupMembers.filter(m => {
            return m.toString() !== req.params.managerId; 
        });
        req.body.groupMembers = members;
    }

    //Add managerId to groupManagers
    let managers = [...travelgroup.groupManagers];
    managers.push(mongoose.Types.ObjectId(req.params.managerId));
    req.body.groupManagers = [...managers];
    await Travelgroup.findByIdAndUpdate(req.params.groupId, req.body, {
        new: true,
        runValidators: true
    });


    res.status(200).json({ success: true });
});


//@desc Change owership
//@route PUT v1/travelgroups/update/groupowner/:userId/:groupId/:newOwnerId
//@access Private

exports.changeOwnership = asyncHandler(async (req, res, next) => {
    if (req.params.userId === req.params.newOwnerId) {
        return next(
            new ErrorResponse(
                'Duplicate userId',
                400
            )
        )
    }
    const user = await User.findById(req.params.userId);
    let isGroupowner = false;
    let isNewOwnerIdInMembers = false;
    let isNewOwnerIdInManagers = false;
    if (!user) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.userId} is not a valid userId`,
                400
            )
        );
    }
    const newOwner = await User.findById(req.params.newOwnerId);
    if (!newOwner) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.newOwnerId} is not a valid userId`,
                400
            )
        );
    }


    const travelgroup = await Travelgroup.findById(req.params.groupId);
    if (!travelgroup) {
        return next(
            new ErrorResponse(
                `The travelgroup with Id ${req.params.groupId} is not a valid groupId`,
                400
            )
        )
    }

    isGroupowner = travelgroup.groupOwner.toString() === req.params.userId;
    isNewOwnerIdInMembers = travelgroup.groupMembers.includes(mongoose.Types.ObjectId(req.params.newOwnerId));
    isNewOwnerIdInManagers = travelgroup.groupManagers.includes(mongoose.Types.ObjectId(req.params.newOwnerId));

    //Check if userId === groupOwner
    if (!isGroupowner) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.userId} doesn't have the authority to change ownership`,
                401
            )
        )
    }
    //Check if managerId waiting to be added is a manager of this group
    if (isNewOwnerIdInManagers) {
        const managers = travelgroup.groupManagers.filter(m => {
            return m.toString() !== req.params.newOwnerId;
        });
        req.body.groupManagers = managers;
        
    }

    if (isNewOwnerIdInMembers) {
        const members = travelgroup.groupMembers.filter(m => {
           return m.toString() !== req.params.newOwnerId;
        });
        req.body.groupMembers = members;
    }
    req.body.groupOwner = req.params.newOwnerId;
    await Travelgroup.findByIdAndUpdate(req.params.groupId, req.body, {
        new: true,
        runValidators: true
    });

    res.status(200).json({ success: true });
    
});

//@desc Delete a member or manager
//@route DELETE v1/travelgroups/delete/:userId/:groupId/:deleteUserId
//@access groupManager and groupOwner
exports.deleteMember = asyncHandler(async (req, res, next) => {
    if (req.params.userId === req.params.newOwnerId) {
        return next(
            new ErrorResponse(
                'Duplicate userId',
                400
            )
        )
    }
    const user = await User.findById(req.params.userId);
    if (!user) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.userId} is not a valid userId`,
                400
            )
        );
    }

    const travelgroup = await Travelgroup.findById(req.params.groupId);
    if (!travelgroup) {
        return next(
            new ErrorResponse(
                `The travelgroup with Id ${req.params.groupId} is not a valid groupId`,
                400
            )
        )
    }

    let isOwner;
    let isManager
    isOwner = travelgroup.groupOwner.toString() === req.params.userId;
    //if(travelgroup.groupManagers)
    isManager = travelgroup.groupManagers.includes(mongoose.Types.ObjectId(req.params.userId));

    //Check if user is a manager or owner
    if (!isOwner && !isManager) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.userId} doesn't have the authority to delete a member`,
                401
            )
        )
    }

    let isDeleteUserIdInManagers = travelgroup.groupManagers.includes(mongoose.Types.ObjectId(req.params.deleteUserId));
    let isDeleteUserIdInMembers = travelgroup.groupMembers.includes(mongoose.Types.ObjectId(req.params.deleteUserId));

    if (isDeleteUserIdInMembers) {
        //If user waiting to be deleted is a member, then just delete it
        const members = travelgroup.groupMembers.filter(m => {
            return m.toString() !== req.params.deleteUserId
        });
        req.body.groupMembers = members;
        await Travelgroup.findByIdAndUpdate(req.params.groupId, req.body, {
            new: true,
            runValidators: true
        })
    } else if (isDeleteUserIdInManagers && isOwner) {
        //If user waiting to be deleted is a manager
        //Make sure only groupOwner can delete a manager
        const managers = travelgroup.groupManagers.filter(m => {
            return m.toString() !== req.params.deleteUserId
        });
        req.body.groupManagers = managers;
        await Travelgroup.findByIdAndUpdate(req.params.groupId, req.body, {
            new: true,
            runValidators: true
        })
    } else if (isDeleteUserIdInManagers && !isOwner) {
        //Make sure only groupOwner can delete a manager
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.userId} doesn't have the authority to delete a member`,
                401
            )
        )
    } else {
        return next(
            new ErrorResponse(
                `User with Id ${req.params.deleteUserId} is not in the group`,
                400
            )
        )
    }

    res.status(200).json({ success: true });
});

//@desc Delete a group
//@route DELETE v1/travelgroups/update/close/:userId/:groupId
//@access group owner
exports.deleteTravelgroup = asyncHandler(async (req, res, next) => {
    const user = await User.findById(req.params.userId);
    if (!user) {
        return next(
            new ErrorResponse(
                `The user with Id ${req.params.userId} is not a valid userId`,
                400
            )
        );
    }

    const travelgroup = await Travelgroup.findById(req.params.groupId);
    if (!travelgroup) {
        return next(
            new ErrorResponse(
                `The travelgroup with Id ${req.params.groupId} is not a valid groupId`,
                400
            )
        )
    }
    //req.body.groupStatus = 0;
    await Travelgroup.findByIdAndDelete(req.params.groupId);
    res.status(200).json({ success: true });
});

//@desc suspend a group
//@route PUT v1/travelgroups/update/suspend/:groupId
//@access admin user
exports.suspendTravelgroup = asyncHandler(async (req, res, next) => {
    const travelgroup = await Travelgroup.findById(req.params.groupId);
    if (!travelgroup) {
        return next(
            new ErrorResponse(
                `The travelgroup with Id ${req.params.groupId} is not a valid groupId`,
                400
            )
        )
    }

    req.body.status = 0;
    await Travelgroup.findByIdAndUpdate(req.params.groupId, req.body, {
        new: true,
        runValidators: true
    });

    res.status(200).json({ success: true });
})


















