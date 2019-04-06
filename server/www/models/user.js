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
  return this.remove({ "email" : email });
};

userSchema.statics.userlogin = function (email,pw) {
	//console.log("ok");
	return this.find({email,pw});
		
};
	
module.exports = mongoose.model('User', userSchema);

