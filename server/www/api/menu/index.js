const express = require('express');
const router = express.Router();
const controller = require('./controller')

router.post('/join', controller.create);
router.post('/dbtest', controller.dbtest);
router.post('/remove', controller.remove);
router.post('/modify', controller.modify);
router.get('/getemailmenu', controller.getemailmenu);


module.exports = router;