Vue.component("single_facility_display", {
	data: function () {
	    return {
	      facility : null
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


    	</div>
    	`,
    mounted () {
        var id = localStorage.getItem("facilityID")
        axios
        .get("rest/facilities/get_one",
        { params : {
                        id : id
                    }})
        .then(response => {this.facility = response.data});
    },
    methods: {
        previousState: function(e){
            this.facilities_before_checking = this.facilities
        }
    }
});