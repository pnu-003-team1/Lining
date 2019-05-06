var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var session = require('express-session');
//var ajax = require('./routes/ajax');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');
var mainRouter = require('./routes/main');
var loginRouter = require('./routes/login');
var memberRouter = require('./routes/member');
var reloginRouter = require('./routes/relogin');
var reservationRouter = require('./routes/reservation');
var myPageRouter = require('./routes/mypage');
var mymenuRouter = require('./routes/mymenu');
var addmenuRouter = require('./routes/addmenu');
var successRouter = require('./routes/success');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));


app.use(session({
  secret: '@#@$MYSIGN#@$#$',
  resave: false,
  saveUninitialized: true
 }));
app.use('/', indexRouter);
app.use('/users', usersRouter);
app.use('/main', mainRouter);
app.use('/login', loginRouter);
app.use('/member', memberRouter);
app.use('/relogin', reloginRouter);
app.use('/reservation', reservationRouter);
app.use('/mypage', myPageRouter);
app.use('/mymenu' , mymenuRouter);
app.use('/addmenu' , addmenuRouter);
app.use('/success' , successRouter);
//app.use('/ajax', ajax);

app.post('http://54.164.52.65:3000/users/fullCheck', function(req, res) {

   var email = req.body.email;
   var full = req.body.full;

   var result = 'Email : ' + email + ' and ' + full;

   console.log(result);

   //res.send({email:email});
});

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
