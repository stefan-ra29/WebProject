const Register = { template: '<register></register>' }
const Login = {template : '<login></login>'}
const SFDisplay = { template: '<sport_facility_display></sport_facility_display>' }

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/registration', component: Register},
		{ path: '/login', component: Login},
		{ path: '/', name: 'home', component: SFDisplay}
	  ]
});

var app = new Vue({
	router,
	el: '#sport'
});