var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userSchema = new Schema ({
	name: String,
});

userSchema.static.create = function (payload) {
	// this === Model
	const user = new this(payload);
	return user.save();
};

module.exports = mongoose.model('User', userSchema);
