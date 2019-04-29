const express = require('express');
const router = express.Router();
const controller = require('./controller')

router.get('/reservation', controller.getReservation);

module.exports = router;