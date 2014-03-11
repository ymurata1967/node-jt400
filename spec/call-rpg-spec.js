'use strict';
var jt400 = require('../lib/jt400'),
	q = require('q');

function onError(that, done) {
	return function (err) {
		that.fail(err);
		done();
	};
}

describe('PGM', function () {
	it('should run rpg program', function (done) {
		var getIsk = jt400.pgm('GET_ISK', [{name: 'mynt', size: 3}]);
		q.all([getIsk({mynt: 'Kr.'}), getIsk({mynt: 'EUR'})]).then(function (result) {
			expect(result[0].mynt).toBe('ISK');
			expect(result[1].mynt).toBe('EUR');
			done();
		}).fail(onError(this, done));
	});

	it('should run GETNETFG', function (done) {
		var getNetfang = jt400.pgm('GETNETFG', [
			{name: 'kt', size: 10, decimals: 0},
			{name: 'email', size: 30},
			{name: 'valid', size: 1}]);
		getNetfang({kt: '0123456789'}).then(function (result) {
			expect(result.email).toBe('');
			expect(result.valid).toBe('J');
			done();
		}).fail(onError(this, done));
	});
});