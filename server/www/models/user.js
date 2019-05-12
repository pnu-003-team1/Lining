var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userSchema = new Schema ({
   email : {type : String, required : false, unique: true},
   name : String,
   pw : {type : String, required : true},
   phone : {type : String, required : true}
});

userSchema.statics.add = function (payload) {
   // this === Model
   const user = new this(payload);
   return user.save();
};

userSchema.statics.checkid = function (email){
	console.log("email: ", email);
	var test = this.find({email});
	console.log("test: ", email);
   return this.find({email});
};

userSchema.statics.checkpw = function (pw){
   return this.find({pw});
};

userSchema.statics.findAll = function(payload) {
	return this.find({});
};

userSchema.statics.deleteEmail = function (email) {
  return this.deleteOne({ email });
};

userSchema.statics.userlogin = function (email,pw) {
	return this.find({email, pw});
};

userSchema.statics.usermodify = function(email,name,payload){
	console.log("usermodify ", email, name, payload.pw);
	return this.findOneAndUpdate({email,name}, payload,
	{new : false});
};

userSchema.statics.deleteAll = function (payload){
   return this.remove({});
};

userSchema.statics.getUserInfo = function (email){
	console.log("User getUsersInfo", email);
	return this.find({email}).select("-_id name email pw phone");
};

module.exports = mongoose.model('User', userSchema);

