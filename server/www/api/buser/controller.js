/*
* create(): POST /join
* login(): POST/login
* checkRep(): POST/repetition
**/

const router = require('express').Router();
const Buser = require('../../models/buser');

// temporal buser data
let users = [
 {
 	id: 1,
 	bname: "Alice",
 	email: "aaa@abc.com",
 	pw: "123",
 	tel: "123-432-4323",
 	addr: "abc def gfd"
 }
];

exports.create = (req, res) => {
	const bname = req.body.bname; // undefined 시  ''
	const email = req.body.email;
	const pw = req.body.pw;
	const tel = req.body.tel;
	const addr = req.body.addr;
	
	if (!bname.length) {
		return res.status(400).json({error: 'bname length 0'});
	}
	
	if (!email.length) {
		return res.status(400).json({error: 'email length 0'});
	}
	
	if (!pw.length) {
		return res.status(400).json({error: 'pw length 0'});
	}
	
	if (!tel.length) {
		return res.status(400).json({error: 'tel length 0'});
	}
	
	if (!addr.length) {
		return res.status(400).json({error: 'addr length 0'});
	}
	
	Buser.addbuser(req.body)
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
	
	Buser.buserlogin(req.body.email,req.body.pw)
		.then((user) => {
      if (user.length < 1){
      	return res.status(400).send({ error: 'User not found' });
      }
      else {
      	return res.status(200).send();
      }
    })
    .catch(err => res.status(500).send(err));
};

exports.checkRep = (req, res) => {
	const email = req.body.email;
	
	if (!email.length) {
		return res.status(400).json({error: 'email length 0'});
	}
	
	let user = users.filter(user => user.email === email)[0]
	
	if (user) {
		return res.status(400).json({error: 'Email repetition'});
	}
	
	Buser.checkbid(req.body.email)
		.then((user) => {
			console.log("result len", user.length);
      if (user.length < 1) return res.status(200).send();
      else res.status(400).send({error: 'email repetition'});
    })
    .catch(err => res.status(500).send({error: 'server error'}));
};
