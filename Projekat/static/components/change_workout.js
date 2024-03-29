Vue.component("change_workout", {
	data: function () {
	    return {
	      workout: {name : '', workoutType : {}, coachID: '', description: '', picture : '', duration : null, sportFacilityID: '', supplement : null},
	      types: {},
	      coaches: {},
	      selectedCoachName: {}
	    }
	},
	    template: `
    	<div>
    	    <navbar/>

            <h1 class="single_facility_header">Izmeni trening</h1>
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
                            <form>
                                <select v-model="workout.coachID">
                                    <option v-bind:value="c.username" v-for="(c, index) in coaches">{{c.firstName}} {{c.lastName}}</option>
                                </select>
                                <input type="reset" value="Ukloni" v-if="workout.coachID != '' && workout.coachID != null" v-on:click="removeCoach" />
                            </form>
                        </td>
                    </tr>
                    <tr><td>Trajanje u minutima</td><td><input type="number" name="duration" v-model = "workout.duration" min="30" max="120"></td></tr>
                    <tr><td>Doplata</td><td><input type="number" name="supplement" v-model = "workout.supplement" min="0" max="2000"></td></tr>
                    <tr><td>Opis</td><td><input type="text" name="description" v-model = "workout.description"></td></tr>
                    <tr><td></td><td><input type="submit" v-on:click = "receiveFormData" value="Dodaj"></td></tr>
                </table>
            </form>
    	</div>
    	`,
    mounted () {
        var id = localStorage.getItem("workoutChangeID")
        axios
        .get("rest/workouts/get_workout_by_id",
        { params : {
            id : id
        }})
        .then(response => {
            this.workout = response.data
            if(this.workout.duration == 0){
                this.workout.duration = null}
            if(this.workout.supplement == 0){
                this.workout.supplement = null}
        });

        this.addTypes()
        this.addCoaches()
    },
    methods: {
        receiveFormData: function(e){
            e.preventDefault()
            if(this.workout.duration == "")
                this.workout.duration = null

            if(this.workout.supplement == "")
                this.workout.supplement = null

            if(this.workout.name.trim() == "" || this.workout.picture.trim() == "" || this.workout.workoutType.type == undefined ){
                alert("Morate popuniti polja oznacena zvezdicom!")
                e.preventDefault()
            }
            else{

                if(this.workout.duration != null && (this.workout.duration < 30 || this.workout.duration >120)){
                    alert("Trajanje treninga mora biti u rasponu od 30 do 120 minuta!")
                    e.preventDefault()
                    return
                }


                if( this.workout.supplement != null && (this.workout.supplement < 0 || this.workout.supplement >2000
                    && this.workout.supplement !=1500 && this.workout.supplement !=2000)){
                    alert("Vrednost doplate nije validna!")
                    e.preventDefault()
                    return;
                }

                this.workout.name = this.workout.name.trim()
                this.workout.picture = this.workout.picture.trim()
                this.workout.description = this.workout.description.trim()

                axios
                .put("rest/workouts/change_workout", this.workout)
                .then(response => {
                    var isAcceptable = response.data

                    if(isAcceptable == false){
                        alert("Postoji greska!")
                    }
                    else{
                        alert("Uspesno ste promenili trening!")
                        router.push('/single_facility')
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
        },
        removeCoach: function(e){
            e.preventDefault
            this.workout.coachID = ""
        }
	}
});