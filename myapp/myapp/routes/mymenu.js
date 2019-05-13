var express = require('express');
var router = express.Router();
var Client = require('node-rest-client').Client;
var client = new Client();
var object = {};

var invoke_user_get = function(fcn, args, callback){
  var jsonheaders = {
     "Content-Type" : "application/json"
     };
  object.headers = jsonheaders;


  var api_url = 'http://54.164.52.65:3001/menu/'+fcn;
  var jsonContent = args;
  object.data = jsonContent;

  client.registerMethod("invokeUserMethod", api_url, "GET");
   client.methods.invokeUserMethod(object, function (data, response) {


     console.log( "data : " +data);
     callback(data);
  });
};


/* GET home page. */
router.get('/', function(req, res, next) {

  var sess = req.session;
  var email = sess.email;
  var args = {
    email: email
  };

  invoke_user_get('getemailmenu', args, function(data) {
    var length = JSON.parse(data).list.length;
    var foodArray = new Array();
    var priceArray = new Array();

    for(var i = 0; i < length; i++) {
      foodArray[i] = JSON.parse(data).list[i].food;
      priceArray[i] = JSON.parse(data).list[i].price;
    }
    res.render('mymenu', { 
      title: 'MyMenu',
      foodArray: foodArray,
      priceArray: priceArray,
      length: length
    });

    
    if(data.success) {
      console.log("email 전송 성공");
    }
  });
});

module.exports = router;