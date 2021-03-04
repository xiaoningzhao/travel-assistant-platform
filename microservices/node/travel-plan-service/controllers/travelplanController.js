const ErrorResponse = require('../utils/errorResponse');
const asyncHandler = require('../middleware/async');
const Travelplan = require('../models/travelplan');
const path = require('path');



//@desc Get all travelplans
//@route GET /api/v1/travelplan/read
//@access Private
exports.getAllTravelplans = asyncHandler(async (req, res, next) => {
    const travelplans = await Travelplan.find();
    
    if (!travelplans || travelplans.length == 0) {
        return next(
            new ErrorResponse(
                'No Travelplans found',
                404
            )
        );
    }
    res.status(200).json({
        success: true,
        count: travelplans.length,
        data: travelplans
    })
});

//@desc Get all travelplans belonging to a travelgroup with id
//@route GET /api/v1/travelplan/read/plans_in/:groupId
//@access Private
exports.getAllTravelplansOfTravelgroup = asyncHandler(async (req, res, next) => {
    const travelplans = await Travelplan.find({ travelGroup: req.params.groupId });
                                                                       
    if (!travelplans) {
        return next(
            new ErrorResponse(
                'No Travelplans found',
                404
            )
        )
    }
    res.status(200).json({
        success: true,
        count: travelplans.length,
        data: travelplans
    })
});


//@desc Get all travelplans created by a user with userId
//@route GET /api/v1/travelplan/read/plans_createdby/:userId
//@access Private
exports.getAllTraveplansOfUser = asyncHandler(async (req, res, next) => {
    const travelplans = await Travelplan.find({ initiator: req.params.userId });
    
    if (!travelplans || travelplans.length == 0) {
        return next(
            new ErrorResponse(
                'No Travelplans found',
                404
            )
        )
    }
    res.status(200).json({
        success: true,
        count: travelplans.length,
        data: travelplans
    })
})


//@desc Add to a travelPlan by user with userId
//@route POST /api/v1/travelplan/create/:userId
//@access Private
exports.addTravelplan = asyncHandler(async (req, res, next) => {
    req.body.initiator = req.params.userId;
    const travelplan = await Travelplan.create(req.body);
    res.status(201).json({
        success: true,
        message:'New travelplan is added',
        data: travelplan
    })
});

//@desc Update a travelplan with planId by a user with userId
//@route PUT /api/v1/travelplan/update/:userId/:planId
//@access Private
exports.updateTravelplan = asyncHandler(async (req, res, next) => {
    let travelplan = await Travelplan.find(
        {
            _id: req.params.planId,
            initiator: req.params.userId
        }
    );
    if (!travelplan || travelplan.length == 0) {
        return next(
            new ErrorResponse(
                `No travelplan found with planId ${req.params.id} and userId ${req.params.userId}`
            )
        )
    }

    travelplan = await Travelplan.findByIdAndUpdate(req.params.planId, req.body, {
        new: true,
        runValidators: true
    });

    //travelplan.save();

    res.status(200).json({
        success: true,
        message:'Travelplan is updtated',
        data: travelplan
    });
    

});

//@desc Delete a travelplan with planId by a user with userId
//@route DELETE /api/v1/travelplan/delete/:userId/:planId
//@access Private

exports.deleteTravelplan = asyncHandler(async (req, res, next) => {
    const travelplan = await Travelplan.find(
        {
            _id: req.params.planId,
            initiator: req.params.userId
        }
    );
    if (!travelplan || travelplan.length == 0) {
        return next(
            new ErrorResponse(
                `No travelplan found with planId ${req.params.id} and userId ${req.params.userId}`,
                404
            )
        )
    }

    await Travelplan.findByIdAndDelete(req.params.planId);
    res.status(200).json({
        success: true,
        message: 'Delete a travelplan is completed'
    });
});

//@desc Like a travelplan
//@route PUT /api/v1/travelplan/like/:userId/:planId
//@access Private
exports.likeTravelplan = asyncHandler(async (req, res, next) => {
    let travelplan = await Travelplan.findById(req.params.planId);
    if (!travelplan) {
        return next(
            new ErrorResponse(
                `No travelplan with planId ${req.params.planId} found`,
                404
            )
        )
    }
    // const travelgroupId = travelplan.travelGroup;
    // const travegroup = await Travelgroup.findById(travelgroupId);
    // // Only group memebrs can like a travelplan or dislike a travelplan
    // if (!travegroup || !travegroup.groupMembers.includes(req.params.userId)) {
    //     return next(
    //         new ErrorResponse(
    //             `User with userId ${req.params.userId} is not authoried`,
    //             401
    //         )
    //     )
    // }
    if (travelplan.dislikes.includes(req.params.userId)) {
        const idxToRemove = travelplan.dislikes.indexOf(req.params.userId);
        travelplan.dislikes.splice(idxToRemove, 1);
        await travelplan.save();
    }

    if (!travelplan.likes.includes(req.params.userId)) {
        travelplan.likes.push(req.params.userId);
        await travelplan.save();
        res.status(200).json({
            success: true,
            data: travelplan
        })
        // req.body.likes = travelplan.likes;
        // travelplan = await Travelplan.findByIdAndUpdate()

    } else {
        res.status(400).json({
            success: false,
            message: 'You already liked this travlplan'
        })
    }

});


