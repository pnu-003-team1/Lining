const express = require('express');
const router = express.Router();
const controller = require('./controller')

router.post('/add', controller.addGuest);
router.post('/dbtest', controller.dbtest);
router.post('/remove', controller.remove);
router.post('/deleteDB', controller.deleteAll);
router.post('/checkRes', controller.isRes);

module.exports = router;