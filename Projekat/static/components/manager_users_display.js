Vue.component("manager_users_display", {
	data: function () {
	    return {
	      displayCoaches: null,
          coaches: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {}, role: ''},
          customers: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {}, role: '', points: 0, customerTypeName: '' },

          firstNameSearch: '',
          lastNameSearch: '',
          usernameSearch: '',
	      usersBeforeSearch: {},
          searchHappened : false,
          sort: "",
          pointSortHappened: false,
          filterCustomers: '',
          filter: "",
	      filterHappened: false,
	      filteredUsers: {},
          filterDone: false,
          jwt: localStorage.getItem('jwt')
	    }
	},
	    template: `
    	<div v-if="customers != null && coaches != null" >
    	    <navbar/>

            <h1 v-if="this.displayCoaches == 'false'" class="single_facility_header">Kupci</h1>
            <h1 v-else class="single_facility_header">Treneri</h1>

            <table v-if="this.displayCoaches == 'false'">
                 <tr>
                     <th>Ime</th>
                     <th>Prezime</th>
                     <th>Korisnicko ime</th>
                     <th>E-mail</th>
                     <th >Tip kupca</th>
                     <th >Broj bodova</th>
                 <tr>
                 <tr v-for="customer in customers">

                     <td>{{customer.firstName}}</td>
                     <td>{{customer.lastName}}</td>
                     <td>{{customer.username}}</td>
                     <td>{{customer.email}}</td>
                     <td v-if="customer.customerTypeName == null">Nije kategorisan/a</td>
                     <td v-else>{{customer.customerTypeName}}</td>
                     <td v-if="customer.points == 0">Korisnik nema poene</td>
                     <td v-else>{{customer.points}}</td>
                 </tr>
             </table>
             <table v-else>
                  <tr>
                      <th>Ime</th>
                      <th>Prezime</th>
                      <th>Korisnicko ime</th>
                      <th>E-mail</th>
                  <tr>
                  <tr v-for="coach in coaches">
                       <td>{{coach.firstName}}</td>
                       <td>{{coach.lastName}}</td>
                       <td>{{coach.username}}</td>
                       <td>{{coach.email}}</td>
                   </tr>
             </table>

    	</div>
    	`,
    mounted () {
        var id = localStorage.getItem("facilityID")
        this.displayCoaches = localStorage.getItem("managerViewsCoaches")
        if(this.displayCoaches == "true"){

            axios
            .get("rest/coaches/get_coaches_from_facility",
            { params : {
                id : id
            }})
            .then(response => {
                this.coaches = response.data
                console.log(this.coaches)
            });
        }
        else if(this.displayCoaches == "false"){

            axios
            .get("rest/customers/get_customers_from_facility",
            { params : {
                id : id
            }})
            .then(response => {
                this.customers = response.data
                console.log(this.customers)
            });
        }

    },
    methods: {
    }
});