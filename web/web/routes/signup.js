var express = require('express');
var router = express.Router();
var request = require('request');

router.get('/signup', function(req, res, next) {
  res.render('/member/signup');
  res.send('회원가입 페이지');
});

// request('http://54.180.123.67:3000/buser/join', function(error, response, body) {
//   if(!error && response.statusCode==200)
//     console.log(body);
//   else {
//     console.log("회원가입 실패");
//   }
// });

module.exports = router;
