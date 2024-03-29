Vue.component("memberships", {
	data: function () {
	    return {
	      customer: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {}, customerTypeName: ''},
	      currentMembership: {type: '', availableVisits: '', expirationDate: {}},
	      jwt: localStorage.getItem('jwt')
	    }
	},
	    template: `
	    <div style="text-align: center" class="orange_wrap">
	        <navbar/>

	        <h1>CLANARINE</h1>
	        <p v-if="customer.customerTypeName == 'Bronzani'">Bronzani ste korisnik, imate popust od 8%</p>
            <p v-else-if="customer.customerTypeName == 'Srebrni'">Srebrni ste korisnik, imate popust od 12%</p>
            <p v-else-if="customer.customerTypeName == 'Zlatni'">Zlatni ste korisnik, imate popust od 15%</p>
	        <div v-if="currentMembership != null" class="current_membership_info">
	            <p>Trenutno imate aktivnu {{currentMembership.type}} clanarinu i ostalo Vam je {{currentMembership.availableVisits}} ulazaka</p>
                <p>Clanarina Vam istice {{currentMembership.expirationDate.day}}.{{currentMembership.expirationDate.month}}.{{currentMembership.expirationDate.year}}.</p>
	        </div>
	        <div v-else class="current_membership_info">
	            <p>Trenutno nemate aktivnu clanarinu</p>
	        </div>

             <table class="membership_table">
                <tr>
                    <td>
                        <div class="monthly_membership_light">
                            <h2>Mjesecna light</h2>
                            <p>Tip: mjesecna</p>
                            <p>Cijena: 3600 dinara</p>
                            <p v-if="customer.customerTypeName == 'Bronzani'">Imate popust od 8%, cijena za Vas je 3312 dinara</p>
                            <p v-else-if="customer.customerTypeName == 'Srebrni'">Imate popust od 12%, cijena za Vas je 3168 dinara</p>
                            <p v-else-if="customer.customerTypeName == 'Zlatni'">Imate popust od 15%, cijena za Vas je 3060 dinara</p>
                            <p>1 ulazak dnevno</p>
                            <p>Ukupno 30 ulazaka</p>
                            <button v-on:click="monthlyLightSubscribe">Uplati</button>
                        </div>
                    </td>
                    <td>
                        <div class="monthly_membership_premium">
                            <h2>Mjesecna premium</h2>
                            <p>Tip: mjesecna</p>
                            <p>Cijena: 5400 dinara</p>
                            <p v-if="customer.customerTypeName == 'Bronzani'">Imate popust od 8%, cijena za Vas je 4968 dinara</p>
                            <p v-else-if="customer.customerTypeName == 'Srebrni'">Imate popust od 12%, cijena za Vas je 4752 dinara</p>
                            <p v-else-if="customer.customerTypeName == 'Zlatni'">Imate popust od 15%, cijena za Vas je 4590 dinara</p>
                            <p>2 ulaska dnevno</p>
                            <p>Ukupno 60 ulazaka</p>
                            <button v-on:click="monthlyPremiumSubscribe">Uplati</button>
                        </div>
                    </td>
                    <td>
                        <div class="yearly_membership">
                            <h2>Godisnja</h2>
                            <p>Tip: godisnja</p>
                            <p>Cijena: 22000 dinara</p>
                            <p v-if="customer.customerTypeName == 'Bronzani'">Imate popust od 8%, cijena za Vas je 20240 dinara</p>
                            <p v-else-if="customer.customerTypeName == 'Srebrni'">Imate popust od 12%, cijena za Vas je 19360 dinara</p>
                            <p v-else-if="customer.customerTypeName == 'Zlatni'">Imate popust od 15%, cijena za Vas je 18700 dinara</p>
                            <p>1 ulazak dnevno</p>
                            <p>Ukupno 365 ulazaka</p>
                            <button v-on:click="yearlySubscribe">Uplati</button>
                        </div>
                    </td>
                </tr>
             </table>
	    </div>

    	`,
    mounted () {
        axios
        .get("rest/users/getLoggedUser",
                    { params : {
                        jwt : this.jwt,
                        isUserManager : false
                    }})
        .then(response => {this.loadCustomer(response)});

    },
    methods: {

        loadCustomer: function(response){
            this.customer = response.data
            this.loadCurrentMembership()
        },

        loadCurrentMembership() {
            axios
            .post("rest/memberships/getCurrentMembership", this.customer)
            .then(response => (this.currentMembership = response.data))
        },

        monthlyLightSubscribe: function(e){
            e.preventDefault()
            axios
            .post("rest/memberships/addMonthlyLight", this.customer)
            .then(response => (this.successMessage(e) ))

        },

        monthlyPremiumSubscribe: function(e){
            e.preventDefault()
            axios
            .post("rest/memberships/addMonthlyPremium", this.customer)
            .then(response => (this.successMessage(e) ))
        },

        yearlySubscribe: function(e){
            e.preventDefault()
            axios
            .post("rest/memberships/addYearly", this.customer)
            .then(response => (this.successMessage(e) ))
        },

        successMessage: function(e) {
            e.preventDefault()
            alert("Uspjesno ste uplatili clanarinu")
            window.location.reload()
        }

    }
});