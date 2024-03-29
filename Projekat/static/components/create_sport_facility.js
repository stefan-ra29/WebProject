Vue.component("create_facility", {
	data: function () {
	    return {
	        facility: {name: '', type: '', location: {}, logo: ''},
            name: null,
            type: {type: ''},
            location: {street: '', streetNumber: '', city: '', postalCode: '', longditude: 45.5, latitude: 102.5},
            startHour: '',
            endHour: '',
            logo: null,
            managers: {},
            manager: null
	    }
	},
	    template: `
    	<div class="registration_wrap">
    	    <navbar/>

    	    <h1>DODAVANJE NOVOG OBJEKTA</h1>
    		<form id="form"  class="registration_form">
				<table v-if="this.managers.length !== 0">
				    <tr><td>Naziv</td><td><input type="text" name="name" v-model = "name"></td></tr>
				    <tr><td>Tip</td><td><input type="text" name="type" v-model = "type.type"></td></tr>
					<tr><td>Ulica</td><td><input type="text" name="street" v-model = "location.street"></td></tr>
					<tr><td>Broj</td><td><input type="text" name="streetNum" v-model = "location.streetNumber"></td></tr>
					<tr><td>Grad</td><td><input type="text" name="city" v-model = "location.city"></td></tr>
					<tr><td>Postanski broj</td><td><input type="text" name="postalCode" v-model = "location.postalCode"></td></tr>
					<tr> <td>Pocetak radnog vremena</td> <td> U <input type="number" v-model="startHour" min="0" max="23"> sati</td> </tr>
					<tr> <td>Kraj radnog vremena</td> <td> U <input type="number" v-model="endHour" min="0" max="23"> sati</td> </tr>
					<tr><td>Logo</td><td><input type="file" id="file" ref="file" v-on:change="onChangeFileUpload()"/></td></tr>
					<tr v-if="this.managers.length !== 0">
                                            <td>Menadzer</td>
                                            <td>
                                                <select v-model="manager" id="managerSelect" style="width: 170px; height: 22px">
                                                    <option v-for="m in managers">{{m}}</option>
                                                </select>
                                            </td>
                    </tr>
					<tr><td></td><td><input type="submit" v-on:click = "receiveFormData" value="Dodaj"></td></tr>
				</table>
				<div v-else>
                	<p>Nema slobodnih menadzera! Kreirajte novog!</p>
                	<input type="submit" v-on:click = "createNewManager" value="Kreiraj menadzera">
                </div>
			</form>

    	</div>
    	`,
    mounted () {

        axios
            .get('rest/managers/getAvailableManagers')
            .then(response => {this.managers = response.data});


        var sel = document.getElementById('managerSelect')
        sel.selectedIndex = 0
    },
    methods: {
        receiveFormData: function(e) {
            e.preventDefault();
            if(this.name === "" || this.type === "" || this.logo === "" || this.location.street === "" || this.location.streetNumber === ""
            || this.location.city === "" || this.location.postalCode === "" || this.startHour === "" || this.endHour === "") {
                alert("Morate popuniti sva polja!")
                e.preventDefault()
            }

            else{
                axios
                    .post('rest/facilities/createNew/', {
                        name: this.name,
                        logo: this.logo,
                        type: this.type,
                        location: this.location },
                        { params: {
                            manager: this.manager,
                            startHour: this.startHour,
                            endHour: this.endHour
                            } })
                    .then(response => (this.checkResponse(response, e) ))
            }
        },

        createNewManager: function(e) {
            e.preventDefault()
            localStorage.setItem("facilityCreationInProcess", '1')
            router.push("/managerRegistration")
        },

        onChangeFileUpload($event) {
            this.file = this.$refs.file.files[0];
            this.encodeImage(this.file)
        },

        encodeImage(input) {
        	if(input) {
        		const reader = new FileReader()
        		reader.onload = (e) => {
        			this.logo = e.target.result
        		}
        		reader.readAsDataURL(input)
        	}
        },

        checkResponse: function(response, e) {
            if(response.data) {
                alert("Novi objekat uspjesno kreiran!")
                router.push("/");
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