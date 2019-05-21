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


  var api_url = 'http://54.164.52.65:3000/menu/'+fcn;
  var jsonContent = args;
  object.data = jsonContent;

  client.registerMethod("invokeUserMethod", api_url, "GET");
   client.methods.invokeUserMethod(object, function (data, response) {


     console.log( "data : " +data);
     callback(data);
  });
};

var invoke_user_post = function(fcn, args, callback){
  var jsonheaders = {
     "Content-Type" : "application/json"
     };
  object.headers = jsonheaders;


  var api_url = 'http://54.164.52.65:3000/menu/'+fcn;
  var jsonContent = args;
  object.data = jsonContent;

  client.registerMethod("invokeUserMethod", api_url, "POST");
   client.methods.invokeUserMethod(object, function (data, response) {


     var result = JSON.stringify(data);
     console.log("data : " + result);
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
      length: length,
      email:email
    });


    if(data.success) {
      console.log("email 전송 성공");
    }
  });
});

router.post('/delete',function(req,res,next){
  var sess = req.session;
  var email = sess.email;
  var food = req.body.food;

  var args = {
    email: email,
    food : food
  };
  invoke_user_post('remove',args,function(data){
    console.log("data.success : "+ data.success );
    if(data.success){
      res.redirect('/mymenu');
    }
    else{
      console.log("fail delete");
      res.redirect('/mymenu');
    }
  });
});

router.post('/modify', function(req, res, next){
  console.log("메뉴를 수정해보까");
  var sess = req.session;
  var email = sess.email;
  var food = req.body.mfood;
  var args = {
    email : email,
    food : food
  };

  invoke_user_get('getmenuinfo', args, function(data) {
    console.log("email: " +args.email);
    var success = JSON.parse(data).success;
    if(success){
      console.log("수정 정보 전송 성공");
      var foodn = JSON.parse(data).list[0].food;
      var foodp = JSON.parse(data).list[0].price;
      console.log( "price : " + foodp );
      var fn = encodeURIComponent(foodn);
      var fp = encodeURIComponent(foodp);
      res.redirect("/modifymenu/?fn=" +fn + "&fp=" +fp);
    }
  });
});

module.exports = router;
