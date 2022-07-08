Vue.component("single_facility_display", {
	data: function () {
	    return {
	      facility : null,
	      isManager : false,
	      workouts: {},
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
	      customer: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {} },
          currentMembership: {type: '', availableVisits: '', expirationDate: {}},
          jwt: localStorage.getItem('jwt')
	    }
	},
	    template: `
    	<div v-if="facility != null" >
            <h1 class="single_facility_header">{{facility.name}}</h1>
            <button v-if="isManager == true" style="margin-left : 10px" v-on:click="addNewWorkout()" >Dodaj novi trening</button>
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

            <form v-if="workouts.length != null" class="sport_facility_search_display">
                <select style="width: 125px; padding:1px" name="search_criteria" id="search_criteria" v-model = "criteria">
                      <option value="withoutSupplement">Bez doplate</option>
                      <option value="withSupplement">Sa doplatom</option>
                </select>
                <div v-if="criteria == 'withSupplement'">
                    Od: <input  type="number" name="price" min="0" max="2000" v-model = "minPrice">
                    Do: <input  type="number" name="price" min="0" max="2000" v-model = "maxPrice">
                </div>
                <input type="submit" value="Pretrazi" v-on:click="searchWorkouts" name="search_button">
                <button class= "button_icon_style" v-if=" criteria != ''" v-on:click="removeSearch"><i class="fa-solid fa-x"></i></button></br>

            </form>

            Sortiraj:
            <select style="width: 195px; padding:1px" name="sort" id="sort" v-model = "sort" @change = "sortWorkouts($event)" >
                  <option value="price_increasing">Doplata (rastuce)</option>
                  <option value="price_decreasing">Doplata (opadajuce)</option>
            </select>
            <button class= "button_icon_style" v-if=" sort != ''" v-on:click="removeSort"><i class="fa-solid fa-x"></i></button></br>

            Filtriraj:
            <select style="width: 195px; padding:1px" name="filter" id="filter"
                @change = "filterWorkouts($event)" v-model="filter" >
                  <option v-for="type in types" >{{type.type}}</option>
            </select>
            <button class= "button_icon_style" v-if=" filter != ''" v-on:click="removeFilter"><i class="fa-solid fa-x"></i></button></br>

            <div v-for="(workout, index) in workouts" class="facility_display_wrap">
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
                         <td v-if="coaches != undefined">Trener: {{coaches[index]}}</td>
                     </tr>
                     <tr v-if="workout.description != ''">
                         <td>Opis: {{workout.description}}</td>
                     </tr>
                     <tr v-if="workout.supplement != null & workout.supplement != 0">
                         <td>Postoji mogucnost doplate u iznosu od {{workout.supplement}} dinara.</td>
                     </tr>
                     <tr><td><button v-if="isManager == true" v-on:click = "changeWorkout(workout.id)">Izmeni</button></td></tr>

                     <tr v-if="isFacilityCurrentlyWorking == true && currentMembership != null && customer.username != ''">
                        <button v-on:click="checkInToWorkout(customer.username, workout.id)">Prijavi se na trening</button>
                     </tr>

                 </table>
             </div>


    	</div>
    	`,
    mounted () {
        var id = localStorage.getItem("facilityID")
        if(localStorage.getItem('role') == "Manager"){
            axios
            .get("rest/users/getLoggedUser",
            { params : {
                jwt : window.localStorage.getItem('jwt'),
                isUserManager : true
            }})
            .then(response => {
                var manager = response.data
                if(manager.sportFacilityId == id){
                    this.isManager = true
                }
            });
        }
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
        .then(response => {
            this.workouts = response.data
            this.workoutsBeforeSearch = this.workouts
            axios
            .post("rest/workouts/get_coaches_names", this.workouts)
            .then(response => {this.coaches = response.data});
        });
        this.addTypes()

        if(localStorage.getItem('role') == "Customer"){
            axios
            .get("rest/users/getLoggedUser",
            { params : {
                jwt : this.jwt,
                isUserManager : false
            }})
            .then(response => {this.loadCustomer(response)});
        }


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
            this.workouts = this.workoutsBeforeSearch
        },

        addNewWorkout : function(e){
                    router.push('/add_new_workout')
        },

        changeWorkout: function(workoutID){
            localStorage.setItem("workoutChangeID", workoutID)
            router.push('/change_workout')
        },

        searchWorkouts : function(e){
            e.preventDefault()
            if(this.criteria == "")
                return;

            if(this.criteria == "withSupplement"){
                if(this.minPrice == "" || this.maxPrice == ""){
                    alert("Morate uneti i minimalnu i maksimalnu vrednost!")
                }else if(+this.minPrice > +this.maxPrice){
                    alert("Maksimalna vrednost mora biti veca od minimalne!")
                }
            }
            if(this.filterDone == true){
                this.workouts = this.filteredWorkouts
            }
            else{
                if(this.searchHappened == true){
                    this.previousState(e)
                }
            }

            axios
            .post("rest/workouts/search", this.workouts,
            { params : {
                criteria : this.criteria,
                minPrice : this.minPrice,
                maxPrice: this.maxPrice
            }})
            .then(response => {
                this.workouts = response.data
                if(this.sort != ""){
                    this.sortWorkouts(e)
                }
                if(this.workouts.length == 0){
                     alert("Nijedan trening se ne podudara sa pretragom")
                 }
                });

            this.searchHappened = true
        },

        sortWorkouts(e){
            e.preventDefault()
            axios
            .post("rest/workouts/sort", this.workouts,
            { params : {
                sortBy : this.sort
            }})
            .then(response => {this.workouts = response.data});
        },

         filterWorkouts(e){
             e.preventDefault()

            if(this.filterHappened == true){
                this.previousState(e)
            }
             axios
             .post("rest/workouts/filter", this.workouts,
             { params : {
                 filterBy : this.filter
             }})

             .then(response => {this.workouts = response.data

             this.filteredWorkouts = this.workouts

             if(this.criteria != ""){
                 this.filterDone = true;
                 this.searchWorkouts(e)
             }
             else{
                this.filterDone = true;
                if(this.sort != ""){
                     this.sortWorkouts(e)
                 }
             }
             if(this.workouts.length == 0){
                  alert("Nijedan trening se ne podudara sa pretragom")
              }
             });
             this.filterHappened = true
         },

          addTypes : function(e){
              axios
              .get("rest/workouts/get_types")
              .then(response => {this.types = response.data});
          },

          removeSort: function(e){
            e.preventDefault()
            this.sort = ""
          },

          removeSearch: function(e){
            e.preventDefault()
            this.previousState()
            this.criteria = ""
            if(this.filter != ""){
                this.filterWorkouts(e)
            }
            else{
                if(this.sort != ""){
                     this.sortWorkouts(e)
                 }
            }
          },

          removeFilter: function(e){
            e.preventDefault()
            this.previousState()
            this.filter = ""
            this.filterDone = false;
            if(this.criteria != ""){
                this.searchWorkouts(e)
            }else{
            if(this.sort != ""){
               this.sortWorkouts(e)
                }
            }
          },

        setIsFacilityCurrentlyWorking(id){
            axios
            .get("rest/facilities/getIsCurrentlyWorking",
            { params : {
                id : id
            }})
            .then(response => { this.isFacilityCurrentlyWorking = response.data });
        },

        checkInToWorkout: function(customerUsername, workoutId){

            axios
            .post("rest/workouts/checkInToWorkout",
               {},
               { params : {
                   customerId : customerUsername,
                   workoutID: workoutId
               }})
             .then(response => { });

            alert("Uspjesno ste se prijavili na trening!")
        }
    }
});