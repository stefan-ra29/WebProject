Vue.component("sport_facility_display", {
	data: function () {
	    return {
	      facilities: {},
	      criteria : "",
	      searchInput : "",
	      grade_criteria: "",
	      filter : ""
	    }
	},
	    template: `
    	<div>
    	    <div style="margin-bottom:10px">
    	        <a href="http://localhost:8081/#/login" style="margin-right:10px">Prijava</a>
    	        <a href="http://localhost:8081/#/registration">Registracija</a>
    	    </div>
    	    <form>
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

    	    <h1>Objekti:</h1>
    		<table>
                <tr>
                    <th>Ime sportskog objekta</th>
                    <th>Tip sportskog objekta</th>
                    <th>Prosecna ocena</th>
                    <th>Adresa</th>
                    <th>Vreme otvaranja</th>
                    <th>Vreme zatvaranja</th>

                </tr>
                <tr v-for="facility in facilities" v-bind:key="facility.id">
                    <td>{{facility.name}}</td>
                    <td>{{facility.type.type}}</td>
                    <td>{{facility.averageGrade}}</td>
                    <td>{{facility.location.street}} {{facility.location.streetNumber}} {{facility.location.city}}</td>
                    <td v-if="facility.startHour.minute < 10 ">
                        {{facility.startHour.hour}}:0{{facility.startHour.minute}}
                    </td>
                    <td v-else>
                        {{facility.startHour.hour}}:{{facility.startHour.minute}}
                    </td>
                    <td v-if="facility.closingHour.minute < 10 ">
                        {{facility.closingHour.hour}}:0{{facility.closingHour.minute}}
                    </td>
                    <td v-else>
                        {{facility.closingHour.hour}}:{{facility.closingHour.minute}}
                    </td>
                </tr>
            </table>
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
            axios
            .get("rest/facilities/search",
            { params : {
                criteria : this.criteria,
                searchInput : this.searchInput,
                gradeCriteria: this.grade_criteria
            }})

            .then(response => {this.facilities = response.data});
        }
	}

});