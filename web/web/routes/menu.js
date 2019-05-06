var express = require('express');
var router = express.Router();
var Client = require('node-rest-client').Client;
var client = new Client();
var object = {};

router.get('/', function(req, res){
  res.render('menu');
  console.log('menu modify page');
});

var invoke_user = function(fcn, args, callback){
   var jsonheaders = {
      "Content-Type" : "application/json"
      };
   object.headers = jsonheaders;


   var api_url = 'http://54.164.52.65:3000/menu/'+fcn;
   var jsonContent = args;
   object.data = jsonContent;

   client.registerMethod("invokeUserMethod", api_url, "POST");
    client.methods.invokeUserMethod(object, function (data, response) {
      console.log("data : " + JSON.stringify(data));
      callback(data);
   });
};

router.post('/join', function(req, res, next){
  console.log("POST 형식 메뉴 추가");
  var email = req.body.email;
  var food = req.body.food;
  var price = req.body.price;
  var args = {
    email : email,
    food : food,
    price : price
  };
  invoke_user('join', args, function(data){
    if(data.success){
      res.send("성공적으로 추가되었어요.");
    }
    else {
      // res.send(data.error);
      res.send("error");
    }
  });
});

router.post('/remove', function(req, res, next){
  console.log("POST 형식으로 메뉴 삭제");
  var email = req.body.email;
  var food = req.body.food;
  var args = {
    email : email,
    food : food
  };
  invoke_user('remove', args, function(data){
    if(data.success){
      res.send("성공적으로 삭제되었어요");
    }
    else{
      // res.send(data.error);
      res.send("error");
    }
  });
});

router.post('/modify', function(req, res, next){
  console.log("POST 형식으로 메뉴 수정");
  var email = req.body.email;
  var food = req.body.food;
  var price = req.body.price;
  var args = {
    email : email,
    food : food,
    price : price,
  };
  invoke_user('modify', args, function(data){
    if(data.success){
      res.send("성공적으로 수정되었어요.");
    }
    else{
      // res.send(data.error);
      res.send("error");
    }
  });
});

module.exports = router;
