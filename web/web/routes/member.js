var express = require('express');
var router = express.Router();
var app = express();

router.get('/', function(req, res) {
  res.render('member');
  console.log('sign up page');
});

app.poat('http://54.180.123.67:3000/buser/join', function(req, res){
  var success = req.body.success;

  console.log(success);
});

app.poat('http://54.180.123.67:3000/buser/repetition', function(req, res, next) {
  var success = req.body.success;

  console.log(success);
});


module.exports = router;
