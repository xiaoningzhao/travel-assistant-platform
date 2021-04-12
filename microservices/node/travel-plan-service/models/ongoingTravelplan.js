const mongoose = require("mongoose");

const ongoingTravelplanSharingSchema = new mongoose.Schema({
  planId: String,
  userId: Number,
  lat: Number,
  lng: Number,
});

module.exports = mongoose.model(
  "OngoingTravelplan",
  ongoingTravelplanSharingSchema
);
