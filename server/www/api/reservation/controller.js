const router = require('express').Router();
const Reser = require('../../models/resInfo');
const Buser = require('../../models/buser');

exports.addGuest = (req, res) => {
	console.log("add Guest: ", req.body.email);
	console.log(req.body.phone,req.body.total);
	console.log(req.body.bemail);
	
	const email = req.body.email;
	const phone = req.body.phone;
	const total = req.body.total;
	const bemail = req.body.bemail;
	
	console.log("bemail length: ", bemail.length);
	
	if (!email.length) {
		return res.status(200).send({success: false, error: 'email length 0'});
	}
	if (!phone.length) {
		return res.status(200).send({success: false, error: 'phone length 0'});
	}
	if (!total.length) {
		return res.status(200).send({success: false, error: 'total length 0'});
	}
	
	if (!bemail.length) {
		return res.status(200).send({success: false, error: 'bemail length 0'});
	}
	
    Reser.addGuest(req.body)
    	.then(user => res.status(200).send({success: true}))
		.catch(err => res.status(200).send({success: false, error: err}));
};

exports.remove = (req, res) => {
   Reser.deletephone(req.body.phone)
      .then((user) => {
      if (user.n===0) return res.status(200).send({ success : false });
      else{res.json({ success : true })};
    })
    .catch(err => res.status(500).send({ msg: 'errr', error: 'fail to remove'}));
};


exports.dbtest = (req, res) => {
   Reser.findAll()
      .then((user) => {
      if (!user.length) return res.status(404).send({ error: 'User not found' });
      res.send(`find successfully: ${user}`);
    })
    .catch(err => res.status(500).send({ msg: 'errr', err: err}));
};

exports.isRes = (req, res) => {
	console.log("busr checkRep: ", req.body.phone);
	const phone = req.body.phone;
	
	if (!phone.length) {
		return res.status(200).send({success: false, error: 'phone length 0'});
	}
	
	Reser.checkPhone(req.body.phone)
		.then((user) => {
			console.log("result len", user.length);
      if (user.length < 1) return res.status(200).send({success: true});
      else res.status(200).send({success: false, error: 'already reservation'});
    })
    .catch(err => res.status(500).send({success: false, error: 'server error'}));
};

exports.deleteAll = (req, res) => {
	Reser.deleteAll()
		.then(user => res.status(200).send({success: true}))
		.catch(err => res.status (200).send({success: false, error: err}));
};
