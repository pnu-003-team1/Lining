/*
 * index(): GET /users
 * show(): GET /usres/:id
 * delete(): DELETE /users/:id
 * create(): POST /users
 */
//req: 클라이언트로부터 넘어온 데이터 저장된 객체
//res: 클라이언트로 결과를 넘겨주기 위한 객체

// temporal user database
let users = [
 {
 	id: 1,
 	name: "Alice"
 },
 {
 	id: 2,
 	name: "Bek"
 },
 {
 	id: 3,
 	name: "Chris"
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
	
	const id = users.reduce((maxId, user) => {
		return user.id > maxId ? user.id : maxId
	}, 0) + 1;
	
	const newUser = {
		id: id,
		name: name
	};
	
	users.push(newUser);
	
	return res.status(201).json(newUser);
};