const express = require('express');
const router = express.Router();
const controller = require('./controller')

router.post('/join', controller.create);
router.post('/login', controller.login);
router.post('/repetition', controller.checkRep);
router.post('/dbtest', controller.dbtest);
router.post('/fullCheck', controller.fullCheck);

module.exports = router;