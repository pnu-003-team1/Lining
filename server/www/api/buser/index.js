const express = require('express');
const router = express.Router();
const controller = require('./controller')

router.post('/join', controller.create);
router.post('/login', controller.login);
router.post('/repetition', controller.checkRep);
router.post('/dbtest', controller.dbtest);
router.post('/fullCheck', controller.fullCheck);
router.post('/removeall', controller.removeall);
router.post('/remove', controller.remove);
router.post('/modify', controller.modify);
router.get('/allbuserList', controller.getallbuserList);
router.get('/getemailbuser', controller.getemailbuser);

module.exports = router;