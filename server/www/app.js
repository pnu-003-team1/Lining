// Server-side: app.js
const express    = require('express')
const app = express()
const bodyParser = require('body-parser') // post 위함
const usersRouter = require('./api/user/index')
//const vhost = require("vhost")

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}))

app.get('/', function (req, res) {
	res.send("Hello, World");
})

app.use('/users', usersRouter)
/*
app.get('/users', function(req, res) {
	
});*/

app.listen(3000, function() {
	console.log("server starting with 3000")
})

module.exports = app;
