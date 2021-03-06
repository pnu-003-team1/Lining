var express = require('express');
var router = express.Router();
var Client = require('node-rest-client').Client;
var client =  new Client();
var object = {};

var invoke_user = function(fcn, args, callback){
   var jsonheaders = {
      "Content-Type" : "application/json"
      };
   object.headers = jsonheaders;


   var api_url = 'http://54.180.123.67:3000/users/'+fcn;
   var jsonContent = args;
   object.data = jsonContent;

   client.registerMethod("invokeUserMethod", api_url, "POST");
    client.methods.invokeUserMethod(object, function (data, response) {
      console.log("data : " + JSON.stringify(data));
      callback(data);
   });
};

router.get('/', function(req, res) {
  var sess = req.session;
  var email = sess.email;
  var pw = sess.pw;
  //console.log("email  : " + email + " | pw : " + pw);
  res.render('main',{
    title:'main'
  });
});

router.post('/fullCheck', function(req, res, next){
  var sess = req.session;
  var email = sess.email;
  var full = req.body.full;
  var args = {
    email : email,
    full : full
  };
  console.log("email  : " + email + " | full : " + full);
  console.log("post session success!");
  invoke_user('fullCheck', args, function(data){
    var fullChecked = data.success;
    console.log("fullChecked : " + fullChecked);

  });
});

module.exports = router;
