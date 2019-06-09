var express = require('express');
var router = express.Router();
var Client = require('node-rest-client').Client;
var client =  new Client();
var object = {};

var store_tel = "";
var store_name = "";
var store_add = "";

var invoke_user_get = function(fcn, args, callback){
   var jsonheaders = {
      "Content-Type" : "application/json"
      };
   object.headers = jsonheaders;


   var api_url = 'http://54.164.52.65:3000/buser/'+fcn;
   var jsonContent = args;
   object.data = jsonContent;

   client.registerMethod("invokeUserMethod", api_url, "GET");
    client.methods.invokeUserMethod(object, function (data, response) {


      console.log( "data : " +data);
      callback(data);
   });
};

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

/* GET home page. */
router.get('/', function(req, res, next) {
  var sess = req.session;
  var email = sess.email;

  var args = {
    email : email
  }

  invoke_user_get('getemailbuser', args, function(data){
    var success = JSON.parse(data).success;
    store_tel = JSON.parse(data).list[0].tel;
    store_name = JSON.parse(data).list[0].bname;
    store_add = JSON.parse(data).list[0].addr;
    console.log("tel : " + store_tel );
    if(success){
      console.log("email 전송 성공");
      res.render('mypage', {
        title: 'MyPage' ,
        user_email : email,
        store_name : store_name,
        store_tel : store_tel,
        store_add : store_add
      });
    }
  })
});


router.post('/modify', function(req, res, next){
  var sess = req.session;
  var email = sess.email;

  var pw = req.body.pw;
  var bname = req.body.bname;
  var addr = req.body.addr;
  var tel = req.body.tel;

  var args = {
    email : email,
    pw : pw,
    bname : bname,
    addr : addr,
    tel : tel
  };
  console.log("email: " + email);
  console.log("bname: " + bname);
  invoke_user('/modify', args, function(data){
    if(data.success){
      console.log("수정 완료");
      var string = encodeURIComponent("정보 수정을");
      var btn = encodeURIComponent("확인");
      var href = encodeURIComponent("mypage");
      res.redirect("/success/?title=" +string + "&btitle=" +btn + "&href=" + href);
    }
    else{
      console.log("수정 실패");
      }
  });
})


module.exports = router;
