var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  var title = req.query.title;
  var btn_string = req.query.btitle;
  var href_string = req.query.href;
  res.render('success',
    { title: title,
      btn_string : btn_string,
      href_string : href_string
     });
});

module.exports = router;
