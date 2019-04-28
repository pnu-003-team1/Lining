const express = require('express');
const router = express.Router();
const controller = require('./controller')

//router.get('/', controller.show);
//router.get('/:id', controller.index);
//router.delete('/:id', controller.delete);
router.post('/join', controller.create);
router.post('/login', controller.login);
router.post('/repetition', controller.checkRep);
router.get('/dbtest', controller.dbtest);
router.get('/buserList', controller.getbuserList);
router.get('/removeall', controller.removeall);
module.exports = router;