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
	Buser.fullCheck(req.body.email,req.body)
    .then(buser => {if (buser.full === true||buser.full === false){
      	return res.status(200).send({ success : true });
      }
      else {
      	return res.status(200).send({success : false});
      }
    })
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

exports.modify = (req, res) => {
	console.log("buser modify", req.body.email);
	
	const bname = req.body.bname;
	const addr = req.body.addr;
	const pw = req.body.pw;
	const tel = req.body.tel;
	
	if (!bname.length) {
		return res.status(200).send({success: false, error: 'name length 0'});
	}
	
	if (!addr.length) {
		return res.status(200).send({success: false, error: 'addr length 0'});
	}
	
	if (!pw.length) {
		return res.status(200).send({success: false, error: 'pw length 0'});
	}
	
	if (!tel.length) {
		return res.status(200).send({success: false, error: 'tel length 0'});
	}
	
	Buser.busermodify(req.body.email,req.body)
    .then((user) => {
      res.send({ success : true });
    })
    .catch(err => res.status(500).send({ success: false, err: err}));
};
exports.remove = (req, res) => {
   Buser.deleteEmail(req.body.email)
      .then((buser) => {
      if (buser.n===0) return res.status(200).send({ success : false });
      else{res.json({ success : true })};
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
exports.getemailbuser = (req, res) => {
	Buser.checkEmail(req.body.email)
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
exports.getallbuserList = (req, res) => {
	console.log("All user buserList");
	Buser.getallBusersInfo()
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
	      		
	      		user.forEach(function (item, index){
	      			console.log('each item #', index, item.full);
	      			console.log('each item #', index, item.bname);
	      			console.log('each item #', index, item.tel);
	      			console.log('each item #', index, item.addr);
	      			
	      			
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