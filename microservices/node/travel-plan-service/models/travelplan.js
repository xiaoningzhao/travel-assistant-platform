const mongoose = require("mongoose");
const geocoder = require("../utils/geocoder");

const travelplanSchema = new mongoose.Schema(
  {
    planName: {
      type: String,
      reuqired: [true, "Please add a name"],
      trim: true,
      maxLength: [50, "planName can not be more than 50 characters"],
    },
    image: {
      type: String,
      default: "no-image.jpg",
    },

    planDescription: {
      type: String,
      trim: true,
    },
    startDate: Date,
    endDate: Date,
    cancelledDate: Date,
    travelMembers: {
      type: [Number],
    },
    initiator: {
      type: Number,
    },
    travelGroup: {
      type: String,
    },
    likes: {
      type: [Number],
    },
    dislikes: {
      type: [Number],
    },
    //0 denotes cancelled
    //1 denotes plan is ongoing
    //2 denotes plan is completed

    //3 denotes plan is created
    status: {
      type: Number,
      default: 3,
    },
    comments: [
      {
        user: {
          type: Number,
        },
        text: {
          type: String,
          required: [true, "Please add your comment"],
        },
        name: {
          type: String,
        },
        date: {
          type: Date,
          default: Date.now,
        },
      },
    ],
    departureAddress: {
      type: String,
      trim: true,
    },
    destinationAddress: [String],
  },
  {
    timestamps: true,
  }
);

//Reverse populate departure address with virtuals
// travelplanSchema.virtual('departureAddress', {
//     ref: 'Address',
//     localField: '_id',
//     foreignField: 'travelPlan',

//     justOne: false,
//     options:{addressType: 'Departure'}

// });

//Reverse populate destination addresses with virtuals
// travelplanSchema.virtual('destinationAddresses', {
//     ref: 'Address',
//     localField: '_id',
//     foreignField: 'travelPlan',
//     justOne: false,
//     options:{addressType: 'Destination'}

// });

//Reverse populate destination addresses with virtuals
// travelplanSchema.virtual('comments', {
//     ref: 'Comment',
//     localField: '_id',
//     foreignField: 'travelPlan',
//     justOne: false,
//     options: {sort:{createdAt: - 1}}
// })

module.exports = mongoose.model("Travelplan", travelplanSchema);
