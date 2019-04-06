const express = require('express');
const router = express.Router();
const controller = require('./controller')

//router.get('/', controller.show);
//router.get('/:id', controller.index);
//router.delete('/:id', controller.delete);
router.post('/join', controller.create);
router.post('/login', controller.login);
router.post('/dbtest', controller.dbtest);
router.post('/dbtest1', controller.dbtest1);
router.post('/dbtest2', controller.dbtest2);
router.post('/dbtest3', controller.dbtest3);
router.post('/dbtest4', controller.dbtest4);
module.exports = router;

// 함수 체이닝->함수를 체인처럼 연결해서 사용
/*
function User(_name) {
	this.name = _name;
}

User.prototype.greeting = function() {
	console.log('Hello!');
	return this;
};

User.prototype.introduce = function() {
	console.log('I am ${this.name}');
	return this;
};

var Chris = new User(Chris);
Chris.greeting().introduce();
*/
