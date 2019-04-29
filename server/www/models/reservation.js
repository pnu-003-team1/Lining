var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var reservationSchema = new Schema ({
   email : String,
   phone: {type : String, unique : true, required: true},
   total: Number,
   date: Date
});

reservationSchema.statics.addGuest = function (payload) {
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

reservationSchema.statics.findAll = function(payload) {
	console.log("Reservation-findAll");
	return this.find({});
};

reservationSchema.statics.checkPhone = function(phone) {
	return this.find({phone});
};

reservationSchema.statics.deletephone = function (phone) {
  return this.deleteOne({phone});
};

reservationSchema.statics.deleteAll = function (payload){
   return this.remove({});
};

module.exports = mongoose.model('Reser', reservationSchema);