/*
* create(): POST /join
* login(): POST/login
**/

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
	
	const id = users.reduce((maxId, user) => {
		return user.id > maxId ? user.id : maxId
	}, 0) + 1;
	
	const newUser = {
		id: id,
		bname: bname,
		email: email,
		pw: pw,
		tel: tel,
		addr: addr
	};
	
	users.push(newUser);
	
	return res.status(200).json(newUser);
};


