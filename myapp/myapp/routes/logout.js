var express = require('express');
var router = express.Router();
var Client = require('node-rest-client').Client;
var client =  new Client();
var object = {};




/* GET home page. */
router.get('/', function(req, res, next) {
  console.log("hello logout");
  req.session.destroy(function(){
    req.session;
  });
  //res.redirect('/index');
  res.render('index', { title: 'logout' });
});


module.exports = router;
