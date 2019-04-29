const router = require('express').Router();
const User = require('../../models/user');
const Buser = require('../../models/buser');
const Reservation = require('../../models/reservation');

exports.getReservation = (req, res) => {
   Reservation.findAll(req.body)
      .then((user) => {
      if (!user.length) return res.status(404).send({ err: 'User not found' });
      res.send(`find successfully: ${user}`);
    })
    .catch(err => res.status(500).send({ msg: 'errr', err: err}));
};