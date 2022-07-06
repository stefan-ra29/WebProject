Vue.component("add_new_workout", {
	data: function () {
	    return {
	      facility: {},
	      workout: {name : '', workoutType : {}, coach: {}, description: '', picture : '', duration : null, sportFacilityID: ''},
	      types: {},
	      coaches: {}
	    }
	},
	    template: `
    	<div>
            <h1 class="single_facility_header">Novi trening</h1>
            <form id="form" class="registration_form">
                <table>
                    <tr><td>*Naziv</td><td><input type="text" name="name" v-model = "workout.name"></td></tr>
                    <tr><td>*Link za logo</td><td><input type="text" name="logo" v-model = "workout.picture"></td></tr>
                    <tr>
                        <td>*Tip treninga</td>
                        <td v-if="this.types != undefined">
                            <select v-model="workout.workoutType.type">
                                <option v-for="t in types">{{t.type}}</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Trener</td>
                        <td v-if="this.coaches != undefined">
                            <select v-model="workout.coach">
                                <option v-bind:value="c" v-for="c in coaches">{{c.firstName}} {{c.lastName}}</option>
                            </select>
                        </td>
                    </tr>
                    <tr><td>Trajanje u minutima</td><td><input type="number" name="duration" v-model = "workout.duration" min="30" max="120"></td></tr>
                    <tr><td>Opis</td><td><input type="text" name="description" v-model = "workout.description"></td></tr>
                    <tr><td></td><td><input type="submit" v-on:click = "receiveFormData" value="Dodaj"></td></tr>
                </table>
            </form>
    	</div>
    	`,
    mounted () {
        var id = localStorage.getItem("facilityID")
        axios
        .get("rest/facilities/get_one",
        { params : {
            id : id
        }})
        .then(response => {
            this.facility = response.data
            this.workout.sportFacilityID = this.facility.id
        });

        this.addTypes()
        this.addCoaches()
    },
    methods: {
        receiveFormData: function(e){
            e.preventDefault()

            if(this.workout.name.trim() == "" || this.workout.picture.trim() == "" || this.workout.workoutType.type == undefined ){
                alert("Morate popuniti polja oznacena zvezdicom!")
                e.preventDefault()
            }
            else{
                this.workout.name = this.workout.name.trim()
                this.workout.picture = this.workout.picture.trim()
                this.workout.description = this.workout.description.trim()

                axios
                .post("rest/workouts/create_workout", this.workout)
                .then(response => {
                    var isAcceptable = response.data

                    if(isAcceptable == false){
                        alert("Ovo ime je vec iskorisceno!")
                    }
                    else{
                        alert("Uspesno ste dodali trening!")
                        router.push('/managers_facility')
                    }
                });
            }
        },
        addTypes : function(e){
            axios
            .get("rest/workouts/get_types")
            .then(response => {this.types = response.data});
        },
        addCoaches : function(e){
            axios
            .get("rest/coaches/get_all")
            .then(response => {this.coaches = response.data});
        }
	}
});