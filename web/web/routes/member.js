var express = require('express');
var router = express.Router();
// var Client = require('node-rest-client').Client;
// var client = new Client();

router.get('/', function(req, res) {
  res.render('member');
  console.log('sign up page');
});

router.post('/', function(req, res, next) {
  console.log('POST 방식으로 서버 호출됨');
  var msg = req.body.msg;
  msg = '[echo]' + msg;
  res.send({result:true, msg:msg});
});

module.exports = router;
