const Register = { template: '<register></register>' }
const Login = {template : '<login></login>'}
const SFDisplay = { template: '<sport_facility_display></sport_facility_display>' }
const singleSFDisplay = { template: '<single_facility_display></single_facility_display>' }
const UserProfile = {template : '<user_profile></user_profile>'}
const CreateFacility = {template: '<create_facility></create_facility>'}
const RegisterManager = {template: '<register_manager></register_manager>'}
const RegisterCoach = {template: '<register_coach></register_coach>'}
const Memberships = {template: '<memberships></memberships>'}
const ManagersSFDisplay = { template: '<managers_facility_display></managers_facility_display>' }
const AddNewWorkout = { template: '<add_new_workout></add_new_workout>' }

const router = new VueRouter({
	mode: 'hash',
	  routes: [
		{ path: '/registration', component: Register},
		{ path: '/login', component: Login},
		{ path: '/', name: 'home', component: SFDisplay},
		{ path: '/single_facility', component: singleSFDisplay},
		{ path: '/userProfile', component: UserProfile},
		{ path: '/createFacility', component: CreateFacility},
		{ path: '/managerRegistration', component: RegisterManager},
		{ path: '/coachRegistration', component: RegisterCoach},
		{ path: '/memberships', component: Memberships},
        { path: '/managers_facility', component: ManagersSFDisplay},
        { path: '/add_new_workout', component: AddNewWorkout}
	  ]
});

var app = new Vue({
	router,
	el: '#sport'
});