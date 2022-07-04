Vue.component("single_facility_display", {
	data: function () {
	    return {
	      facility : null
	    }
	},
	    template: `
    	<div v-if="facility != null">
            <p>{{facility.name}}</p>
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