const Register = { template: '<register></register>' }
const Login = {template : '<login></login>'}
const SFDisplay = { template: '<sport_facility_display></sport_facility_display>' }
const singleSFDisplay = { template: '<single_facility_display></single_facility_display>' }
const UserProfile = {template : '<user_profile></user_profile>'}
const CreateFacility = {template: '<create_facility></create_facility>'}
const RegisterManager = {template: '<register_manager></register_manager>'}
const RegisterCoach = {template: '<register_coach></register_coach>'}
const Memberships = {template: '<memberships></memberships>'}
const AddNewWorkout = { template: '<add_new_workout></add_new_workout>' }
const ChangeWorkout = { template: '<change_workout></change_workout>' }
const AdminUserDisplay = { template: '<users_display></users_display>'}
const ManagerUserDisplay = { template: '<manager_users_display></manager_users_display>'}
const CustomerWorkoutHistory = { template: '<customer_workout_history></customer_workout_history>' }
const ScheduleWorkout = { template: '<schedule_workout></schedule_workout>' }
const CoachWorkoutDisplay = { template: '<coach_workout_display></coach_workout_display>' }
const Navbar = {template: '<navbar></navbar>'}

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
        { path: '/add_new_workout', component: AddNewWorkout},
        { path: '/change_workout', component: ChangeWorkout},
        { path: '/users_display', component: AdminUserDisplay},
        { path: '/view_users', component: ManagerUserDisplay},
        { path: '/customer_workout_history', component: CustomerWorkoutHistory},
        { path: '/schedule_workout', component: ScheduleWorkout},
        { path: '/coach_workout_display', component: CoachWorkoutDisplay}
	  ]
});

var app = new Vue({
	router,
	el: '#sport'
});