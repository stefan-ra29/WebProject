Vue.component("coach_workout_display", {
	data: function () {
	    return {
	      id: '-1',
	      coach: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {} },
	      coachesGroupWorkouts: {},
	      coachesScheduledWorkouts: {},
	      coachesOtherWorkouts: {},
	      jwt: localStorage.getItem('jwt')
	    }
	},
	    template: `
	    <div class="workout_history_wrap">
            <h1>Vasi treninzi</h1>

            <h2>Zakazani personalni treninzi</h2>
                <table v-if="coachesScheduledWorkouts != null">
                <tr>
                    <th> Naziv treninga </th>
                    <th> Naziv objekta </th>
                    <th> Datum treninga </th>
                    <th> Kupac </th>
                    <th> Otkazi </th>
                </tr>
                <tr v-for="workout in coachesScheduledWorkouts">
                    <td>{{workout.name}}</td>
                    <td>{{workout.facilityName}}</td>
                    <td> {{workout.scheduledTime.date.day}}.{{workout.scheduledTime.date.month}}.{{workout.scheduledTime.date.year}}. u
                        {{workout.scheduledTime.time.hour}}:{{workout.scheduledTime.time.minute}}
                    </td>
                    <td>{{workout.customer}}</td>
                    <td> <button v-on:click="cancelScheduledWorkout(workout.id)"> Otkazi </button> </td>
                </tr>
                <tr v-if="coachesScheduledWorkouts == null || coachesScheduledWorkouts.length == 0">
                    <td colspan='3'> Trenutno nemate zakazanih personalnih treninga </td>
                </tr>
            </table>

            <h2>Grupni treninzi</h2>
            <table v-if="coachesGroupWorkouts != null">
                <tr>
                    <th>Naziv treninga</th>
                    <th>Naziv objekta</th>
                </tr>
                <tr v-for="workout in coachesGroupWorkouts">
                    <td>{{workout.name}}</td>
                    <td>{{workout.facilityName}}</td>
                </tr>
                <tr v-if="coachesGroupWorkouts == null || coachesGroupWorkouts.length == 0">
                    <td colspan='3'> Trenutno niste trener ni na jednom grupnom treningu </td>
                </tr>
            </table>

            <h2>Ostali treninzi</h2>
            <table v-if="coachesOtherWorkouts != null">
                <tr>
                    <th>Naziv treninga</th>
                    <th>Naziv objekta</th>
                    <th>Tip treninga</th>
                </tr>
                <tr v-for="workout in coachesOtherWorkouts">
                    <td>{{workout.name}}</td>
                    <td>{{workout.facilityName}}</td>
                    <td>{{workout.type}}</td>
                </tr>
                <tr v-if="coachesGroupWorkouts == null || coachesGroupWorkouts.length == 0">
                    <td colspan='3'> Trenutno niste trener ni na jednom ostalom treningu </td>
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
                this.coach = response.data
                this.getCoachesScheduledWorkouts()
                this.getCoachesGroupWorkouts()
                this.getCoachesOtherWorkouts()
           });
    },

    methods: {

        cancelScheduledWorkout: function(id){

            axios
                .post("rest/workouts/cancelScheduledWorkout",
                {},
                { params : {
                    id: id
                }})
                .then(response => {
                    if(response.data == true)
                        alert("Uspjesno ste otkazali trening!")
                    else
                        alert("Trening mozete otkazate minimalno dva dana unaprijed!")
                });

        },

        getCoachesScheduledWorkouts: function(){
            axios
               .get("rest/workouts/getCoachesScheduledWorkouts",
                           { params : {
                               coachId: this.coach.username
                           }})
              .then(response => { this.coachesScheduledWorkouts = response.data });
        },

        getCoachesGroupWorkouts: function(){
            axios
               .get("rest/workouts/getCoachesGroupWorkouts",
                           { params : {
                               coachId: this.coach.username
                           }})
              .then(response => { this.coachesGroupWorkouts = response.data });
        },

        getCoachesOtherWorkouts: function(){
            axios
               .get("rest/workouts/getCoachesOtherWorkouts",
                           { params : {
                               coachId: this.coach.username
                           }})
              .then(response => { this.coachesOtherWorkouts = response.data });
        },

    }

});