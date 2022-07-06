Vue.component("register_manager", {
	data: function () {
	    return {
	      id: '-1',
	      manager: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {} },
	      dateOfBirth: {},
	      isValid: 'true'
	    }
	},
	    template: `
    	<div class="registration_wrap">
    	    <h1>DODAVANJE MENADZERA</h1>
    		<form id="form" class="registration_form">
				<table>
				    <tr><td>Korisnicko ime</td><td><input type="text" name="username" v-model = "manager.username"></td></tr>
				    <tr><td>Lozinka</td><td><input type="password" name="password" v-model = "manager.password"></td></tr>
					<tr><td>Ime</td><td><input type="text" name="firstName" v-model = "manager.firstName"></td></tr>
					<tr><td>Prezime</td><td><input type="text" name="lastName" v-model = "manager.lastName"></td></tr>
					<tr><td>Email</td><td><input type="text" name="email" v-model = "manager.email"></td></tr>
					<tr><td>Pol</td><td><select style="width: 170px" name="sex" id="sex" v-model="manager.gender">
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
            if(this.manager.username === "" || this.manager.password === "" || this.manager.firstName === "" || this.manager.lastName === ""
            || this.manager.email === "" || this.manager.gender === "" || !this.dateOfBirth) {
                alert("Morate popuniti sva polja!")
            }

            else{
                const date = new Date(this.dateOfBirth)
                let month = date.getMonth()
                month = month + 1
                let text = '{ "year" :' + date.getFullYear() + ', "month" : ' + month + ', "day" : ' + date.getDate() + ' }';
                const obj = JSON.parse(text);
                this.manager.dob = obj;

                axios
                    .post('rest/managers/register/', this.manager)
                    .then(response => (this.checkResponse(response, e) ))
            }
        },

        checkResponse: function(response, e) {
            if(!response.data) {
                alert("Korisnicko ime je zauzeto!")
            }
            else{
                alert("Uspjesno ste dodali menadzera!")
                if(localStorage.getItem('facilityCreationInProcess') == '1') {
                    localStorage.setItem("facilityCreationInProcess", '0')
                    router.push("/createFacility");
                }
                else
                router.push("/");
            }
        }

	}

});