//@desc Dislike a travelplan
//@route PUT /api/v1/travelplan/dislike/:userId/:planId
//@access Private
exports.disLikeTravelplan = asyncHandler(async (req, res, next) => {
    let travelplan = await Travelplan.findById(req.params.planId);
    if (!travelplan) {
        return next(
            new ErrorResponse(
                `No travelplan with planId ${req.params.planId} found`,
                404
            )
        )
    }
    // const travelgroupId = travelplan.travelGroup;
    // const travegroup = await Travelgroup.findById(travelgroupId);
    // if (!travegroup.groupMembers.includes(req.params.userId)) {
    //     return next(
    //         new ErrorResponse(
    //             `User with userId ${req.params.userId} is not authoried`,
    //             401
    //         )
    //     )
    // }
    if (travelplan.likes.includes(req.params.userId)) {
        const idxToRemove = travelplan.likes.indexOf(req.params.userId);
        travelplan.likes.splice(idxToRemove, 1);
        await travelplan.save();
    }

    if (!travelplan.dislikes.includes(req.params.userId)) {
        travelplan.dislikes.push(req.params.userId);
        await travelplan.save();
        res.status(200).json({
            success: true,
            data: travelplan
        })
        // req.body.likes = travelplan.likes;
        // travelplan = await Travelplan.findByIdAndUpdate()

    } else {
        res.status(400).json({
            message: 'User already disliked this travelplan'
        })
    }

});

//@desc Unlike a travelplan
//@route PUT /api/v1/travelplan/unlike/:userId/:planId
//@access Private
exports.unLikeTravelplan = asyncHandler(async (req, res, next) => {
    let travelplan = await Travelplan.findById(req.params.planId);
    if (!travelplan) {
        return next(
            new ErrorResponse(
                `No travelplan with planId ${req.params.planId} found`,
                404
            )
        )
    }


    if (travelplan.likes.includes(req.params.userId)) {
        const idxToRemove = travelplan.likes.indexOf(req.params.userId);
        travelplan.likes.splice(idxToRemove, 1);
        await travelplan.save();
        res.status(200).json({
            success: true,
            data: travelplan
        })
    } else {
        res.status(400).json({
            message: 'User has not like this travelplan'
        })
    }

});

//@desc Undislike a travelplan
//@route PUT /api/v1/travelplan/undislike/:userId/:planId
//@access Private
exports.unDislikeTravelplan = asyncHandler(async (req, res, next) => {
    let travelplan = await Travelplan.findById(req.params.planId);
    if (!travelplan) {
        return next(
            new ErrorResponse(
                `No travelplan with planId ${req.params.planId} found`,
                404
            )
        )
    }


    if (travelplan.dislikes.includes(req.params.userId)) {
        const idxToRemove = travelplan.dislikes.indexOf(req.params.userId);
        travelplan.dislikes.splice(idxToRemove, 1);
        await travelplan.save();
        res.status(200).json({
            success: true,
            data: travelplan
        })
    } else {
        res.status(400).json({
            message: 'User has not like this travelplan'
        })
    }

});

//@desc Upload image for a travelplan
//@route PUT /api/v1/travelplan/updateimage/:userId/:planId
//@access Private

exports.uploadImageToTravelplan = asyncHandler(async (req, res, next) => {
    const travelplan = await Travelplan.find(
        {
            _id: req.params.planId,
            initiator: req.params.userId
        }
    );
    if (!travelplan || travelplan.length == 0) {
        return next(
            new ErrorResponse(
                `No travelplan found with planId ${req.params.id} and userId ${req.params.userId}`
            )
        )
    }
    if (!req.files) {
        return next(new ErrorResponse('Please upload a file', 400)); 
    }

    const file = req.files.file;

    if (!file.mimetype.startsWith('image')) {
        return next(new ErrorResponse('Plese upload an image file', 400));
    }
    //check file size
    if (file.size > process.env.MAX_FILE_UPLOAD) {
      return next(
      new ErrorResponse(
        `Please upload an image less than ${process.env.MAX_FILE_UPLOAD}`,
        400
      )
    );
    }
    

    file.name = 'image_' + `${path.parse(file.name).name}` + Date.now().toString() + `${path.parse(file.name).ext}`;

    file.mv(`${process.env.FILE_UPLOAD_PATH}/${file.name}`, async err => {
    if (err) {
      console.error(err);
      return next(new ErrorResponse(`Problem with file upload`, 500));
    }

    await Travelplan.findByIdAndUpdate(req.params.planId, { image: file.name });

    res.status(200).json({
      success: true,
      data: file.name
    });
  });


});
