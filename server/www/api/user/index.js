const express = require('express');
const router = express.Router();
const controller = require('./controller')

router.post('/join', controller.create);
router.post('/login', controller.login);
router.post('/repetition', controller.checkRep);
router.post('/favorite', controller.addFav);
router.post('/delfav', controller.delFavOne);
router.post('/isFav', controller.isFav);

router.get('/menuList', controller.getMenuList);
router.get('/menudb', controller.menuDBtest);
router.get('/dbtest', controller.dbtest);
router.get('/buserList', controller.getbuserList);
router.get('/removeall', controller.removeall);
router.get('/userInfo', controller.getUserInfo);
router.get('/favorite', controller.getFavList);
router.get('/search', controller.searchBuser);

router.post('/remove', controller.remove);
router.post('/modify', controller.modify);
router.post('/removeFavor', controller.removeFavor);
module.exports = router;
