Vue.component("sport_facility_display", {
	data: function () {
	    return {
	      facilities: null,
	      facilitiesBeforeSearch: {},
	      facility_types: {},
	      nameSearch: "",
          typeSearch: "",
          locationSearch: "",
	      grade_criteria: "",
          searchHappened: false,
          filterHappened: false,
          filterDone: false,
          filteredFacilities: {},
	      filter : "",
	      sort : "",
	      currently_open: true,
	      isLoggedIn: false,
	      role : window.localStorage.getItem('role'),
	      jwt: window.localStorage.getItem('jwt'),
	      manager : {sportFacilityId: '', username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob : {} },
	      managersFacility : {},
	      openFacilities: false
	    }
	},
	    template: `
    	<div>
    	    <div class="sport_facility_display_header">
    	        <a href="http://localhost:8081/#/coachRegistration" v-if="this.role == 'Administrator'" style="margin-right:10px">Dodaj trenera</a>
    	        <a href="http://localhost:8081/#/managerRegistration" v-if="this.role == 'Administrator'" style="margin-right:10px">Dodaj menadzera</a>
    	        <a href="http://localhost:8081/#/createFacility" v-if="this.role == 'Administrator'" style="margin-right:10px">Dodaj objekat</a>
    	        <a href="http://localhost:8081/#/memberships" v-if="this.role == 'Customer'" style="margin-right:10px">Clanarine</a>
    	        <a href="http://localhost:8081/#/login" v-if="this.jwt == '-1' || this.jwt == null" style="margin-right:10px">Prijavite se</a>
    	        <button v-on:click="logout" v-else>Odjava</button>
    	        <a href="http://localhost:8081/#/userProfile" v-if="this.jwt != '-1' && this.jwt != null" style="margin-right:10px">Vas profil</a>
    	        <a href="http://localhost:8081/#/registration">Registracija</a>
    	    </div>

            <div v-if="this.manager != null && this.manager.sportFacilityId != '' && this.manager.sportFacilityId != null"">
                <button  v-on:click="goToManagersFacility()">{{this.managersFacility.name}}</button>
                <button v-on:click="goToCustomersPage()">Pregled kupaca</button>
                <button v-on:click="goToCoachesPage()">Pregled trenera</button>
            </div>
            <div v-if="this.role == 'Administrator'">
                <button v-on:click="goToUserDisplay()">Korisnici</button>
            </div>

    	    <form class="sport_facility_search_display">
                Naziv: <input type="text" name="nameSearch" v-model="nameSearch">
                Tip: <input type="text" name="typeSearch" v-model="typeSearch">
                Lokacija: <input type="text" name="locationSearch" v-model="locationSearch">
                Ocena:
    	        <select v-model = "grade_criteria" name="grade_select" style="width: 100px; padding:1px">
                        <option value="1-2">1-2</option>
                        <option value="2-3">2-3</option>
                        <option value="3-4">3-4</option>
                        <option value="4-5">4-5</option>
                        <option value="">Sve</option>
                </select>
    	        <input type="submit" value="Pretrazi" v-on:click="searchFacilities" name="search_button">
    	        <button class= "button_icon_style" v-on:click="removeSearch"
    	        v-if=" nameSearch != '' || locationSearch != '' || typeSearch != '' || grade_criteria != ''" >
    	            <i class="fa-solid fa-x"></i>
                </button></br>
    	    </form>

            Sortiraj:
                <select style="width: 195px; padding:1px" name="sort" id="sort" v-model = "sort" @change = "sortFacilities($event)" >
                      <option value="name_increasing">Naziv (A-Z)</option>
                      <option value="name_decreasing">Naziv (Z-A)</option>
                      <option value="location_increasing">Lokacija (A-Z)</option>
                      <option value="location_decreasing">Lokacija (Z-A)</option>
                      <option value="average_grade_increasing">Prosecna ocena (rastuce)</option>
                      <option value="average_grade_decreasing">Prosecna ocena (opadajuce)</option>
                </select>
                <button class= "button_icon_style" v-if=" sort != ''" v-on:click="removeSort"><i class="fa-solid fa-x"></i></button></br>

            Filtriraj:
                <select style="width: 195px; padding:1px" name="filter" id="filter"
                    @change = "filterFacilities($event)" v-model="filter" >
                      <option v-for="type in facility_types" >{{type}}</option>
                </select>
            <button class= "button_icon_style" v-if=" filter != ''" v-on:click="removeFilter"><i class="fa-solid fa-x"></i></button></br>

            <input type="checkbox" id="openFacilities" name="openFacilities" value="openFacilities"
             @change = "getOpenFacilities($event)" v-model="openFacilities" >
            <label for="openFacilities">Trenutno otvoreni objekti</label>

    	    <h1 class="facility_heading">Objekti</h1>

            <div v-for="facility in facilities" class="facility_display_wrap">
                <table class="facility_table_wrap">
                    <tr><th>{{facility.name}}</th>
                        <th rowspan="6"><img :src="facility.logo" class= "image_display"/></th>
                    <tr>
                    <tr v-if="facility.startHour != null && facility.closingHour != null">
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
                    <tr v-if="facility.averageGrade == 0">
                        <td>Prosecna ocena: nema ocenu za sada</td>
                    </tr>
                    <tr v-else>
                        <td>Prosecna ocena: {{facility.averageGrade}}</td>
                    </tr>
                    <tr ><td colspan="2" class="facility_button_row" ><button class="facility_button" v-on:click = "details(facility)">Prikazi detaljnije</button></td></tr>
                </table>

            </div>

    	</div>
    	`,
    mounted () {
        axios
        .get("rest/facilities/get_all")
        .then(response => {
            this.facilities = response.data
            this.facilities.sort((a,b) => b.isOpen - a.isOpen)
            this.facilitiesBeforeSearch = this.facilities
        });

        axios
        .get("rest/facilities/get_facility_types")
        .then(response => {this.facility_types = response.data});

        if(this.role == "Manager"){
            axios
            .get("rest/users/getLoggedUser",
            { params : {
                jwt : this.jwt,
                isUserManager : true
            }})
            .then(response => {this.getFacilityName(response)});
        }
    },
    methods: {
        getFacilityName: function(response){
            this.manager = response.data

             if(this.manager.sportFacilityId != '' && this.manager.sportFacilityId != null){
                axios
                .get("rest/facilities/get_one",
                { params : {
                    id : this.manager.sportFacilityId
                }})
                .then(response => {this.managersFacility = response.data});
            }
        },
        goToManagersFacility : function(e){
            localStorage.setItem("facilityID", this.managersFacility.id)
            router.push('/single_facility')
        },
        previousState: function(e){
            this.facilities = this.facilitiesBeforeSearch
        },
        searchFacilities : function(e){
            e.preventDefault()

            if(this.nameSearch.trim() == "" && this.typeSearch.trim() == "" && this.locationSearch.trim() == "" && this.grade_criteria.trim() == ""){
                return;
            }
            if(this.filterDone == true){
                this.facilities = this.filteredFacilities
            }
            else{
                if(this.searchHappened == true){
                    this.previousState(e)
                }
            }

            this.nameSearch = this.nameSearch.trim()
            this.typeSearch = this.typeSearch.trim()
            this.locationSearch = this.locationSearch.trim()
            axios
            .post("rest/facilities/search", this.facilities,
            { params : {
                nameSearch : this.nameSearch,
                typeSearch : this.typeSearch,
                locationSearch : this.locationSearch,
                gradeCriteria: this.grade_criteria
            }})
            .then(response => {
                this.facilities = response.data
                if(this.openFacilities == true){
                  this.getOpenFacilities(e)
                }
                if(this.sort != ""){
                    this.sortFacilities(e)
                }
                if(this.facilities.length == 0){
                    alert("Nijedan objekat se ne podudara sa pretragom")
                }
                });

            this.searchHappened = true
        },
        sortFacilities(e){
            e.preventDefault()
            axios
            .post("rest/facilities/sort", this.facilities,
            { params : {
                sortBy : this.sort
            }})
            .then(response => {this.facilities = response.data});
        },
        filterFacilities(e){
            e.preventDefault()
            if(this.filterHappened == true){
                this.previousState(e)
            }
            axios
            .post("rest/facilities/filter", this.facilities,
            { params : {
                filterBy : this.filter
            }})

            .then(response => {this.facilities = response.data
                this.filteredFacilities = this.facilities
                if(this.openFacilities == true){
                    this.getOpenFacilities(e)
                }
                 if(this.nameSearch.trim() != "" || this.typeSearch.trim() != "" || this.locationSearch.trim() != "" || this.grade_criteria.trim() != ""){
                     this.filterDone = true;
                     this.searchFacilities(e)
                 }
                 else{
                    this.filterDone = true;

                    if(this.sort != ""){
                         this.sortFacilities(e)
                     }
                 }
                 if(this.facilities.length == 0){
                     alert("Nijedan objekat se ne podudara sa pretragom")
                 }
             });
             this.filterHappened = true
        },
        getOpenFacilities(e){
            e.preventDefault()

            if(this.openFacilities == true){
                axios
                .post("rest/facilities/get_currently_opened_facilities", this.facilities)
                .then(response => {
                    this.facilities = response.data
                    if(this.facilities.length == 0){
                         alert("Nijedan objekat se ne podudara sa pretragom")
                     }});
            }else{
                this.previousState()
                if(this.nameSearch.trim() != "" || this.typeSearch.trim() != "" || this.locationSearch.trim() != "" || this.grade_criteria.trim() != ""){
                    this.searchFacilities(e)
                }
                else if(this.filter != ""){
                    this.filterFacilities(e)
                }
                else if(this.sort != ""){
                    this.sortFacilities(e)
                }
            }
        },
        details : function(facility){
           localStorage.setItem("facilityID", facility.id)
           router.push('/single_facility')
        },

        logout : function(e) {
            localStorage.setItem("role", '');
            localStorage.setItem("jwt", '-1');
            window.location.reload();

        },
        removeSearch: function(e){
            e.preventDefault()
            this.previousState()
            this.nameSearch = ""
            this.locationSearch = ""
            this.typeSearch = ""
            this.grade_criteria = ""

            if(this.filter != ""){
                this.filterFacilities(e)
            }
            else{
                if(this.sort != ""){
                     this.sortFacilities(e)
                 }
                 if(this.openFacilities == true){
                     this.getOpenFacilities(e)
                 }
            }
        },
       removeSort: function(e){
         e.preventDefault()
         this.sort = ""
       },
       removeFilter: function(e){
          e.preventDefault()
          this.previousState()
          this.filter = ""
          this.filterDone = false;

          if(this.nameSearch.trim() != "" || this.typeSearch.trim() != "" || this.locationSearch.trim() != "" || this.grade_criteria.trim() != ""){
              this.searchFacilities(e)
          }
          else{
            if(this.openFacilities == true){
              this.getOpenFacilities(e)
            }
              if(this.sort != ""){
                   this.sortFacilities(e)
               }
          }
       },
       goToUserDisplay: function(e){
            router.push('/users_display')
       },
       goToCustomersPage: function(e){
            localStorage.setItem("facilityID", this.managersFacility.id)
            var sending = false
            localStorage.setItem("managerViewsCoaches", sending)
            router.push('/view_users')
       },
       goToCoachesPage: function(e){
           localStorage.setItem("facilityID", this.managersFacility.id)
           var sending = true
           localStorage.setItem("managerViewsCoaches", sending)
           router.push('/view_users')
      }
	}

});