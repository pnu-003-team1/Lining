var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var buserSchema = new Schema ({
   email : {type : String, required : false, unique: true},
   bname : String,
   pw : {type : String, required : true},
   tel : {type : String, required : true},
   addr : {type : String, required : true},
   full : {type : Boolean, default: false}
});

buserSchema.statics.addbuser = function (payload) {
   // this === Model
   console.log("Buser connect");
   const user = new this(payload);
   return user.save();
};

buserSchema.statics.checkbid = function (email){
	console.log("email: ", email);
	var test = this.find({email});
	console.log("test: ", email);
   return this.find({email});
};

buserSchema.statics.checkbpw = function (pw){
   return this.find({pw});
};

buserSchema.statics.findAll = function(payload) {
	return this.find({});
};


buserSchema.statics.deleteEmail = function (email) {
  return this.remove({ "email" : email });
};

buserSchema.statics.buserlogin = function (email,pw) {
	//console.log("ok");
	return this.find({email,pw});
		
};

buserSchema.statics.getBusersInfo = function (payload) {
	console.log("DB getBusersInfo");
	return this.find({}).select("-_id bname tel addr full");
};
	
module.exports = mongoose.model('Buser', buserSchema);

