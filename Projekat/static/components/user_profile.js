Vue.component("user_profile", {
	data: function () {
	    return {
	      id: '-1',
	      customer: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {} },
	      dateOfBirth: null,
	      jwt: localStorage.getItem('jwt'),
	      editMode: false
	    }
	},
	    template: `
    	<div class="registration_wrap">
    	    <h1>VAŠ PROFIL</h1>
    		<form class="registration_form">
				<table>
				    <tr><td>Korisnicko ime</td><td><input type="text" name="username" disabled v-model = "customer.username"></td></tr>
				    <tr><td>Lozinka</td><td><input type="password" :disabled="!editMode" name="password" v-model = "customer.password"></td></tr>
					<tr><td>Ime</td><td><input type="text" :disabled="!editMode" name="firstName" v-model = "customer.firstName"></td></tr>
					<tr><td>Prezime</td><td><input type="text" :disabled="!editMode" name="lastName" v-model = "customer.lastName"></td></tr>
					<tr><td>Email</td><td><input type="text" :disabled="!editMode" name="email" v-model = "customer.email"></td></tr>
					<tr><td>Pol</td><td><select style="width: 170px" :disabled="!editMode" name="sex" id="sex" v-model="customer.gender">
										  <option value="male">Muski</option>
										  <option value="female">Zenski</option>
										  <option value="else">Drugo</option>
										</select></td></tr>
                    <tr><td>Datum rodjenja</td><td><input style="width: 165px" :disabled="!editMode" type="date" name="dob" v-model = "dateOfBirth"></td></tr>
					<tr>
					<td> <button v-on:click="setEditMode" v-if="editMode == false"> Izmjeni informacije </button>  </td>
					<td> <input type="submit" v-if="editMode == true" v-on:click = "updateProfile" value="Sacuvaj izmjene"> </td>
					</tr>
				</table>
			</form>
    	</div>
    	`,
    mounted () {
        axios
            .get("rest/users/getLoggedUser",
                        { params : {
                            jwt : this.jwt,
                            isUserManager : false
                        }})
           .then(response => {this.loadUserData(response)});
    },
    methods: {

        loadUserData: function(response){
            this.customer = response.data
            year = response.data.dob.year
            month = (response.data.dob.month < 10 ? "0" : "") + response.data.dob.month;
            day = (response.data.dob.day < 10 ? "0" : "") + response.data.dob.day;
            this.dateOfBirth = year + "-" + month + "-" + day
        },

        setEditMode: function(e) {
            e.preventDefault()
            this.editMode = true
        },

        updateProfile: function(e) {
            e.preventDefault();
            if(this.customer.username === "" || this.customer.password === "" || this.customer.firstName === "" || this.customer.lastName === ""
            || this.customer.email === "" || this.customer.gender === "" || !this.dateOfBirth) {
                alert("Morate popuniti sva polja!")
            }

            else{
             const date = new Date(this.dateOfBirth)
             let month = date.getMonth()
             month = month + 1
             let text = '{ "year" :' + date.getFullYear() + ', "month" : ' + month + ', "day" : ' + date.getDate() + ' }';
             const obj = JSON.parse(text);
             this.customer.dob = obj;

             axios
                 .put('rest/users/update/', this.customer)
                 .then(response => (this.checkResponse(response, e) ))

             this.editMode = false
           }


        },

        checkResponse: function(response, e) {
            if(!response.data) {
                alert("Korisnicko ime je zauzeto!")
            }
            else{
                e.preventDefault();
                alert("Uspjesno ste izmjenili svoje informacije!")
            }
        },

        facilitiesDisplay: function(){
            router.push("sf-display/");
        }
	}

});