var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('reserve_suc', { title: 'ReservationSuccess' });
});

module.exports = router;
