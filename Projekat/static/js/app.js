const Register = { template: '<register></register>' }
const Login = {template : '<login></login>'}
const SFDisplay = { template: '<sport_facility_display></sport_facility_display>' }

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: Register},
		{ path: '/login', component: Login},
		{ path: '/sf-display/', component: SFDisplay}
	  ]
});

var app = new Vue({
	router,
	el: '#sport'
});