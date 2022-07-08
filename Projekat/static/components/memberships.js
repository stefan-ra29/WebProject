Vue.component("memberships", {
	data: function () {
	    return {
	      customer: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {} },
	      currentMembership: {type: '', availableVisits: '', expirationDate: {}},
	      jwt: localStorage.getItem('jwt')
	    }
	},
	    template: `
	    <div style="text-align: center">
	        <h1>Clanarine</h1>
	        <div v-if="currentMembership.type != ''">
	            <p>Trenutno imate aktivnu {{currentMembership.type}} clanarinu i ostalo Vam je {{currentMembership.availableVisits}} ulazaka</p>
                <p>Clanarina Vam istice {{currentMembership.expirationDate.day}}.{{currentMembership.expirationDate.month}}.{{currentMembership.expirationDate.year}}.</p>
	        </div>
	        <div v-else>
	            <p>Trenutno nemate aktivnu clanarinu</p>
	        </div>

             <table class="membership_table">
                <tr>
                    <td>
                        <div class="monthly_membership_light">
                            <h2>Mjesecna light</h2>
                            <p>Tip: mjesecna</p>
                            <p>Cijena: 30 eura</p>
                            <p>1 ulazak dnevno</p>
                            <p>Ukupno 30 ulazaka</p>
                            <button v-on:click="monthlyLightSubscribe">Uplati</button>
                        </div>
                    </td>
                    <td>
                        <div class="monthly_membership_premium">
                            <h2>Mjesecna premium</h2>
                            <p>Tip: mjesecna</p>
                            <p>Cijena: 45 eura</p>
                            <p>2 ulaska dnevno</p>
                            <p>Ukupno 60 ulazaka</p>
                            <button v-on:click="monthlyPremiumSubscribe">Uplati</button>
                        </div>
                    </td>
                    <td>
                        <div class="yearly_membership">
                            <h2>Godisnja</h2>
                            <p>Tip: godisnja</p>
                            <p>Cijena: 180 eura</p>
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
        }

    }
});