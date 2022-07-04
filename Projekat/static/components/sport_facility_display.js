Vue.component("sport_facility_display", {
	data: function () {
	    return {
	      facilities: null,
	      facilities_before_checking: {},
	      facility_types: {},
	      criteria : "",
	      searchInput : "",
	      grade_criteria: "",
	      filter : "",
	      sort : "",
	      currently_open: true,
	      isLoggedIn: false,
	      role : window.localStorage.getItem('role'),
	      jwt: window.localStorage.getItem('jwt')
	    }
	},
	    template: `
    	<div>
    	    <div class="sport_facility_display_header">
    	        <a href="http://localhost:8081/#/createFacility" v-if="this.role == 'Administrator'" style="margin-right:10px">Dodaj objekat</a>
    	        <a href="http://localhost:8081/#/login" v-if="this.jwt == '-1' || this.jwt == null" style="margin-right:10px">Prijavite se</a>
    	        <button v-on:click="logout" v-else>Odjava</button>
    	        <a href="http://localhost:8081/#/userProfile" v-if="this.role == 'Customer'" style="margin-right:10px">Vas profil</a>
    	        <a href="http://localhost:8081/#/registration">Registracija</a>
    	    </div>

    	    <form class="sport_facility_search_display">
    	        <select style="width: 125px; padding:1px" name="search_criteria" id="search_criteria" v-model = "criteria">
                      <option value="name">Naziv</option>
                      <option value="type">Tip</option>
                      <option value="location">Lokacija</option>
                      <option value="average_grade">Prosecna ocena</option>
                </select>
    	        <input v-if="criteria != 'average_grade'" type="text" name="search" v-model = "searchInput">
    	        <select v-else v-model = "grade_criteria" name="grade_select" style="width: 100px; padding:1px">
                        <option value="1-2">1-2</option>
                        <option value="2-3">2-3</option>
                        <option value="3-4">3-4</option>
                        <option value="4-5">4-5</option>
                </select>

    	        <input type="submit" value="Pretrazi" v-on:click="searchSubmit" name="search_button">
    	    </form>

            Sortiraj:
                <select style="width: 195px; padding:1px" name="sort" id="sort" v-model = "sort" @change = "sortFacilities($event)" >
                      <option value="name_increasing">Naziv (A-Z)</option>
                      <option value="name_decreasing">Naziv (Z-A)</option>
                      <option value="location_increasing">Lokacija (A-Z)</option>
                      <option value="location_decreasing">Lokacija (Z-A)</option>
                      <option value="average_grade_increasing">Prosecna ocena (rastuce)</option>
                      <option value="average_grade_decreasing">Prosecna ocena (opadajuce)</option>
                </select></br>

            Filtriraj:
                <select style="width: 195px; padding:1px" name="filter" id="filter"
                    @change = "filterFacilities($event)" v-model=filter >
                      <option v-for="type in facility_types" >{{type}}</option>
                </select></br>

            <input type="checkbox" id="openFacilities" name="openFacilities" value="openFacilities"
             @change = "getOpenFacilities($event)">
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
                    <tr>
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
        .then(response => {this.facilities = response.data});

        axios
        .get("rest/facilities/get_facility_types")
        .then(response => {this.facility_types = response.data});

    },
    methods: {
        previousState: function(e){
            this.facilities_before_checking = this.facilities
        },
        searchSubmit : function(e){
            e.preventDefault()

            if(this.criteria == "")
                return;

            var copied_facilities = this.facilities

            axios
            .get("rest/facilities/search",
            { params : {
                criteria : this.criteria,
                searchInput : this.searchInput,
                gradeCriteria: this.grade_criteria
            }})

            .then(response => {this.facilities = response.data});
        },

        sortFacilities(event){
            event.preventDefault()

            axios
            .get("rest/facilities/sort",
            { params : {
                sortBy : event.target.value
            }})

            .then(response => {this.facilities = response.data});
        },
        filterFacilities(event){
            event.preventDefault()

            axios
            .get("rest/facilities/filter",
            { params : {
                filterBy : this.filter
            }})

            .then(response => {this.facilities = response.data});
        },
        getOpenFacilities(event){
            event.preventDefault()

            if(event.target.checked == true){
                axios
                .get("rest/facilities/get_currently_opened_facilities")

                .then(response => {this.facilities = response.data});
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

        }
	}

});