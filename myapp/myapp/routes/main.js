var express = require('express');
var router = express.Router();
var Client = require('node-rest-client').Client;
var client =  new Client();
var object = {};
var storeName = '';

var AWS = require('aws-sdk');
AWS.config.loadFromPath('./config.json');
AWS.config.update({region: 'us-west-2'});

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

    var length = 0;
    var emailArray = new Array();
    var phoneArray = new Array();
    var totalArray = new Array();
    var dateArray = new Array();

    if(success) {
      length = JSON.parse(data).list.length;
      for(var i = 0; i < length; i++) {
        emailArray[i] = JSON.parse(data).list[i].email;
        storeName = JSON.parse(data).list[i].bname;
        phoneArray[i] = JSON.parse(data).list[i].phone;
        totalArray[i] = JSON.parse(data).list[i].total;
        dateArray[i] = JSON.parse(data).list[i].date;
      }
      console.log("Store Name : " + storeName);
      console.log("Phone Array : " + phoneArray[1]);
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

router.post('/call', function(req, res, next) {
  var user_phoneNumber = req.body.call_user;
  console.log("User Phone Number : " + user_phoneNumber);
  var splitedPhoneNumber = user_phoneNumber.split('-');
  var params3 = {
  Message: '기다려주셔서 감사 드립니다. ' + storeName + '입니다.지금 입장해주시면 자리 안내 도와드리도록 하겠습니다.',
  PhoneNumber: '+8210' + splitedPhoneNumber[1] + splitedPhoneNumber[2],
  };

  var publishTextPromise = new AWS.SNS({apiVersion: '2010-03-31'}).publish(params3).promise();
  publishTextPromise.then(
  function(data) {
    console.log("MessageID is " + data.MessageId);
  }).catch(
    function(err) {
      console.error(err, err.stack);
    });

  res.redirect('/main');
});

router.post('/logout  ', function(req, res, next) {
  console.log("hello logout")
  req.session.destroy(function(){
    req.session;
  });
  res.redirect('/index');
});

router.post('/delete',function(req,res,next){

  var phone = req.body.dphone;

  var args = {
    phone:phone
  };
  invoke_user_post('remove',args,function(data){
    console.log("data.success : "+ data.success );
    if(data.success){
      res.redirect('/main');
    }
    else{
      console.log("fail delete");
      res.redirect('/main');
    }
  });
});
module.exports = router;
