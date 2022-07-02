Vue.component("sport_facility_display", {
	data: function () {
	    return {
	      facilities: {},
	      criteria : "",
	      searchInput : "",
	      grade_criteria: "",
	      filter : "",
	      sort : ""
	    }
	},
	    template: `
    	<div>
    	    <div class="sport_facility_display_header">
    	        <a href="http://localhost:8081/#/login" style="margin-right:10px">Prijava</a>
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
            <form>Sortiraj:
                <select style="width: 195px; padding:1px" name="sort" id="sort" v-model = "sort" @change = "sortFacilities($event)" >
                                										  <option value="name_increasing">Naziv (A-Z)</option>
                                										  <option value="name_decreasing">Naziv (Z-A)</option>
                                										  <option value="location_increasing">Lokacija (A-Z)</option>
                                										  <option value="location_decreasing">Lokacija (Z-A)</option>
                                										  <option value="average_grade_increasing">Prosecna ocena (rastuce)</option>
                                										  <option value="average_grade_decreasing">Prosecna ocena (opadajuce)</option>
                </select>
            </form>
    	    <h1 class="facility_heading">Objekti</h1>

            <div v-for="facility in facilities" class="facility_display_wrap">
                <table class="facility_table_wrap">
                    <tr><th>{{facility.name}}</th>
                        <th rowspan="6"><img :src="facility.logo" class= "image_display"/></th>
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
                        <td>Prosecna ocena: {{facility.averageGrade}}</td>
                    </tr>
                </table>

            </div>

    	</div>
    	`,
    mounted () {
        axios
        .get("rest/facilities/get_all")
        .then(response => {this.facilities = response.data});
    },
    methods: {
        searchSubmit : function(e){
            e.preventDefault()

            if(this.criteria == "")
                return;

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
        }
	}

});