const mongoose = require('mongoose');

const travelGroupSchema = new mongoose.Schema({
    groupName: {
        type: String,
        require: [true, 'Please add a name for the group'],
        trim: true,
        maxLength:[50, 'groupName can not be more than 50 characters']
    },
    groupImage: {
        type: String,
        default: 'no-image.jpg'
    },
    groupMembers: {
        type: [mongoose.Schema.ObjectId],
        ref: 'User'
    },
    groupManagers: {
        type: [mongoose.Schema.ObjectId],
        ref: 'User'
    },
    groupOwner: {
        type: mongoose.Schema.ObjectId,
        ref: 'User'
    },
    travelPlans: {
        type: [mongoose.Schema.ObjectId],
        ref: 'Travelplan'
    },
    status: {
        // 0 indicates group is suspended
        // 1 indiccates group is active
        type: Number,
    }

},
    {
        timestamps: true
   } 
);

module.exports = mongoose.model('Travelgroup', travelGroupSchema);
