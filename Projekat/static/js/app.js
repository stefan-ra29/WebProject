const Register = { template: '<register></register>' }

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: Register}
	  ]
});

var app = new Vue({
	router,
	el: '#sport'
});