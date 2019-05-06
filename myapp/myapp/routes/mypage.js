var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  var sess = req.session;
  var email = sess.email;

  res.render('mypage', {
    title: 'MyPage' ,
    user_email : email
  });
});

module.exports = router;
