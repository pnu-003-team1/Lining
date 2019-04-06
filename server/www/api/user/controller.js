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

// temporal user database
let users = [
 {
 	id: 1,
 	name: "Alice",
 	email: "abc@abc.com",
 	phone: "101-231-2341",
 	pw: "pqwe1"
 }
];

exports.index = (req, res) => {
	console.log(req.params.id);
	const id = parseInt(req.params.id, 10);
	
	if(!id) { // id == NaN, id 숫자인지 검증
		return res.status(400).json({error: 'Incorrect Id'});
	}
	
	let user = users.filter(user => user.id === id)[0]
	
	if (!user) {
		return res.status(404).json({error: 'Unknown user'});
	}
	console.log('user:' + user);
	
	return res.json(user);
};

exports.show = (req, res) => {
	//res.send("show ok");
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
	const name = req.body.name; // undefined 시  ''
	const email = req.body.email;
	const pw = req.body.pw;
	const phone = req.body.phone;
	
	if (!name.length) {
		return res.status(400).json({error: 'name length 0'});
	}
	
	if (!email.length) {
		return res.status(400).json({error: 'email length 0'});
	}
	
	if (!pw.length) {
		return res.status(400).json({error: 'pw length 0'});
	}
	
	if (!phone.length) {
		return res.status(400).json({error: 'phone length 0'});
	}
	/* // for tmep data
	const id = users.reduce((maxId, user) => {
		return user.id > maxId ? user.id : maxId
	}, 0) + 1;
	
	const newUser = {
		id: id,
		name: name,
		pw: pw,
		email: email,
		phone: phone
	};
	
	users.push(newUser);
	
	return res.status(200).json(newUser);*/
	
	User.add(req.body)
		.then(user => res.send(user))
		.catch(err => res.status(400).send({error: 'fail to add'}));
};


exports.login = (req, res) => {
	const email = req.body.email;
	const pw = req.body.pw;
	
	if (!email.length) {
		return res.status(400).json({error: 'email length 0'});
	}
	
	if (!pw.length) {
		return res.status(400).json({error: 'pw length 0'});
	}
	
	//res.status(200).send();
	
	User.userlogin(req.body.email,req.body.pw)
		.then((user) => {
      if (!user) return res.status(400).send({ error: 'User not found' });
      return res.status(200).send();
    })
    .catch(err => res.status(500).send(err));
};


exports.checkRep = (req, res) => {
	const email = req.body.email;
	
	if (!email.length) {
		return res.status(400).json({error: 'email length 0'});
	}
	
	/* // for temp data
	let user = users.filter(user => user.email === email)[0]
	
	if (user) {
		return res.status(400).json({error: 'Email repetition'});
	}
	
	res.status(200).send();*/
	
	User.checkid(req.body.email)
		.then((user) => {
      if (!user) return res.status(200).send();
      return res.status(400).send({error: 'email repetition'});
    })
    .catch(err => res.status(500).send({error: 'server error'}));
};

exports.dbtest = (req, res) => {
	User.add(req.body)
		.then(user => res.send(user))
		.catch(err => res.status(500).send(err));
};

exports.dbtest1 = (req, res) => {
	User.findAll()
		.then((user) => {
      if (!user.length) return res.status(404).send({ err: 'User not found' });
      res.send(`findOne successfully: ${user}`);
    })
    .catch(err => res.status(500).send({ msg: 'errr', err: err}));
};

exports.dbtest2 = (req, res) => {
	User.checkid(req.body.email)
		.then((user) => {
      if (!user) return res.status(404).send({ err: 'User not found' });
      res.send(`find successfully: ${user}`);
    })
    .catch(err => res.status(500).send(err));
};
exports.dbtest5 = (req, res) => {
	User.checkpw(req.body.pw)
		.then((user) => {
      if (!user) return res.status(404).send({ err: 'User not found' });
      res.send(`find successfully: ${user}`);
    })
    .catch(err => res.status(500).send(err));
};
exports.dbtest3 = (req, res) => {
	User.deleteEmail(req.body.email)
		.then((user) => {res.sendStatus(200);
		res.send(`find successfully: ${user}`); })
    	.catch(err => res.status(500).send(err));
};

exports.dbtest4 = (req, res) => {
	User.userlogin(req.body.email,req.body.pw)
		.then((user) => {
      if (!user) return res.status(404).send({ err: 'User not found' });
      res.send(`find successfully: ${user}`);
    })
    .catch(err => res.status(500).send(err));
};
