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


  var api_url = 'http://54.164.52.65:3000/buser/'+fcn;
  var jsonContent = args;
  object.data = jsonContent;

  client.registerMethod("invokeUserMethod", api_url, "POST");
   client.methods.invokeUserMethod(object, function (data, response) {
     console.log("data : " + JSON.stringify(data));
     callback(data);
  });
};

var invoke_user_post = function(fcn, args, callback){
  var jsonheaders = {
     "Content-Type" : "application/json"
     };
  object.headers = jsonheaders;


  var api_url = 'http://54.164.52.65:3000/reservation/'+fcn;
  var jsonContent = args;
  object.data = jsonContent;

  client.registerMethod("invokeUserMethod", api_url, "POST");
   client.methods.invokeUserMethod(object, function (data, response) {


     var result = JSON.stringify(data);
     console.log("data : " + result);
     callback(data);
  });
};

var reservation = function(fcn, args, callback){
   var jsonheaders = {
      "Content-Type" : "application/json"
      };
   object.headers = jsonheaders;


   var api_url = 'http://54.164.52.65:3000/reservation/'+fcn;
   var jsonContent = args;
   object.data = jsonContent;

   client.registerMethod("reservationMethod", api_url, "GET");
    client.methods.reservationMethod(object, function (data, response) {
      console.log("data : " + JSON.stringify(data));
      callback(data);
   });
};

router.get('/', function(req, res) {
  var sess = req.session;
  var email = sess.email;
  var pw = sess.pw;
  console.log("email  : " + email + " | pw : " + pw);

  var args = {
    bemail: email
  };

  reservation('myguest', args, function(data) {
    var success = JSON.parse(data).success;
    console.log(success);

    var emailArray = new Array();
    var phoneArray = new Array();
    var totalArray = new Array();
    var dateArray = new Array();

    if(success) {
      var length = JSON.parse(data).list.length;
      console.log(length);
      for(var i = 0; i < length; i++) {
        emailArray[i] = JSON.parse(data).list[i].email;
        phoneArray[i] = JSON.parse(data).list[i].phone;
        totalArray[i] = JSON.parse(data).list[i].total;
        dateArray[i] = JSON.parse(data).list[i].date;
      }
    }
    res.render('main', {
    title: '사업자',
    email: email,
    emailArray: emailArray,
    phoneArray: phoneArray,
    totalArray: totalArray,
    dateArray: dateArray,
    length: length
    });
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



router.post('/logout  ', function(req, res, next) {
  console.log("hello logout")
  req.session.destroy(function(){
    req.session;
  });
  res.redirect('/index');
});


module.exports = router;
