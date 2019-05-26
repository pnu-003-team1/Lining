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
	const bname = req.body.bname;
	
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
	
	if (!bname.length) {
		return res.status(200).send({success: false, error: 'bname length 0'});
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

exports.myRes = (req, res) => {
	console.log("busr myRes: ", req.query.phone);
	const phone = req.query.phone;
	
	if (!phone.length) {
		return res.status(200).send({success: false, error: 'phone length 0'});
	}
	
	Reser.checkPhone(phone)
		.then((user) => {
			console.log("result len", user.length);
      if (user.length < 1) {
      	var list = new Array();
      			var result = {
      				success: true,
      				isRes: false,
      				list: list
      			};
		var jsonData = JSON.stringify(result);
		return res.status(200).send(jsonData);
      }
      else {
  		var list = new Array();
  		
  		user.forEach(function (item, index){
  			console.log('each item #', index, item.email);
  			console.log('each item #', index, item.total);
  			console.log('each item #', index, item.phone);
  			console.log('each item #', index, item.bemail);
  			console.log('each item #', index, item.date);
  			
  			list.push(item);
  				
  		});
  		
  		console.log("list length", list.length);
  		var result = {
  			success: true,
  			isRes: true,
  			list : list
  		};
  		var jsonData = JSON.stringify(result);
  		
  		res.send(jsonData);	
      } 
    })
    .catch(err => res.status(500).send({success: false, error: 'server error'}));
};

exports.remain = (req, res) => {
	console.log("busr remain: ", req.query.phone);
	const phone = req.query.phone;
	var info = new Object();
	
	if (!phone.length) {
		return res.status(200).send({success: false, error: 'phone length 0'});
	}
	
	Reser.checkPhone(phone)
		.then((user) => {
			console.log("result len: ", user.length);
			if (user.length < 1) {
				var result = {
					success: true,
					isRes: false
				};
				var jsonData = JSON.stringify(result);
				return res.status(200).send(jsonData);
			}
			else {
				user.forEach(function (item, index){
	      			info = {
			    		email: item.email,
			    		bemail: item.bemail,
				      	bname: item.bname,
				      	date: item.date	      				
			    	};		
	      		});
				Reser.remain(info.bemail, info.date)
					.then((result) => {
						var count = result.length;
						
						/*
						var list = new Array(); 
						result.forEach(function (item, index) {
							console.log("each item #", item.email);
							console.log("each item #", item.date);
							console.log("each item #", item.phone);
							console.log("each item #", item.bname);
							
							list.push(item);					
						});*/
						var result = {
							success: true,
							isRes: true,
							email: info.email,
							bemail: info.bemail,
							bname: info.bname,
							date: info.date,
							count: String(count)
						};		
						
						console.log("result: ", result);
						var jsonData = JSON.stringify(result);
						return res.status(200).send(jsonData);					
				});				
			}
		})
		.catch(err => res.status (200).send({success: false, error: err}));
};

exports.myguest = (req, res) => {
	console.log("busr email: ", req.query.bemail);
	const bemail = req.query.bemail;
	
	if (!bemail.length) {
		return res.status(200).send({success: false, error: 'phone length 0'});
	}
	
	Reser.findguest(bemail)
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
	      			console.log('each item #', index, item.email);
	      			console.log('each item #', index, item.phone);
	      			console.log('each item #', index, item.total);
	      			console.log('each item #', index, item.date);
	      			
	      			
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
    .catch(err => res.status(500).send({ msg: 'errr', err: err}));
};
