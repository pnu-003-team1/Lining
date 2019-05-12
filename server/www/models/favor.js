var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var favorSchema = new Schema({
	email: {type: String},
	bemail: {type: String}
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
	return this.find({email}).select("-_id bemail");
}

favorSchema.statics.delOne = function (email, bemail) {
	console.log("favSchema-deleteOne",email,bemail);
	return this.deleteOne({email, bemail});
}

module.exports = mongoose.model('Favor', favorSchema);

