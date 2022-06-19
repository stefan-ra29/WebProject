Vue.component("register", { 
	data: function () {
	    return {
	      id: '-1',
	      customer: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {} },
	      dateOfBirth: {},
	      isValid: 'true'
	    }
	},
	    template: ` 
    	<div class="registration_wrap">
    	    <h1>Registruj se</h1>
    		<form id="form" class="registration_form">
				<table>
				    <tr><td>Korisnicko ime</td><td><input type="text" name="username" v-model = "customer.username"></td></tr>
				    <tr><td>Lozinka</td><td><input type="password" name="password" v-model = "customer.password"></td></tr>
					<tr><td>Ime</td><td><input type="text" name="firstName" v-model = "customer.firstName"></td></tr>
					<tr><td>Prezime</td><td><input type="text" name="lastName" v-model = "customer.lastName"></td></tr>
					<tr><td>Email</td><td><input type="text" name="email" v-model = "customer.email"></td></tr>
					<tr><td>Pol</td><td><select style="width: 170px" name="sex" id="sex" v-model="customer.gender">
										  <option value="male">Muski</option>
										  <option value="female">Zenski</option>
										  <option value="else">Drugo</option>
										</select></td></tr>
                    <tr><td>Datum rodjenja</td><td><input style="width: 165px" type="date" name="dob" v-model = "dateOfBirth"></td></tr>
					<tr><td></td><td><input type="submit" v-on:click = "receiveFormData" value="Registruj se"></td></tr>
				</table>
			</form>
    	</div>		  
    	`,
    mounted () {
    },
    methods: {
        receiveFormData: function(e) {
            e.preventDefault();
            if(this.customer.username === "" || this.customer.password === "" || this.customer.firstName === "" || this.customer.lastName === ""
            || this.customer.email === "" || this.customer.gender === "" || !this.dateOfBirth) {
                alert("Morate popuniti sva polja!")
            }

            const date = new Date(this.dateOfBirth)
            let month = date.getMonth()
            month = month + 1
            let text = '{ "year" :' + date.getFullYear() + ', "month" : ' + month + ', "day" : ' + date.getDate() + ' }';
            const obj = JSON.parse(text);
            this.customer.dob = obj;

            axios
                .post('rest/customers/register/', this.customer)
                .then(response => (this.checkResponse(response, e) ))

        },

        checkResponse: function(response, e) {
            if(!response.data) {
                alert("Korisnicko ime je zauzeto!")
            }
            else{
                alert("Uspjesno ste se registrovali!")
                router.push('/login')
            }
        },

        facilitiesDisplay: function(){
            router.push("sf-display/");
        }
	}
    
});