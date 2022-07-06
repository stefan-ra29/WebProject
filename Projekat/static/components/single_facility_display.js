Vue.component("single_facility_display", {
	data: function () {
	    return {
	      facility : null,
	      workouts: {},
	      isFacilityCurrentlyWorking: null,
	      customer: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {} },
          currentMembership: {type: '', availableVisits: '', expirationDate: {}},
          jwt: localStorage.getItem('jwt')
	    }
	},
	    template: `
    	<div v-if="facility != null" >
            <h1 class="single_facility_header">{{facility.name}}</h1>
            <table class="facility_table_wrap">
                 <tr><th><th>
                     <th rowspan="7"><img :src="facility.logo" class= "single_facility_image_display"/></th>
                 <tr>
                 <tr>
                     <td v-if="facility.startHour.minute < 10">Radno vreme:
                         {{facility.startHour.hour}}:0{{facility.startHour.minute}} - {{facility.closingHour.hour}}:0{{facility.closingHour.minute}}</td>
                     <td v-else>Radno vreme:
                         {{facility.startHour.hour}}:{{facility.startHour.minute}} - {{facility.closingHour.hour}}:0{{facility.closingHour.minute}}
                     </td>
                 </tr>
                 <tr>
                     <td>Adresa: {{facility.location.street}} {{facility.location.streetNumber}}, {{facility.location.city}}</td>
                 </tr>
                 <tr>
                     <td>Tip objekta: {{facility.type.type}}</td>
                 </tr>
                 <tr>
                      <td v-if="facility.isOpen == true">Status: Objekat radi</td>
                      <td v-else>Status: Objekat trenutno ne radi</td>
                  </tr>
                 <tr>
                     <td>Prosecna ocena: {{facility.averageGrade}}</td>
                 </tr>
                 <tr><td colspan="2" ></td></tr>
             </table>
             <h2 class="managers_facility_header">Treninzi:</h2>
             <div v-for="workout in workouts" class="facility_display_wrap">
                 <table class="facility_table_wrap">
                     <tr><th>{{workout.name}}</th>
                         <th rowspan="6"><img :src="workout.picture" class= "managers_facility_workout_image_display"/></th>
                     <tr>
                     <tr>
                         <td>Tip: {{workout.workoutType.type}}</td>
                     </tr>
                     <tr v-if="workout.duration == null || workout.duration == 0">
                         <td>Trajanje trenutno nije definisano</td>
                     </tr>
                     <tr v-else>
                         <td>Trajanje: {{workout.duration}} minuta</td>
                     </tr>
                     <tr v-if="workout.coachID == '' || workout.coachID == null">
                         <td>Trenutno nije postavljen trener.</td>
                     </tr>
                     <tr v-else>
                         <td>Trener: </td>
                     </tr>
                     <tr v-if="workout.description != ''">
                         <td>Opis: {{workout.description}}</td>
                     </tr>
                     <tr v-if="isFacilityCurrentlyWorking == true && currentMembership != null">
                        <button>Prijavi se na trening</button>
                     </tr>
                 </table>
             </div>


    	</div>
    	`,
    mounted () {
        var id = localStorage.getItem("facilityID")
        axios
        .get("rest/facilities/get_one",
        { params : {
                        id : id
                    }})
        .then(response => {
            this.facility = response.data
            this.setIsFacilityCurrentlyWorking(id)

        });

        axios
        .get("rest/workouts/get_workouts_by_facility",
        { params : {
            id : id
        }})
        .then(response => {this.workouts = response.data});

        axios
        .get("rest/users/getLoggedUser",
        { params : {
            jwt : this.jwt,
            isUserManager : false
        }})
        .then(response => {this.loadCustomer(response)});

    },
    methods: {

        loadCustomer: function(response){
            this.customer = response.data
            this.loadCurrentMembership()
        },

        loadCurrentMembership() {
            axios
            .post("rest/memberships/getCurrentMembership", this.customer)
            .then(response => (this.currentMembership = response.data))
        },

        previousState: function(e){
            this.facilities_before_checking = this.facilities
        },

        setIsFacilityCurrentlyWorking(id){
            axios
            .get("rest/facilities/getIsCurrentlyWorking",
            { params : {
                id : id
            }})
            .then(response => { this.isFacilityCurrentlyWorking = response.data });
        }
    }
});