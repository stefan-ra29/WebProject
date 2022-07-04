Vue.component("create_facility", {
	data: function () {
	    return {
	        facility: {name: '', type: '', location: {}, logo: ''},
            name: null,
            type: {type: ''},
            location: {street: '', streetNumber: '', city: '', postalCode: '', longditude: 45.5, latitude: 102.5},
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
				    <tr><td>Tip</td><td><input type="text" name="type" v-model = "type.type"></td></tr>
					<tr><td>Ulica</td><td><input type="text" name="street" v-model = "location.street"></td></tr>
					<tr><td>Broj</td><td><input type="text" name="streetNum" v-model = "location.streetNumber"></td></tr>
					<tr><td>Grad</td><td><input type="text" name="city" v-model = "location.city"></td></tr>
					<tr><td>Postanski broj</td><td><input type="text" name="postalCode" v-model = "location.postalCode"></td></tr>
					<tr><td>Link za logo</td><td><input type="text" name="logo" v-model = "logo"></td></tr>
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
            if(this.name === "" || this.type === "" || this.logo === "" || this.location.street === "" || this.location.streetNumber === ""
            || this.location.city === "" || this.location.postalCode === "" ) {
                alert("Morate popuniti sva polja!")
                e.preventDefault()
            }

            else{
                axios
                    .post('rest/facilities/createNew/', {
                        name: this.name,
                        logo: this.logo,
                        type: this.type,
                        location: this.location
                    })
                    .then(response => (this.checkResponse(response, e) ))
            }


        },

        checkResponse: function(response, e) {
            if(response.data) {
                alert("Novi objekat uspjesno kreiran!")
                router.push("sf-display/");
            }
            else{
                alert("Dogodila se greska!")
            }
        },

        facilitiesDisplay: function(){
            router.push("sf-display/");
        }
	}

});