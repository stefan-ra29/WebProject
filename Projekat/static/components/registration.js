Vue.component("register", { 
	data: function () {
	    return {
	      id: '-1',
	      customer: {username:'', password: '', firstName: '', lastName: '', gender: null, dob: null},
	      isValid: 'true'
	    }
	},
	    template: ` 
    	<div>
    		<form id="form" >
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
					<tr><td>Datum rodjenja</td><td><input style="width: 165px" type="date" name="dob" v-model = "customer.dob"></td></tr>

					<tr><td><input type="submit" v-on:click = "submitFormData(customer)" value="Registruj se"></td></tr>
				</table>
			</form>
			<button name="facilities" v-on:click = "facilitiesDisplay" >View Facilities</button>
    	</div>		  
    	`,
    mounted () {
    },
    methods: {
        facilitiesDisplay: function(){
            router.push("sf-display/");
        }
	}
    
});