var express = require('express');
var router = express.Router();
var Client = require('node-rest-client').Client;
var client = new Client();
var object = {};

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('addmenu', { title: '메뉴 추가하기' });
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
  var sess = req.session;
  var email = sess.email;
  console.log("email : " + email);
  var food = req.body.food;
  var price = req.body.price;
  var args = {
    email : email,
    food : food,
    price : price
  };
  invoke_user('join', args, function(data){
    if(data.success){
      var string = encodeURIComponent("메뉴 추가를");
      var btn = encodeURIComponent("우리가게 메뉴로 돌아가기");
      var href = encodeURIComponent("mymenu");
      res.redirect("/success/?title=" +string + "&btitle=" +btn + "&href=" + href);
    }
    else {
      // res.send(data.error);
      res.send("error");
    }
  });
});


module.exports = router;
