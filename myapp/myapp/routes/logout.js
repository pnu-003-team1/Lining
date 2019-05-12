var express = require('express');
var router = express.Router();
var Client = require('node-rest-client').Client;
var client =  new Client();
var object = {};

/* GET home page. */
router.get('/', function(req, res, next) {
  res.session.destroy();
  res.redirect('/index');
});

module.exports = router;
