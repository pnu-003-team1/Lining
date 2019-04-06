var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userSchema = new Schema ({
   email : {type : String, required : true, unique: true},
   name : String,
   pw : {type : String, required : true},
   phone : {type : String, required : true},
   addr : {type : String, required : true}
});

userSchema.statics.addbuser = function (payload) {
   // this === Model
   const buser = new this(payload);
   return buser.save();
};

userSchema.statics.busercheckid = function (email){
   return this.findOne({email});
};

userSchema.statics.busercheckpw = function (email){
   return this.findOne({email});
};

userSchema.statics.buserfindAll = function() {
	return this.find({});
};


userSchema.statics.buserdeleteEmail = function (email) {
  return this.remove({ email });
};

userSchema.statics.buserlogin = function (email,pw) {
	//console.log("ok");
	return this.find({email,pw});
};
	
module.exports = mongoose.model('User', userSchema);

