var express = require('express');
var router = express.Router();
var request = require('request');

// var port = '3000';
// var api_host = 'http://54.180.123.67' + port;
// // var Client = require('node-rest-client').Client;
// var client = new Client();
// var object = {};

router.get('/', function(req, res) {
  res.render('member');
});
//
// var jsonheaders = {
//   "Authorization": "Bearer " +
// }
//
// var api_url = api_host + 'buser/join';
// object.headers = jsonheaders;
//
// client.registerMethod("queryUserMethod", api_url, 'POST');
// client.methods.queryUserMethod(object, function(data, response) {
//   var statusCode = response.statusCode;
//   console.log('data : ' + data);
//   console.log('statusCode : ' + statusCode);
// });
//
module.exports = router;
