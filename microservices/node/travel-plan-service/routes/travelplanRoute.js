const express = require("express");
const {
  getAllTravelplans,
  getAllTravelplansOfTravelgroup,
  getAllTraveplansOfUser,
  addTravelplan,
  updateTravelplan,
  deleteTravelplan,
  likeTravelplan,
  disLikeTravelplan,
  unLikeTravelplan,
  unDislikeTravelplan,
  uploadImageToTravelplan,
  getSingleTravelPlan,

  createOngoingTravelplan,
  updateOngoingTravelplan,
  deleteOngoingTravelplan,
} = require("../controllers/travelplanController");

const router = express.Router();
router.route("/read/:planId").get(getSingleTravelPlan);
//GET
router.route("/read").get(getAllTravelplans);

router.route("/read/plans_in/:groupId").get(getAllTravelplansOfTravelgroup);

router.route("/read/plans_createdby/:userId").get(getAllTraveplansOfUser);

router.route("/create/:userId").post(addTravelplan);

router.route("/update/:userId/:planId").put(updateTravelplan);

router.route("/delete/:userId/:planId").delete(deleteTravelplan);

router.route("/like/:userId/:planId").put(likeTravelplan);

router.route("/dislike/:userId/:planId").put(disLikeTravelplan);

router.route("/unlike/:userId/:planId").put(unLikeTravelplan);

router.route("/undislike/:userId/:planId").put(unDislikeTravelplan);

router.route("/updateimage/:userId/:planId").put(uploadImageToTravelplan);

//for position sharing
//router.route("/position/create/:userId/:planId").post(addPositionSharing);

//router
//  .route("/position/update/:userId/:planId/:lat/:lng")
//  .put(updatePositionSharing);

//router.route("/position/read/:planId").get(getUserPositionsOfTravelPlan);

//router.route("/position/delete/:userId/:planId").delete(deletePositionSharing);

router.route("/ongoing/:userId/:planId").post(createOngoingTravelplan);

router.route("/ongoing/:userId/:planId").put(updateOngoingTravelplan);

router.route("/ongoing/:userId/:planId").delete(deleteOngoingTravelplan);

module.exports = router;
