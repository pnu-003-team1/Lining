var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var buserSchema = new Schema ({
   email : {type : String, required : true,unique : true, index : true},
   bname : String,
   pw : {type : String, required : true},
   tel : {type : String, required : true},
   addr : {type : String, required : true},
   longitude : {type : String},
   latitude : {type : String},
   full : {type : Boolean, default: false}
});

buserSchema.statics.fullCheck = function(email,payload){
	return this.findOneAndUpdate({email}, payload,
	{new : true});
};

buserSchema.statics.addbuser = function (payload) {
   // this === Model
   console.log("Buser connect");
   const user = new this(payload);
   return user.save();
};

buserSchema.statics.checkbid = function (email){
	console.log("email: ", email);
    return this.find({email}).select("-_id tel addr full email");;
};
buserSchema.statics.checkEmail = function(email) {
	console.log("email: ", email);
	return this.find({email}).select("-_id bname tel addr postnum longitude latitude full email");;
};
buserSchema.statics.checkbpw = function (pw){
   return this.find({pw});
};

buserSchema.statics.findAll = function() {
	return this.find({});
};

buserSchema.statics.deleteAll = function (payload){
   return this.remove({});
};

buserSchema.statics.deleteEmail = function (email) {
  return this.deleteOne({ email });
};

buserSchema.statics.buserlogin = function (email,pw) {
	//console.log("ok");
	return this.find({email,pw});		
};
buserSchema.statics.busermodify = function(email,payload){
	return this.findOneAndUpdate({email}, payload,
	{new : true});
};

buserSchema.statics.getBusersInfo = function (email) {
	console.log("DB getBusersInfo");
	return this.find({email}).select("-_id bname tel addr full");
};

buserSchema.statics.getallBusersInfo = function (payload) {
	console.log("DB getBusersInfo");
	return this.find({}).select("-_id bname tel addr full email");
};

buserSchema.statics.searchName = function (bname) {
	console.log("searchName DB");
	return this.find({'bname': {'$regex': bname, '$options': 'i'}}).select("-_id bname tel addr full email");
};

	
module.exports = mongoose.model('Buser', buserSchema);

