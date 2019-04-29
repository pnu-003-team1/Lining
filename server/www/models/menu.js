var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var menuSchema = new Schema ({
	email : {type : String, required : true},
	food : {type : String, required : true},
	price : {type : String, required : true}
});

menuSchema.statics.addmenu = function (payload) {
	console.log("menu connect");
	const menu = new this(payload);
	return menu.save();
};

menuSchema.statics.findAll = function(payload) {
	return this.find({});
};

menuSchema.statics.deletemenu = function (email,food) {
	return this.deleteOne({email,food});
};

menuSchema.statics.menumodify = function(email,food,payload){
	return this.findOneAndUpdate({email,food}, payload,
	{new : false});
};








module.exports = mongoose.model('Menu', menuSchema);