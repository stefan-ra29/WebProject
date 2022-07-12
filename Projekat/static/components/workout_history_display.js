Vue.component("customer_workout_history", {
	data: function () {
	    return {
	      id: '-1',
	      customer: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {} },
	      pastMonthWorkoutHistoryDTOs: null,
	      workoutsBeforeSearch: {},
	      jwt: localStorage.getItem('jwt'),

          criteria: '',
	      nameSearch: '',
	      price_criteria: '',
	      minPrice: '',
	      maxPrice: '',
	      today:  '2022-1-1',
	      monthEarlier: '2022-1-1',
	      minDate: "",
	      maxDate: "",
          searchHappened: ''

	    }
	},
	    template: `
	    <div class="workout_history_wrap orange_wrap">
	        <navbar/>
	        
            <h1>Istorija Vasih treninga u posljednjih mjesec dana</h1>

            <form class="sport_facility_search_display">
                 <select style="width: 160px; padding:1px" name="criteria" id="criteria" v-model = "criteria">
                      <option value="facilityName">Po nazivu objekta</option>
                      <option value="withoutSupplement">Treninzi bez doplate</option>
                      <option value="withSupplement">Treninzi sa doplatom</option>
                      <option value="workoutDate">Po datumu treninga</option>
                </select>
                <input v-if="criteria == 'facilityName'" type="text" name="nameSearch" v-model="nameSearch">
                <div v-if="criteria == 'withSupplement'">
                    Od cene: <input  type="number" name="price" min="0" max="2000" v-model = "minPrice">
                    Do cene: <input  type="number" name="price" min="0" max="2000" v-model = "maxPrice">
                </div>
                <div v-if="criteria == 'workoutDate'">
                    Od datuma: <input  type="date" id="startDate" :max="today" :min="monthEarlier" v-model = "minDate">
                    Do datuma: <input  type="date" id="finishDate" :max="today" :min="monthEarlier" v-model = "maxDate">
                </div>
                <input type="submit" value="Pretrazi" v-on:click="searchWorkouts" name="search_button">
                <button class= "button_icon_style" v-on:click="removeSearch"
                v-if=" criteria != ''" >
                    <i class="fa-solid fa-x"></i>
                </button></br>
            </form>


            <table v-if="pastMonthWorkoutHistoryDTOs != null">
                <tr>
                    <th>Naziv treninga</th>
                    <th>Naziv objekta</th>
                    <th>Datum treninga</th>
                    <th>Mogucnost doplate</th>
                </tr>
                <tr v-for="workoutHistory in pastMonthWorkoutHistoryDTOs">
                    <td>{{workoutHistory.workoutName}}</td>
                    <td>{{workoutHistory.facilityName}}</td>
                    <td>{{workoutHistory.startTime.date.day}}.{{workoutHistory.startTime.date.month}}.{{workoutHistory.startTime.date.year}}.</td>
                    <td v-if="workoutHistory.supplement == 0">Nema doplate</td>
                    <td v-else>{{workoutHistory.supplement}}</td>
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

           this.defineDates()
    },

    methods: {
        previousState: function(e){
            this.pastMonthWorkoutHistoryDTOs = this.workoutsBeforeSearch
        },
        defineDates: function(e){
           var todaysDate = new Date()
           this.today = todaysDate.toISOString().slice(0, 10)
           console.log(this.today)
           this.monthEarlier = new Date(todaysDate.getFullYear(), todaysDate.getMonth()-1, todaysDate.getDate()).toISOString().slice(0, 10)
           console.log(this.monthEarlier)
        },
        getPastMonthWorkoutHistoryForCustomer: function(){
            axios
               .get("rest/workouts/getPastMonthWorkoutHistoryForCustomer",
                           { params : {
                               customerId: this.customer.username
                           }})
              .then(response => {
              this.pastMonthWorkoutHistoryDTOs = response.data
              this.workoutsBeforeSearch = this.pastMonthWorkoutHistoryDTOs
              });
        },

        searchWorkouts: function(e){
            e.preventDefault()
            if(this.criteria == "")
                return;

            if(this.criteria == "facilityName" && this.nameSearch.trim() == ""){
                return
            }

            if(this.criteria == "withSupplement"){
                if(this.minPrice == "" || this.maxPrice == ""){
                    alert("Morate uneti i minimalnu i maksimalnu vrednost!")
                    return
                }else if(+this.minPrice > +this.maxPrice){
                    alert("Maksimalna vrednost mora biti veca od minimalne!")
                    return
                }
                else if((+this.minPrice <0 || +this.minPrice >2000) || (+this.maxPrice <0 || +this.maxPrice >2000)){
                    alert("Vrednosti cene treba da su u rasponu do 2000 dinara!")
                    return
                }
            }
            if(this.criteria == "workoutDate"){
                if(this.minDate == "" || this.maxDate == ""){
                    alert("Morate uneti i pocetni i krajnji datum!")
                    return
                }else if(this.minDate > this.maxDate){
                    alert("Vrednost pocetnog datuma mora biti manja od vrednosti krajnjeg datuma!")
                    return
                }else if((this.minDate < this.monthEarlier || this.minDate > this.today) ||
                    (this.maxDate < this.monthEarlier|| this.maxDate > this.today)){
                     alert("Datum mora biti u proteklih mesec dana!")
                     return
                 }
            }
//            if(this.filterDone == true){
//                this.workouts = this.filteredWorkouts
//            }
//            else{
                if(this.searchHappened == true){
                    this.previousState(e)
                }
//            }

            axios
            .post("rest/history_workouts/search", this.pastMonthWorkoutHistoryDTOs,
            { params : {
                criteria : this.criteria,
                minPrice : this.minPrice,
                maxPrice: this.maxPrice,
                minDate: this.minDate,
                maxDate: this.maxDate,
                nameSearch : this.nameSearch.trim()
            }})
            .then(response => {
                this.pastMonthWorkoutHistoryDTOs = response.data
//                if(this.sort != ""){
//                    this.sortWorkouts(e)
//                }
                if(this.pastMonthWorkoutHistoryDTOs.length == 0){
                     alert("Nijedan trening se ne podudara sa pretragom")
                 }
                });

            this.searchHappened = true
        },

        removeSearch: function(e){
            e.preventDefault()
            this.previousState()
            this.criteria = ""
            this.minPrice = ""
            this.maxPrice = ""
            this.minDate = ""
            this.maxDate = ""
        }

    }

});