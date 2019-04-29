/*
* create(): POST /join
* login(): POST/login
* checkRep(): POST/repetition
**/

const router = require('express').Router();
const Buser = require('../../models/buser');
const Reservation = require('../../models/reservation')

exports.create = (req, res) => {
	console.log("busr create: ", req.body.bname);
	const bname = req.body.bname; // undefined 시  ''
	const email = req.body.email;
	const pw = req.body.pw;
	const tel = req.body.tel;
	const addr = req.body.addr;

	if (!bname.length) {
		return res.status(200).send({success: false, error: 'bname length 0'});
	}
	
	if (!email.length) {
		return res.status(200).send({success: false, error: 'email length 0'});
	}
	
	if (!pw.length) {
		return res.status(200).send({success: false, error: 'pw length 0'});
	}
	
	if (!tel.length) {
		return res.status(200).send({success: false, error: 'tel length 0'});
	}
	
	if (!addr.length) {
		return res.status(200).send({success: false, error: 'addr length 0'});
	}
	
	Buser.addbuser(req.body)
		.then(user => res.status(200).send({success: true}))
		.catch(err => res.status(200).send({success: false, error: 'fail to add'}));
};

exports.fullCheck = (req, res) => {
	const email = req.body.email;
	const full = req.body.full;
	
	if (!email.length) {
		return res.send({success: false, error: 'email length 0'});
	}
	
	if (!full.length) {
		return res.send({success: false, error: 'full을 입력하세용'});
	}
	

	console.log("busr fullCheck: ", email, full);
	Buser.fullCheck(req.body)
		.then(user => res.status(200).send({success: true}))
		.catch(err => res.status(400).send({success: false, error: 'fail to revise DB'}));

	Buser.fullCheck(req.body.email,req.body)
    .then(user => res.send(user))
    .catch(err => res.status(400).send(err));
};

exports.login = (req, res) => {
	console.log("busr login: ", req.body.email , req.body.pw);
	const email = req.body.email;
	const pw = req.body.pw;
	
	if (!email.length) {
		return res.status(200).send({success: false, error: 'email length 0'});
	}
	
	if (!pw.length) {
		return res.status(200).send({success: false, error: 'pw length 0'});
	}
	
	Buser.buserlogin(req.body.email,req.body.pw)
		.then((user) => {
      if (user.length < 1){
      	return res.status(200).send({ success: false, error: 'User not found' });
      }
      else {
      	return res.status(200).send({success: true});
      }
    })
    .catch(err => res.status(500).send({success: false, error: err}));
};

exports.checkRep = (req, res) => {
	console.log("busr checkRep: ", req.body.email);
	const email = req.body.email;
	
	if (!email.length) {
		return res.status(200).send({success: false, error: 'email length 0'});
	}
	
	Buser.checkbid(req.body.email)
		.then((user) => {
			console.log("result len", user.length);
      if (user.length < 1) return res.status(200).send({success: true});
      else res.status(200).send({success: false, error: 'email repetition'});
    })
    .catch(err => res.status(500).send({success: false, error: 'server error'}));
};

exports.dbtest = (req, res) => {
   Buser.findAll()
      .then((user) => {
      if (!user.length) return res.status(404).send({ err: 'User not found' });
      res.send(`find successfully: ${user}`);
    })
    .catch(err => res.status(500).send({ msg: 'errr', err: err}));
};

exports.removeall = (req, res) => {
   Buser.deleteAll()
      .then((user) => {
      if (!user.length) return res.status(404).send({ err: 'User not found' });
      res.send(`find successfully: ${user}`);
    })
    .catch(err => res.status(500).send({ msg: 'errr', err: err}));
};
/*exports.fullCheck = (req, res) => {
	const email = req.body.email;
	const full = req.body.full;
	
	console.log("busr fullCheck: ", email, full);
	Buser.fullCheck(req.body)
		.then(user => res.status(200).send({success: true}))
		.catch(err => res.status(200).send({success: false, error: 'fail to revise DB'}));
};*/