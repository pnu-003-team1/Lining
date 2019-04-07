/*
* create(): POST /join
* login(): POST/login
* checkRep(): POST/repetition
**/

const router = require('express').Router();
const Buser = require('../../models/buser');

exports.create = (req, res) => {
	console.log("busr create: ", req.body.bname);
	const bname = req.body.bname; // undefined 시  ''
	const email = req.body.email;
	const pw = req.body.pw;
	const tel = req.body.tel;
	const addr = req.body.addr;
	
	if (!bname.length) {
		return res.status(200).json({success: false, error: 'bname length 0'});
	}
	
	if (!email.length) {
		return res.status(200).json({success: false, error: 'email length 0'});
	}
	
	if (!pw.length) {
		return res.status(200).json({success: false, error: 'pw length 0'});
	}
	
	if (!tel.length) {
		return res.status(200).json({success: false, error: 'tel length 0'});
	}
	
	if (!addr.length) {
		return res.status(200).json({success: false, error: 'addr length 0'});
	}
	
	Buser.addbuser(req.body)
		.then(user => res.status(200).send({success: true}))
		.catch(err => res.status(200).send({success: false, error: 'fail to add'}));
};

exports.login = (req, res) => {
	console.log("busr login: ", req.body.email);
	const email = req.body.email;
	const pw = req.body.pw;
	
	if (!email.length) {
		return res.status(200).json({success: false, error: 'email length 0'});
	}
	
	if (!pw.length) {
		return res.status(200).json({success: false, error: 'pw length 0'});
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
		return res.status(200).json({success: false, error: 'email length 0'});
	}
	
	Buser.checkbid(req.body.email)
		.then((user) => {
			console.log("result len", user.length);
      if (user.length < 1) return res.status(200).send({success: true});
      else res.status(200).send({success: false, error: 'email repetition'});
    })
    .catch(err => res.status(500).send({success: false, error: 'server error'}));
};
