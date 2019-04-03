const express = require('express');
const router = express.Router();
const controller = require('./controller')

router.post('/join', controller.create);
//router.post('/login', controller.login);
module.exports = router;