Vue.component("register_coach", {
	data: function () {
	    return {
	      id: '-1',
	      coach: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {} },
	      dateOfBirth: {},
	      isValid: 'true'
	    }
	},
	    template: `
    	<div class="registration_wrap">
    	    <h1>DODAVANJE TRENERA</h1>
    		<form id="form" class="registration_form">
				<table>
				    <tr><td>Korisnicko ime</td><td><input type="text" name="username" v-model = "coach.username"></td></tr>
				    <tr><td>Lozinka</td><td><input type="password" name="password" v-model = "coach.password"></td></tr>
					<tr><td>Ime</td><td><input type="text" name="firstName" v-model = "coach.firstName"></td></tr>
					<tr><td>Prezime</td><td><input type="text" name="lastName" v-model = "coach.lastName"></td></tr>
					<tr><td>Email</td><td><input type="text" name="email" v-model = "coach.email"></td></tr>
					<tr><td>Pol</td><td><select style="width: 170px" name="sex" id="sex" v-model="coach.gender">
										  <option value="male">Muski</option>
										  <option value="female">Zenski</option>
										  <option value="else">Drugo</option>
										</select></td></tr>
                    <tr><td>Datum rodjenja</td><td><input style="width: 165px" type="date" name="dob" v-model = "dateOfBirth"></td></tr>
					<tr><td></td><td><input type="submit" v-on:click = "receiveFormData" value="Dodaj"></td></tr>
				</table>
			</form>
    	</div>
    	`,
    mounted () {
    },
    methods: {
        receiveFormData: function(e) {
            e.preventDefault();
            if(this.coach.username === "" || this.coach.password === "" || this.coach.firstName === "" || this.coach.lastName === ""
            || this.coach.email === "" || this.coach.gender === "" || !this.dateOfBirth) {
                alert("Morate popuniti sva polja!")
            }

            else{
                const date = new Date(this.dateOfBirth)
                let month = date.getMonth()
                month = month + 1
                let text = '{ "year" :' + date.getFullYear() + ', "month" : ' + month + ', "day" : ' + date.getDate() + ' }';
                const obj = JSON.parse(text);
                this.coach.dob = obj;

                axios
                    .post('rest/coaches/register/', this.coach)
                    .then(response => (this.checkResponse(response, e) ))
            }
        },

        checkResponse: function(response, e) {
            if(!response.data) {
                alert("Korisnicko ime je zauzeto!")
            }
            else{
                alert("Uspjesno ste dodali trenera!")
                router.push("/");
            }
        }

	}

});