const mongoose = require("mongoose");

const travelGroupSchema = new mongoose.Schema(
  {
    groupName: {
      type: String,
      require: true,
      trim: true,
      maxLength: [50, "groupName can not be more than 50 characters"],
    },
    groupImage: {
      type: String,
      default: "no-image.jpg",
    },
    groupMembers: {
      type: [Number],
    },
    groupManagers: {
      type: [Number],
    },
    groupOwner: {
      type: Number,
    },
    travelPlans: {
      type: String,
    },
    status: {
      // 0 indicates group is suspended
      // 1 indiccates group is active
      type: Number,
      default: 1,
    },
  },
  {
    timestamps: true,
  }
);

module.exports = mongoose.model("Travelgroup", travelGroupSchema);
