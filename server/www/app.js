// Server-side: app.js
const express = require('express')
const app = express()
const bodyParser = require('body-parser') // post 위함

const usersRouter = require('./api/user/index')
const buserRouter = require('./api/buser/index')
const menuRouter = require('./api/menu/index')
const dbtestRouter = require('./api/dbtest/index')
const reserRouter = require('./api/reservation/index')
const mongoose = require('mongoose')

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}))

app.get('/', function (req, res) {
	res.send("Hello, World");
})

app.use('/users', usersRouter)
app.use('/buser', buserRouter)
app.use('/menu', menuRouter)
app.use('/reservation', reserRouter)
app.use('/dbtest', dbtestRouter)

app.listen(3000, function() {
	console.log("server starting with 3000")
})

var db = mongoose.connection;
db.on('error', console.error);
db.once('open', function() {
	// CONNECTED TO MONGODB SERVER
	console.log("Connected to mongodb server");
});
mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost/mongodb_tutorial',{useNewUrlParser : true});
module.exports = app;
