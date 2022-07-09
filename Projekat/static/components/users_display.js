Vue.component("users_display", {
	data: function () {
	    return {
	      users: null,
          coachesAndManagers: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {}, role: '', points: 0 },
          customers: {username:'', password: '', firstName: '', lastName: '', email: '', gender: '', dob: {}, role: '', points: 0 },
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
    	<div v-if="users != null" >
            <h1 class="single_facility_header">Korisnici</h1>

            <form class="sport_facility_search_display">
                Ime: <input type="text" name="firstNameSearch" v-model="firstNameSearch">
                Prezime: <input type="text" name="lastNameSearch" v-model="lastNameSearch">
                Korisnicko ime: <input type="text" name="usernameSearch" v-model="usernameSearch">

                <input type="submit" value="Pretrazi" v-on:click="searchUsers" name="search_button">
                <button class= "button_icon_style" v-on:click="removeSearch"
                v-if=" firstNameSearch != '' || lastNameSearch != '' || usernameSearch != ''" >
                    <i class="fa-solid fa-x"></i>
                </button></br>
            </form>

             Sortiraj:
            <select style="width: 195px; padding:1px" name="sort" id="sort" v-model = "sort" @change = "sortUsers($event)" >
                  <option value="firstName_increasing">Ime (A-Z)</option>
                  <option value="firstName_decreasing">Ime (Z-A)</option>
                  <option value="lastName_increasing">Prezime (A-Z)</option>
                  <option value="lastName_decreasing">Prezime (Z-A)</option>
                  <option value="username_increasing">Korisnicko ime (A-Z)</option>
                  <option value="username_decreasing">Korisnicko ime (Z-A))</option>
                  <option value="points_increasing">Broj poena (rastuce)</option>
                  <option value="points_decreasing">Broj poena (opadajuce)</option>
            </select>
            <button class= "button_icon_style" v-if=" sort != ''" v-on:click="removeSort"><i class="fa-solid fa-x"></i></button></br>

            Filtriraj:
            <select style="width: 195px; padding:1px" name="filter" id="filter"
                @change = "filterUsers($event)" v-model="filter" >
                  <option value="Customer" >Kupci</option>
                  <option value="Manager" >Menadzeri</option>
                  <option value="Coach" >Treneri</option>
            </select>
            <button class= "button_icon_style" v-if=" filter != ''" v-on:click="removeFilter"><i class="fa-solid fa-x"></i></button></br>

            <div v-if="filter == 'Customer'">
                Tip kupca:
                <select style="width: 195px; padding:1px" name="filter_customers" id="filter_customers"
                    @change = "filterCustomerType($event)" v-model="filterCustomers" >
                      <option value="bronze_customer" >Bronzani kupac</option>
                      <option value="silver_customer" >Srebrni kupac</option>
                      <option value="gold_customer" >Zlatni kupac</option>
                </select>
                <button class= "button_icon_style" v-if=" filterCustomers != ''" v-on:click="removeCustomerFilter"><i class="fa-solid fa-x"></i></button></br>
            </div>

            <table>
                 <tr>
                     <th>Ime</th>
                     <th>Prezime</th>
                     <th>Korisnicko ime</th>
                     <th>Tip korisnika</th>
                     <th>E-mail</th>
                     <th>Broj bodova</th>
                 <tr>
                 <tr v-for="user in users">
                     <td>{{user.firstName}}</td>
                     <td>{{user.lastName}}</td>
                     <td>{{user.username}}</td>
                     <td v-if="user.role == 'Customer'">Kupac</td>
                     <td v-else-if="user.role == 'Coach'">Trener</td>
                     <td v-else>Menadzer</td>
                     <td>{{user.email}}</td>
                     <td v-if="user.points == undefined">Tip korisnika nema poene</td>
                     <td v-else>{{user.points}}</td>
                 </tr>
             </table>

    	</div>
    	`,
    mounted () {

        axios
        .get("rest/customers/get_all")
        .then(response => {
            this.customers = response.data

            axios
            .get("rest/users/get_coaches_and_managers")
            .then(response => {
                this.coachesAndManagers = response.data
                this.users = this.customers.concat(this.coachesAndManagers)
                this.usersBeforeSearch = this.users
            });
        });
    },
    methods: {
        previousState: function(e){
            this.users = this.usersBeforeSearch
        },
        searchUsers: function(e){
            e.preventDefault()

            if(this.firstNameSearch.trim() == "" && this.lastNameSearch.trim() == "" && this.usernameSearch.trim() == ""){
                return;
            }

            if(this.filterDone == true){
                this.users = this.filteredUsers
            }
            else{
                if(this.searchHappened == true && this.pointSortHappened != true){
                    this.previousState(e)
                }
            }

//            if(this.searchHappened == true && this.pointSortHappened != true){
//                this.previousState(e)
//            }

            if(this.sort != "" && this.pointSortHappened != true){
//                if(this.sort == "points_increasing" || this.sort == "points_decreasing"){
//                    this.pointSortHappened = true;
//                }
                this.sortUsers(e)
            }

            this.firstNameSearch = this.firstNameSearch.trim()
            this.lastNameSearch = this.lastNameSearch.trim()
            this.usernameSearch = this.usernameSearch.trim()
            axios
            .post("rest/users/search", this.users,
            { params : {
                firstNameSearch : this.firstNameSearch,
                lastNameSearch : this.lastNameSearch,
                usernameSearch : this.usernameSearch
            }})
            .then(response => {
                this.users = response.data

                if(this.users.length == 0){
                    alert("Nijedan objekat se ne podudara sa pretragom")
                }
                this.givebackCustomerAttributes(e)
                this.pointSortHappened = false
            });

            this.searchHappened = true
            this.pointSortHappened = false
        },

        removeSearch: function(e){
            e.preventDefault()
            this.previousState()
            this.firstNameSearch = ""
            this.lastNameSearch = ""
            this.usernameSearch = ""
            this.searchHappened = false
            if(this.filter != ""){
                this.filterUsers(e)
            }
            else{
                if(this.sort != ""){
                     this.sortUsers(e)
                 }
            }
        },

        sortUsers(e){
            e.preventDefault()
            if(this.sort == "points_increasing" || this.sort == "points_decreasing"){
                if(this.filterHappened == true && this.filter == "Customer"){
                    axios
                    .post("rest/customers/sort", this.users,
                    { params : {
                        sortBy : this.sort
                    }})
                    .then(response => {
                        this.users = response.data
                    });
                }
                else if(this.filterHappened == true && (this.filter == "Manager" || this.filter == "Coach")){
                    return
                }
                else{
                    axios
                    .post("rest/customers/sort", this.customers,
                    { params : {
                        sortBy : this.sort
                    }})
                    .then(response => {
                        this.customers = response.data
                        this.users = this.customers.concat(this.coachesAndManagers)
                        if(this.searchHappened = true){
                            this.pointSortHappened = true
                            this.searchUsers(e)
                        }
                    });
                }

            }else{
                axios
                .post("rest/users/sort", this.users,
                { params : {
                    sortBy : this.sort
                }})
                .then(response => {
                    this.users = response.data
                    this.givebackCustomerAttributes(e)
                });
            }
        },

        removeSort: function(e){
            e.preventDefault()
            this.sort = ""
        },
        givebackCustomerAttributes: function(e){
            e.preventDefault()
            for (const user of this.users) {
                if(user.role == "Customer"){
                    var customer = this.customers.find(element => element.username == user.username);
                    user.points = customer.points
                }
            }
        },
        filterUsers(e){
           e.preventDefault()

          if(this.filterHappened == true){
              this.previousState(e)
          }
           axios
           .post("rest/users/filter", this.users,
           { params : {
               filterBy : this.filter
           }})

           .then(response => {this.users = response.data

                this.filteredUsers = this.users
                this.givebackCustomerAttributes(e)
                this.filterDone = true;

               if(this.searchHappened == true){
                   this.searchUsers(e)
               }
               else{
                  if(this.sort != ""){
                       this.sortUsers(e)
                   }
               }
           if(this.users.length == 0){
                alert("Nijedan trening se ne podudara sa pretragom")
            }
           });
           this.filterHappened = true
           this.filterDone = false
       },

       removeFilter: function(e){
           e.preventDefault()
           this.previousState()
           this.filter = ""
           this.filterCustomers = ""
           this.filterHappened = false
           this.filterDone = false
           if(this.searchHappened == true){
               this.searchUsers(e)
           }else{
               if(this.sort != ""){
                  this.sortUsers(e)
               }
           }
      },

      filterCustomerType: function(e){},
      removeCustomerFilter: function(e){
           e.preventDefault()
           this.previousState()
           this.filterCustomers = ""
      }
    }
});