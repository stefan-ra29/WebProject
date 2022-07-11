Vue.component("navbar", {
	data: function () {
	    return {
	      role : window.localStorage.getItem('role'),
          jwt: window.localStorage.getItem('jwt')
	    }
	},
	    template: `
    	    <div class="sport_facility_display_header">
                <a href="http://localhost:8081/#/coachRegistration" v-if="this.role == 'Administrator'" style="margin-right:10px">Dodaj trenera</a>
                <a href="http://localhost:8081/#/managerRegistration" v-if="this.role == 'Administrator'" style="margin-right:10px">Dodaj menadzera</a>
                <a href="http://localhost:8081/#/createFacility" v-if="this.role == 'Administrator'" style="margin-right:10px">Dodaj objekat</a>
                <a href="http://localhost:8081/#/coach_workout_display" v-if="this.role == 'Coach'" style="margin-right:10px">Vasi treninzi</a>
                <a href="http://localhost:8081/#/customer_workout_history" v-if="this.role == 'Customer'" style="margin-right:10px">Istorija treninga</a>
                <a href="http://localhost:8081/#/memberships" v-if="this.role == 'Customer'" style="margin-right:10px">Clanarine</a>
                <a href="http://localhost:8081/#/login" v-if="this.jwt == '-1' || this.jwt == null" style="margin-right:10px">Prijavite se</a>
                <button v-on:click="logout" v-else>Odjava</button>
                <a href="http://localhost:8081/#/userProfile" v-if="this.jwt != '-1' && this.jwt != null" style="margin-right:10px">Vas profil</a>
                <a href="http://localhost:8081/#/registration">Registracija</a>
            </div>
    	`,
    mounted () {

    },
    methods: {
        logout : function(e) {
            localStorage.setItem("role", '');
            localStorage.setItem("jwt", '-1');
            router.push("/");
            window.location.reload();

        }
    }

});