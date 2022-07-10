Vue.component("customer_workout_history", {
	data: function () {
	    return {
	      id: '-1',
	      coach: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {} },
	      coachesWorkoutDTOs: null,
	      jwt: localStorage.getItem('jwt')
	    }
	},
	    template: `
	    <div class="workout_history_wrap">
            <h1>Istorija Vasih treninga u posljednjih mjesec dana</h1>
            <table v-if="coachesWorkoutDTOs != null">
                <tr>
                    <th>Naziv treninga</th>
                    <th>Naziv objekta</th>
                    <th>Datum treninga</th>
                </tr>
                <tr v-for="workout in coachesWorkoutDTOs">
                    <td>{{workout.workoutName}}</td>
                    <td>{{workout.facilityName}}</td>
                </tr>
                <tr v-if="pastMonthWorkoutHistoryDTOs.length == 0">
                    <td colspan='3'> Trenutno niste trener ni na jednom treningu </td>
                </tr>
            </table>
    	</div>
    	`,
    mounted () {
        axios
            .get("rest/users/getLoggedUser",
                        { params : {
                            jwt : this.jwt,
                            isUserManager : false
                        }})
           .then(response => {
                this.customer = response.data
                this.getPastMonthWorkoutHistoryForCustomer()
           });
    },

    methods: {

        getPastMonthWorkoutHistoryForCustomer: function(){
            axios
               .get("rest/workouts/getPastMonthWorkoutHistoryForCustomer",
                           { params : {
                               customerId: this.customer.username
                           }})
              .then(response => {
              this.pastMonthWorkoutHistoryDTOs = response.data
              console.log(this.pastMonthWorkoutHistoryDTOs)
              });
        }

    }

});