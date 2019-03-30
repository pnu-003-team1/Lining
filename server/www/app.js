// Server-side: app.js
const express    = require('express')
const app = express()
const bodyParser = require('body-parser')

/*
app.get('/', function (req, res) {
	res.send("Hello, World");
})

//req: 클라이언트로부터 넘어온 데이터 저장된 객체
//res: 클라이언트로 결과를 넘겨주기 위한 객체
app.get('/pass', function(req, res) {
	var data = req.query.data
	res.send(data)
})*/

app.use(bodyParser.urlencoded({extended: true}))

app.post('/user', function(req, res) {
	var userID = req.body.id
	var userPW = req.body.pw
	
	res.send('id: ' + userID + 'pw: ' + userPW)
})

app.listen(3000, function() {
	console.log("server starting with 3000")
})
