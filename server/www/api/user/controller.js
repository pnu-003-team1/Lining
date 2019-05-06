/*
 * index(): GET /users
 * show(): GET /usres/:id
 * delete(): DELETE /users/:id
 * create(): POST /join
 * login(): POST/login
 */
//req: 클라이언트로부터 넘어온 데이터 저장된 객체
//res: 클라이언트로 결과를 넘겨주기 위한 객체

const router = require('express').Router();
const User = require('../../models/user');
const Buser = require('../../models/buser');
const Menu = require('../../models/menu');

exports.index = (req, res) => {
	console.log("index");
	console.log(req.params.id);
	const id = parseInt(req.params.id, 10);
	
	if(!id) { // id == NaN, id 숫자인지 검증
		return res.status(200).json({success: false, error: 'Incorrect Id'});
	}
	
	let user = users.filter(user => user.id === id)[0]
	
	if (!user) {
		return res.status(200).json({success: false, error: 'Unknown user'});
	}
	console.log('user:' + user);
	
	return res.json(user);
};

exports.show = (req, res) => {
	return res.json(users);
};

exports.delete = (req, res) => {
	const id = parseInt(req.params.id, 10);
	if (!id) {
		return res.status(400).json({error: 'Incorrect Id'});
	}
	
	const userIdx = users.findIndex(user => {
		return user.id === id;
	});
	if(userIdx === -1) {
		return res.status(404).json({error: 'Unknown user'});
	}
	
	users.splice(userIdx, 1); // 1번째 파라매터 포함해 1개 삭제
	
	res.status(204).send(); // No Content
};

exports.create = (req, res) => {
	console.log("user create: ", req.body.name);
	const name = req.body.name; // undefined 시  ''
	const email = req.body.email;
	const pw = req.body.pw;
	const phone = req.body.phone;
	
	if (!name.length) {
		return res.status(200).send({success: false, error: 'name length 0'});
	}
	
	if (!email.length) {
		return res.status(200).send({success: false, error: 'email length 0'});
	}
	
	if (!pw.length) {
		return res.status(200).send({success: false, error: 'pw length 0'});
	}
	
	if (!phone.length) {
		return res.status(200).send({success: false, error: 'phone length 0'});
	}
	
	User.add(req.body)
		.then(user => res.status(200).send({success: true}))
		.catch(err => res.status(200).send({success: false, error: 'fail to add'}));
};


exports.login = (req, res) => {
	console.log("user login: ", req.body.email);

	const email = req.body.email;
	const pw = req.body.pw;
	
	if (!email.length) {
		return res.status(200).send({success: false, error: 'email length 0'});
	}
	
	if (!pw.length) {
		return res.status(200).send({success: false, error: 'email length 0'});
	}
	
	if (!pw.length) {
		return res.status(200).send({success: false, error: 'pw length 0'});
	}
	
	User.userlogin(req.body.email,req.body.pw)
		.then((user) => {
      if (user.length < 1){
      	return res.status(200).send({success: false, error: 'User not found' });
      }
      else {
      	return res.status(200).send({success: true});
      }
    })
    .catch(err => res.status(500).send(err));
    
};


exports.checkRep = (req, res) => {
	console.log("user checkRep: ", req.body.email);
	const email = req.body.email;
	
	if (!email.length) {
		return res.status(200).send({success: false, error: 'email length 0'});
	}
	
	User.checkid(req.body.email)
		.then((user) => {
			console.log("result len", user.length);
      if (user.length < 1) return res.status(200).send({success: true});
      else res.status(200).send({success: false, error: 'email repetition'});
    })
    .catch(err => res.status(500).send({error: 'server error'}));
};

exports.dbtest = (req, res) => {
   User.findAll()
      .then((user) => {
      if (!user.length) return res.status(404).send({ err: 'User not found' });
      res.send(`find successfully: ${user}`);
    })
    .catch(err => res.status(500).send({ msg: 'errr', err: err}));
};


exports.getbuserList = (req, res) => {
	console.log("user buserList");
	Buser.getBusersInfo()
		.then((user) => {
      		if(!user.length){
      			var list = new Array();
      			var result = {
      				success: false,
      				list: list
      			};
      			var jsonData = JSON.stringify(result);
      			return res.status(200).send(jsonData);
      		}
      		else {
      			var bInfo = new Object();
	      		var list = new Array();
	      		var i = user.length;
	      		
	      		user.forEach(function (item, index){
	      			console.log('each item #', index, item.full);
	      			console.log('each item #', index, item.bname);
	      			console.log('each item #', index, item.tel);
	      			console.log('each item #', index, item.addr);
	      			console.log('each item #', index, item.email);
	      			
	      			list.push(item);
	      				
	      		});
	      		
	      		console.log("list length", list.length);
	      		var result = {
	      			success: true,
	      			list : list
	      		};
	      		var jsonData = JSON.stringify(result);
	      		
	      		res.send(jsonData);	
      		}	      	
    })
    .catch(err => res.send({sccuess: false, list: [], error: err}));
};


exports.modify = (req, res) => {
	User.usermodify(req.body.email,req.body.name,req.body)
    .then((user) => {
      if (req.body.pw === user.pw && req.body.phone === user.phone) return res.status(200).send({ success : false });
      res.send({ success : true });
    })
    .catch(err => res.status(500).send({ msg: 'errr', err: err}));
};

exports.remove = (req, res) => {
   User.deleteEmail(req.body.email)
      .then((user) => {
      if (user.n===0) return res.status(200).send({ success : false });
      else{res.json({ success : true })};
    })
    .catch(err => res.status(500).send({ msg: 'errr', err: err}));
};

exports.removeall = (req, res) => {
   User.deleteAll()
      .then((user) => {
      if (!user.length) return res.status(404).send({ err: 'User not found' });
      res.send(`find successfully: ${user}`);
    })
    .catch(err => res.status(500).send({ msg: 'errr', err: err}));
};

exports.getMenuList = (req, res) => {
	console.log("user getMenuList: ", req.body.email);
	const bEmail = req.body.bEmail;
	
	if (!bEmail.length) {
		return res.status(200).send({success: false, error: 'email length 0'});
	}
	
	Menu.getMenuList(bEmail)
		.then((user) => {
      		if(!user.length){
      			var list = new Array();
      			var result = {
      				success: true,
      				list: list
      			};
      			var jsonData = JSON.stringify(result);
      			return res.status(200).send(jsonData);
      		}
      		else {
      			var menuInfo = new Object();
	      		var list = new Array();
	      		var i = user.length;
	      		
	      		user.forEach(function (item, index){
	      			menuInfo = {
	      				food: item.food,
	      				price: item.price
	      			};
	      			console.log('menuInfo: ', menuInfo.food, menuInfo.price);      			
	      			list.push(menuInfo);
	      		});
	      		
	      		console.log("list length", list.length);
	      		var result = {
	      			success: true,
	      			list : list
	      		};
	      		var jsonData = JSON.stringify(result);
	      		
	      		res.send(jsonData);	
      		}	      	
    })
    .catch(err => res.send({sccuess: false, list: [], error: err}));
};

exports.menuDBtest = (req, res) => {
	console.log("menuDBtest");
   	Menu.findAll()
      .then((user) => {
      if (!user.length) return res.status(404).send({ err: 'Menu not found' });
      res.send(`find successfully: ${user}`);
    })
    .catch(err => res.status(500).send({ msg: 'errr', err: err}));
};

