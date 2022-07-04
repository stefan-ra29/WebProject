const Register = { template: '<register></register>' }
const Login = {template : '<login></login>'}
const SFDisplay = { template: '<sport_facility_display></sport_facility_display>' }
const UserProfile = {template : '<user_profile></user_profile>'}
const CreateFacility = {template: '<create_facility></create_facility>'}

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/registration', component: Register},
		{ path: '/login', component: Login},
		{ path: '/', name: 'home', component: SFDisplay},
		{ path: '/userProfile', component: UserProfile},
		{ path: '/createFacility', component: CreateFacility}
	  ]
});

var app = new Vue({
	router,
	el: '#sport'
});