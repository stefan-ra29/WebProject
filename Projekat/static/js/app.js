const Register = { template: '<register></register>' }
const SFDisplay = { template: '<sport_facility_display></sport_facility_display>' }

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: Register},
		{ path: '/sf-display/', component: SFDisplay}
	  ]
});

var app = new Vue({
	router,
	el: '#sport'
});