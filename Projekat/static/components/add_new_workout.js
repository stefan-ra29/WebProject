Vue.component("add_new_workout", {
	data: function () {
	    return {
	      facility: {},
	      workout: {}
	    }
	},
	    template: `
    	<div>
            <h1 class="single_facility_header">Novi trening</h1>
            
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
        addNewWorkout : function(e){
            router.push('/add_new_workout')
        },

        filterFacilities(event){
            event.preventDefault()

            axios
            .get("rest/facilities/filter",
            { params : {
                filterBy : this.filter
            }})

            .then(response => {this.facilities = response.data});
        }
	}
});