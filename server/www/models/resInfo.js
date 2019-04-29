var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var resInfoSchema = new Schema ({
   email : String,
   phone: {type : String, unique : true, required: true},
   total: Number,
   date: Date
});

resInfoSchema.statics.addGuest = function (payload) {
	var now = new Date();	
	
	// new
	console.log("create new email's guset");
	var info = ({
		email: payload.email,
		phone : payload.phone,
		total : payload.total,
		date : now
	});
		
	console.log("info: ", info);
	const result = new this(info);
	return result.save();
};

resInfoSchema.statics.findAll = function(payload) {
	console.log("Reservation-findAll");
	return this.find({});
};

resInfoSchema.statics.checkPhone = function(phone) {
	console.log("reserInfo-checkPhone")
	return this.find({phone});
};

resInfoSchema.statics.deletephone = function (phone) {
  return this.deleteOne({phone});
};

resInfoSchema.statics.deleteAll = function (payload){
   return this.remove({});
};

module.exports = mongoose.model('ReserInfo', resInfoSchema);