Vue.component("customer_workout_history", {
	data: function () {
	    return {
	      id: '-1',
	      customer: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {} },
	      pastMonthWorkoutHistoryDTOs: null,
	      jwt: localStorage.getItem('jwt')
	    }
	},
	    template: `
	    <div class="workout_history_wrap">
	        <navbar/>
	        
            <h1>Istorija Vasih treninga u posljednjih mjesec dana</h1>
            <table v-if="pastMonthWorkoutHistoryDTOs != null">
                <tr>
                    <th>Naziv treninga</th>
                    <th>Naziv objekta</th>
                    <th>Datum treninga</th>
                </tr>
                <tr v-for="workoutHistory in pastMonthWorkoutHistoryDTOs">
                    <td>{{workoutHistory.workoutName}}</td>
                    <td>{{workoutHistory.facilityName}}</td>
                    <td>{{workoutHistory.startTime.date.day}}.{{workoutHistory.startTime.date.month}}.{{workoutHistory.startTime.date.year}}.</td>
                </tr>
                <tr v-if="pastMonthWorkoutHistoryDTOs.length == 0">
                    <td colspan='3'> Nemate treninga u posljednjih mjesec dana </td>
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