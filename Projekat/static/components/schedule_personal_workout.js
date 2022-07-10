Vue.component("schedule_workout", {
	data: function () {
	    return {
            customer: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {}, customerTypeName: ''},
            facility: {},
            workout: {},
            hoursToSubtract: null,
            minutesToSubtract: null,
            workoutName: localStorage.getItem("schedulingWorkoutName"),
            workoutId: localStorage.getItem("schedulingWorkoutId"),
            facilityName: localStorage.getItem("schedulingFacilityName"),
            facilityId: localStorage.getItem("schedulingFacilityId"),
            scheduleDate: {},
            scheduleHours: null,
            scheduleMinutes: null,
            minDate: '',
            jwt: localStorage.getItem('jwt')

	    }
	},
	    template: `
            <div class="login_wrap">
                <h1>Zakazivanje treninga {{workoutName}} u {{facilityName}}</h1>

                    <table>
                        <tr></tr>
                        <tr><td>Datum</td><td><input type="date" name="date" :min="minDate" v-model = "scheduleDate"></td></tr>
                        <tr><td>Sati</td><td><input type="number" :min="facility.startHour.hour" :max="facility.closingHour.hour - hoursToSubtract"  name="hours" v-model = "scheduleHours"></td></tr>
                        <tr v-if="scheduleHours == facility.closingHour.hour - hoursToSubtract"><td>Minuta</td><td><input type="number" :min="0" :max="59 - minutesToSubtract" name="hours" v-model = "scheduleMinutes"></td></tr>
                        <tr v-else><td>Minuta</td><td><input type="number" :min="0" :max="59" name="hours" v-model = "scheduleMinutes"></td></tr>
                        <tr> <td></td> <td> <button  v-on:click="scheduleWorkout"> Zakazi trening </button> </td> </tr>
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
            console.log(this.customer)
            });


        axios
            .get("rest/facilities/get_one",
            { params : {
                            id : this.facilityId
                        }})
            .then(response => {
                this.facility = response.data
                console.log(this.facility)
            });


        axios
                .get("rest/workouts/get_workout_by_id",
                { params : {
                    id : this.workoutId
                }})
                .then(response => {
                    this.workout = response.data
                    this.calculateHoursAndMinutesToSubtract()
                });


        var dtToday = new Date();
        dtToday.setDate(dtToday.getDate() + 1)

        var month = dtToday.getMonth() + 1;
        var day = dtToday.getDate();
        var year = dtToday.getFullYear();

        if(month < 10)
            month = '0' + month.toString();
        if(day < 10)
            day = '0' + day.toString();

        this.minDate = year + '-' + month + '-' + day;

    },
    methods: {

        calculateHoursAndMinutesToSubtract: function(){
            if(this.workout.duration < 60){
                this.hoursToSubtract = 1
                this.minutesToSubtract = this.workout.duration
            }
            else if(this.workout.duration > 60 && this.workout.duration < 120) {
                this.hoursToSubtract = 2
                this.minutesToSubtract = this.workout.duration
            }
        },

        scheduleWorkout: function(e){
            e.preventDefault()
            axios
                .post("rest/workouts/scheduleWorkout",
                   {},
                   { params : {
                       customerId : this.customer.username,
                       workoutId: this.workoutId,
                       scheduleDate: this.scheduleDate,
                       scheduleHours: this.scheduleHours,
                       scheduleMinutes: this.scheduleMinutes
                   }})
                 .then(response => {
                  alert("Uspjesno ste zakazali trening!")
                  });
        }

	}

});