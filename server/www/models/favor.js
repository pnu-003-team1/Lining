var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var favorSchema = new Schema({
	email: {type: String},
	bemail: {type: String},
	bphone: {type: String},
	baddr: {type: String},
	bname: {type: String}
});

favorSchema.statics.checkPair = function (email, bemail) {
	console.log("favSchema-check Pair");
	return this.find({email, bemail});
}

favorSchema.statics.add = function (payload) {
	console.log("favSchema-add");
	const fav = new this(payload);
	return fav.save();	
}

favorSchema.statics.getList = function (email) {
	console.log("favSchema-getList");
	return this.find({email}).select("-_id bemail bphone baddr bname");
}

favorSchema.statics.delOne = function (email, bemail) {
	console.log("favSchema-deleteOne",email,bemail);
	return this.deleteOne({email, bemail});
}

favorSchema.statics.deleteAll = function (payload) {
	return this.remove({});	
};

module.exports = mongoose.model('Favor', favorSchema);

