const router = require('express').Router();
const Menu = require('../../models/menu');


exports.create = (req, res) => {
	console.log("menu create: ", req.body.food);
	const email = req.body.email;
	const food = req.body.food; // undefined 시  ''
	const price = req.body.price;

	if (!email.length) {
		return res.status(200).send({success: false, error: 'bname length 0'});
	}
	
	if (!food.length) {
		return res.status(200).send({success: false, error: 'email length 0'});
	}
	
	if (!price.length) {
		return res.status(200).send({success: false, error: 'pw length 0'});
	}
	
	Menu.addmenu(req.body)
		.then(menu => res.status(200).send({success: true}))
		.catch(err => res.status(200).send({success: false}));
};

exports.dbtest = (req, res) => {
   Menu.findAll()
      .then((user) => {
      if (!user.length) return res.status(404).send({ err: 'User not found' });
      res.send(`find successfully: ${user}`);
    })
    .catch(err => res.status(500).send({ msg: 'errr', err: err}));
};

exports.modify = (req, res) => {
	Menu.menumodify(req.body.email,req.body.food,req.body)
    .then((menu) => {
      if (req.body.price === menu.price) return res.status(404).send({ success : false });
      res.send({ success : true });
    })
    .catch(err => res.status(500).send({ msg: 'errr', err: err}));
};

exports.remove = (req, res) => {
   Menu.deletemenu(req.body.email,req.body.food)
      .then((menu) => {
      if (menu.n===0) return res.status(200).send({ success : false });
      else{res.json({ success : true })};
    })
    .catch(err => res.status(500).send({ msg: 'errr', err: err}));
};