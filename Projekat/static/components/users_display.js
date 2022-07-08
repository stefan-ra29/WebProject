Vue.component("users_display", {
	data: function () {
	    return {
	      users: null,
          coachesAndManagers: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {}, role: '', points: 0 },
          customers: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {}, role: '', points: 0 },

	      workoutsBeforeSearch: {},
	      coaches: {},
	      criteria : "",
	      minPrice: null,
	      maxPrice: null,
	      searchHappened : false,
	      sort: "",
	      types: {},
	      filter: "",
	      filterHappened: false,
	      filteredWorkouts: {},
	      filterDone: false,
	      workouts: {},
	      isFacilityCurrentlyWorking: null,
          jwt: localStorage.getItem('jwt')
	    }
	},
	    template: `
    	<div v-if="users != null" >
            <h1 class="single_facility_header">Korisnici</h1>
            <table>
                 <tr>
                     <th>Ime</th>
                     <th>Prezime</th>
                     <th>Tip korisnika</th>
                     <th>E-mail</th>
                     <th>Broj bodova</th>
                 <tr>
                 <tr v-for="user in users">
                     <td>{{user.firstName}}</td>
                     <td>{{user.lastName}}</td>
                     <td>{{user.role}}</td>
                     <td>{{user.email}}</td>
                     <td v-if="user.points == undefined">Tip korisnika nema poene</td>
                     <td v-else>{{user.points}}</td>
                 </tr>
             </table>

    	</div>
    	`,
    mounted () {

        axios
        .get("rest/customers/get_all")
        .then(response => {
            this.customers = response.data

            axios
            .get("rest/users/get_coaches_and_managers")
            .then(response => {
                this.coachesAndManagers = response.data
                this.users = this.customers.concat(this.coachesAndManagers)
            });
        });
    },
    methods: {


    }
});