const express = require('express');
const router = express.Router();
const controller = require('./controller')

router.post('/join', controller.create);
router.post('/login', controller.login);
router.post('/repetition', controller.checkRep);
module.exports = router;