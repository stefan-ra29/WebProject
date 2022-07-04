Vue.component("create_facility", {
	data: function () {
	    return {
            name: null,
            type: null,
            location: null,
            longditude: '',
            logo: null,
            managers: {},
            manager: null
	    }
	},
	    template: `
    	<div class="registration_wrap">
    	    <h1>DODAVANJE NOVOG OBJEKTA</h1>
    		<form id="form" class="registration_form">
				<table>
				    <tr><td>Naziv</td><td><input type="text" name="name" v-model = "name"></td></tr>
				    <tr><td>Tip</td><td><select style="width: 170px" name="type" v-model="type">
                    										  <option value="Teretana">Teretana</option>
                    										  <option value="Studio">Studio</option>
                    										</select>
                    				</td>
                    </tr>
					<tr><td>Geografska sirina</td><td><input type="text" name="longditude" v-model = "longditude"></td></tr>
					<tr><td>Geografska duzina</td><td><input type="text" name="latitude" v-model = "location.latitude"></td></tr>
					<tr><td>Ulica</td><td><input type="text" name="street" v-model = "location.street"></td></tr>
					<tr><td>Broj</td><td><input type="text" name="streetNum" v-model = "location.streetNumber"></td></tr>
					<tr><td>Grad</td><td><input type="text" name="city" v-model = "location.city"></td></tr>
					<tr><td>Postanski broj</td><td><input type="text" name="postalCode" v-model = "location.postalCode"></td></tr>
					<tr><td>Link za logo</td><td><input type="text" name="logo" v-model = "logo"></td></tr>
					<tr v-if="this.managers.length !== 0">
                        <td>Menadzer</td>
                        <td>
                            <select v-model="manager">
                                <option v-for="m in managers">{{m}}</option>
                            </select>
                        </td>
					</tr>
				</table>
			</form>
    	</div>
    	`,
    mounted () {
        this.location = {}
        this.location.longditude = ''
        this.location.latitude = ''
        this.location.street = ''
        this.location.streetNumber = ''
        this.location.city = ''
        this.location.postalCode = ''
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