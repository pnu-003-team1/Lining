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
<<<<<<< HEAD
router.get('/getemailbuser', controller.getemailbuser);
=======
>>>>>>> e573b55de599c675ada67ac7bcd93b8fcb2cb3e2

module.exports = router;