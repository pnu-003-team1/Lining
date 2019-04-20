var express = require('express');
var router = express.Router();
var Client = require('node-rest-client').Client;
var client = new Client();
var temp;
/* json 파일 object 파일로 변환 */
var object = {};
var emailChecked = false;



var invoke_user = function(fcn, args, callback){
   var jsonheaders = {
      "Content-Type" : "application/json"
      };
   object.headers = jsonheaders;


   var api_url = 'http://54.180.123.67:3000/buser/'+fcn;
   var jsonContent = args;
   object.data = jsonContent;

   client.registerMethod("invokeUserMethod", api_url, "POST");
    client.methods.invokeUserMethod(object, function (data, response) {
      console.log("data : " + JSON.stringify(data));
      callback(data);
   });
};

router.get('/', function(req, res) {
  res.render('member');
  console.log('sign up page');
});

router.post('/repetition', function(req, res, next){
  var email = req.body.email;
  var args = {
    email : email
  };
  console.log('email : ' + email);
  invoke_user('repetition', args, function(data){
           emailChecked = data.success;
           console.log("emailChecked : " + emailChecked);
           if(data.success){
             res.send("사용 가능한 이메일 입니다.");
           }else{
             res.send("이미 존재하는 이메일 입니다.");
           }
        });
});

router.post('/', function(req, res, next) {
  console.log('POST 방식으로 서버 호출됨');
  var email = req.body.email;
  var pw = req.body.pw;
  var chk_pw = req.body.chk_pw;
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
  invoke_user('join', args, function(data){
      console.log("repeition check : [" + emailChecked + "]");
      if(data.success){
        if(emailChecked){
          emailChecked = false;
          res.redirect('/');
        }
        else{
          res.send('<script type="text/javascript">alert("이메일 중복 확인을 해주세요.");</script>');
        }
      }else{
        res.send('<script type="text/javascript">alert("회원가입 실패");</script>');
    }
        });

});

module.exports = router;
