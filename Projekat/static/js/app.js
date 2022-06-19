const Register = { template: '<register></register>' }
const Login = {template : '<login></login>'}

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/', name: 'home', component: Register},
		{ path: '/login', component: Login}
	  ]
});

var app = new Vue({
	router,
	el: '#sport'
});