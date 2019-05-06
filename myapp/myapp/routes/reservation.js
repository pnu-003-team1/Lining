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


   var api_url = 'http://54.164.52.65:3000/reservation/'+fcn;
   var jsonContent = args;
   object.data = jsonContent;

   client.registerMethod("invokeUserMethod", api_url, "POST");
    client.methods.invokeUserMethod(object, function (data, response) {
       console.log("data : " + JSON.stringify(data));
       callback(data);
   });
}
/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('reservation', { title: 'reservation' });
});

router.post('/',function(req, res, next){

  console.log(req.body);
  var sess = req.session;
  var email = sess.email;
  var phone = req.body.phone;
  var total = req.body.total;
  var args = {
    "bemail" : email,
    "phone" : phone,
    "total" : total,
    "email": "hello"
  };

  console.log("email  : " + email + " | phone : " + phone + " | total : "+ total);
  invoke_user("add", args, function(data){
    if(data.success){
      console.log("reservation success!");
      var string = encodeURIComponent("예약을");
      var btn = encodeURIComponent("확인");
      var href = encodeURIComponent("reservation");
      res.redirect("/success/?title=" +string + "&btitle=" +btn + "&href=" + href);
    }else{
      console.log("reservation fail!");
      res.redirect("/error");
    }

  })
})



module.exports = router;
