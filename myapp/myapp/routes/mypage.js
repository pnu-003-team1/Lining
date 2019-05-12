var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  var sess = req.session;
  var email = sess.email;

  res.render('mypage', {
    title: 'MyPage' ,
    user_email : email,
    store_name : "hello",
    store_tel : "051-934-2534",
    store_add : "우리집"
  });
});

module.exports = router;
