Vue.component("single_facility_display", {
	data: function () {
	    return {
	      facility : null,
	      isManager : false,
	      canCustomerComment : null,
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
          jwt: localStorage.getItem('jwt'),
          comments: {},
          commentText: '',
          grade: null,
          comment:{ text:'', customerID: '', facilityID: '', grade: '', id:''},
          role : window.localStorage.getItem('role')
	    }
	},
	    template: `
    	<div v-if="facility != null" class="orange_wrap">
    	    <navbar/>

            <h1 class="single_facility_header">{{facility.name}}</h1>
            <button v-if="isManager == true" style="margin-left : 10px" v-on:click="addNewWorkout()" >Dodaj novi trening</button>
            <table class="facility_table_wrap">
                 <tr><th></th>
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
                 <tr v-if="facility.averageGrade == 0">
                     <td>Prosecna ocena: nema ocenu za sada</td>
                 </tr>
                 <tr v-else>
                     <td>Prosecna ocena: {{facility.averageGrade}}</td>
                 </tr>
                 <tr><td colspan="2" ></td></tr>

             </table>
             <h2 class="managers_facility_header">TRENINZI:</h2>

            <div v-if="workouts.length != 0 && workouts.length != null">
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

                <div style="margin-bottom: 10px; margin-left: 10px;">
                    Sortiraj:
                    <select style="width: 195px; padding:1px" name="sort" id="sort" v-model = "sort" @change = "sortWorkouts($event)" >
                          <option value="price_increasing">Doplata (rastuce)</option>
                          <option value="price_decreasing">Doplata (opadajuce)</option>
                    </select>
                    <button class= "button_icon_style" v-if=" sort != ''" v-on:click="removeSort"><i class="fa-solid fa-x"></i></button></br>
                </div>

                <div style="margin-bottom: 10px; margin-left: 10px;">
                    Filtriraj:
                    <select style="width: 195px; padding:1px" name="filter" id="filter"
                        @change = "filterWorkouts($event)" v-model="filter" >
                          <option v-for="type in types" >{{type.type}}</option>
                    </select>
                    <button class= "button_icon_style" v-if=" filter != ''" v-on:click="removeFilter"><i class="fa-solid fa-x"></i></button></br>
                </div>
            </div>

            <div v-else style="text-align: center; color: #f0f0f0;">
                <p style="font-size: 18px;"> Objekat trenutno nema treninga u ponudi </p>
            </div>

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

                     <tr v-if="isFacilityCurrentlyWorking == true && currentMembership != null && customer.username != '' && workout.workoutType.type != 'Personalni trening' && facility.isOpen == true">
                        <button v-on:click="checkInToWorkout(customer.username, workout.id)">Prijavi se na trening</button>
                     </tr>
                     <tr v-if="currentMembership != null && customer.username != '' && workout.workoutType.type == 'Personalni trening' && workout.coachID != '' && facility.isOpen == true">
                        <button v-on:click="SchedulePersonalWorkout(workout.id, workout.name, facility.name, facility.id)">Zakazi personalni trening</button>
                     </tr>

                     <tr v-if="role == 'Administrator'">
                        <td> </td>
                        <td v-if="role == 'Administrator'"> <button  v-on:click = "deleteWorkout(workout.id)"> Obrisi trening </button> </td>
                     </tr>
                 </table>
             </div>

             <h2 class="managers_facility_header">Komentari:</h2>
             <div v-if="comments.length == 0" style=" text-align: center; margin-bottom:10px;" > Nema komentara za ovaj objekat</div>
             <div v-else>
                <div class="single_comment" v-for="comm in comments">
                    <div>
                        <p class="single_comment_user">Korisnik: {{comm.customerID}}</p>
                        <p class="single_comment_grade">Ocena: {{comm.grade}}</p>
                    </div>
                    <p class="single_comment_text"> \" {{comm.text}} \"</p>
                    <div v-if="role == 'Administrator' || role =='Manager'">
                        <p v-if="comm.isApproved == true">Status: Odobren</p>
                        <p v-else>Status: Neodobren</p>
                        <div v-if=" role == 'Administrator'">
                            <button v-on:click="deleteComment(comm.id)">Obrisi</button>
                            <button  v-if="comm.isApproved == false" v-on:click="approveComment(comm)">Odobri</button>
                        </div>

                    </div>
                </div>
             </div>
             <div v-if="canCustomerComment == true ">
                <form class="comment_form">
                    Ostavi komentar:
                    <input type="text" v-model="commentText" style="width:500px; height:100px;" ></br>
                    Oceni od 1 do 5:
                    <input type="number" min="1" max="5" v-model="grade">
                    <input type="submit" name="addComment" value="Dodaj komentar" v-on:click="addComment">
                </form>
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
        this.loadFacility(id)

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
        this.loadComments()
    },
    methods: {
        loadFacility: function(id){
            axios
            .get("rest/facilities/get_one",
            { params : {
                            id : id
                        }})
            .then(response => {
                this.facility = response.data
                this.setIsFacilityCurrentlyWorking(id)
            });
        },
        loadComments: function(){
            axios
            .get("rest/comments/get_comments_for_facility",
            { params : {
                role : localStorage.getItem('role'),
                facilityID : localStorage.getItem("facilityID")
            }})
            .then(response => {this.comments = response.data});
        },

        loadCustomer: function(response){
            this.customer = response.data

            this.checkIfCustomerCanComment()

            this.loadCurrentMembership()
        },
        checkIfCustomerCanComment: function(){
            axios
            .get("rest/comments/check_possible_commenting",
            { params : {
                customerID : this.customer.username,
                facilityID : localStorage.getItem("facilityID")
            }})
            .then(response => {
                this.comment = response.data
                if(this.comment.customerID != ""){
                    this.canCustomerComment = true
                }
            });
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
                    return
                }else if(+this.minPrice > +this.maxPrice){
                    alert("Maksimalna vrednost mora biti veca od minimalne!")
                    return
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
             .then(response => {
              console.log(response.data)
              if(response.data == true){
                alert("Uspjesno ste se prijavili na trening!")
                this.checkIfCustomerCanComment()
              }
              else
                alert("Nemate vise preostalih posjeta u okviru clanarine! Uplatite novu.")
              });


        },

        SchedulePersonalWorkout: function(workoutId, workoutName, facilityName, facilityId){
            localStorage.setItem("schedulingWorkoutId", workoutId)
            localStorage.setItem("schedulingWorkoutName", workoutName)
            localStorage.setItem("schedulingFacilityName", facilityName)
            localStorage.setItem("schedulingFacilityId", facilityId)

            router.push('/schedule_workout')
        },

        deleteWorkout: function(id){

            if(confirm("Da li sigurno zelite da obrisete ovaj trening?"))
            {
                axios
                    .delete("rest/workouts/delete",
                    { params : {
                        workoutId : id
                    }})
                    .then(response => {
                        alert("Uspjesno ste obrisali trening!")
                        window.location.reload();
                    });
            }

        },

        addComment: function(e){
            e.preventDefault()

            if(this.commentText.trim() == ""){
                alert("Test komentara ne moze biti prazan!")
                e.preventDefault()
            }
            else{

                if(this.grade != null && (this.grade < 1 || this.grade >5)){
                    alert("Ocena mora biti od 1 do 5!")
                    e.preventDefault()
                    return
                }
                else if(this.grade == null){
                    alert("Ocena mora biti popunjena!")
                    e.preventDefault()
                    return
                }
                if(!Number.isInteger(parseInt(this.grade))){
                    alert("Morate staviti ceo broj!")
                    e.preventDefault()
                    return
                }

                this.comment.text = this.commentText.trim()
                this.comment.grade = this.grade
                axios
                .put("rest/comments/create_filled_comment", this.comment)
                .then(response => {
                    this.comment = response.data
                    this.canCustomerComment = false
                    aler("Uspesno ste dodali komentar!")
                });
            }
        },
        approveComment(comm){
            axios
            .put("rest/comments/approve_comment", comm)
            .then(response => {
                var changedComment = response.data
                this.loadComments()
                this.loadFacility(changedComment.facilityID)
            });
        },
         deleteComment(id){
             if(confirm("Da li sigurno zelite da obrisete komentar?"))
               {
                   axios
                   .delete("rest/comments/delete",
                   { params : {
                       id : id
                   }})
                   .then(response => {
                       alert("Uspesno ste obrisali komentar!")
                       this.loadComments()
                        this.loadFacility(localStorage.getItem("facilityID"))
                   });
               }
         }
    }
});