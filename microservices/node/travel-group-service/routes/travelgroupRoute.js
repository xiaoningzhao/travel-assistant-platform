const express = require("express");
const {
  createTravegroup,
  addMemberToTravelgroup,
  addManagerToTravelgroup,
  changeOwnership,
  downgradeManager,
  suspendTravelgroup,
  deleteTravelgroup,
  deleteMember,
  getAllTravelgroups,
  getSingleTravelgroup,
  getTravelgroupsUserIdIn,
  uploadImageToTravelgroup,
  updateGroupInfo,
} = require("../controllers/travelgroupController");
const { route } = require("./travelplanRoute");

const router = express.Router();
//
router.route("/read/:id").get(getSingleTravelgroup);

///read
router.route("/read").get(getAllTravelgroups);
router.route("/read/groups_in/:userId").get(getTravelgroupsUserIdIn);

router.route("/create/:userId").post(createTravegroup);

router
  .route("/update/addmember/:userId/:groupId/:memberId")
  .put(addMemberToTravelgroup);
router
  .route("/update/addmanager/:userId/:groupId/:managerId")
  .put(addManagerToTravelgroup);
router
  .route("/update/groupowner/:userId/:groupId/:newOwnerId")
  .put(changeOwnership);
router
  .route("/update/downgrademanager/:userId/:groupId/:managerId")
  .put(downgradeManager);

router.route("/updateimage/:userId/:groupId").put(uploadImageToTravelgroup);

//update/suspend/:groupId
router.route("/update/suspend/:groupId").put(suspendTravelgroup);

//update/close/:userId/:groupId
router.route("/update/close/:userId/:groupId").delete(deleteTravelgroup);

//delete/:userId/:groupId/:deleteUserId
router.route("/delete/:userId/:groupId/:deleteUserId").delete(deleteMember);
router.route("/updateinfo/:userId/:groupId").put(updateGroupInfo);

module.exports = router;
