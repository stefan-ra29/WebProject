Vue.component("sport_facility_display", {
	data: function () {
	    return {
	      facilities: {}
	    }
	},
	    template: `
    	<div>
    	    <h1>Svi objekti:</h1>
    		<table>
                <tr>
                    <th>Ime sportskog objekta</th>
                    <th>Tip sportskog objekta</th>
                    <th>Adresa</th>
                    <th>Vreme otvaranja</th>
                    <th>Vreme zatvaranja</th>

                </tr>
                <tr v-for="facility in facilities">
                    <td>{{facility.name}}</td>
                    <td>{{facility.type.type}}</td>
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

	}

});