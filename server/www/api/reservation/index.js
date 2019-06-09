const express = require('express');
const router = express.Router();
const controller = require('./controller')

router.get('/myres', controller.myRes);
router.get('/remain', controller.remain);
router.get('/dbtest', controller.dbtest);

router.post('/add', controller.addGuest);
router.post('/remove', controller.remove);
router.post('/deleteDB', controller.deleteAll);
router.post('/checkRes', controller.isRes);
router.get('/myguest', controller.myguest);

module.exports = router;