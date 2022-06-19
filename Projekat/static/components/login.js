Vue.component("login", {
	data: function () {
	    return {
            username: '',
            password: '',
            test: ''
	    }
	},
	    template: `

    	<div class="login_wrap">
    	    <h1>PRIJAVA</h1>
            <form class="login_form">
                <table>
                    <tr><td>Korisnicko ime</td><td><input type="text" name="username" v-model = "username"></td></tr>
                    <tr><td>Lozinka</td><td><input type="password" name="password" v-model = "password"></td></tr>
                    <tr> <td></td> <td> <input type="submit" v-on:click = "receiveFormData" value="Prijavi se"> </td> </tr>
                </table>
            </form>
    	</div>
    	`,
    mounted () {
        localStorage.setItem("role", '');
        localStorage.setItem("jwt", '-1');
    },
    methods: {
        receiveFormData: function(e) {
            e.preventDefault()
            axios
            .post('rest/users/login', {}, {params: {username: this.username, password: this.password }})
            .then(response => (this.Login(response, e)))
        },

        Login: function(response, e) {
            if (JSON.parse(JSON.stringify(response.data))[0] === "0") {
                alert("Pogresan username i/ili password.")
            }
            else {
            	localStorage.setItem('jwt', JSON.parse(JSON.stringify(response.data))[0]);
            	localStorage.setItem("role", JSON.parse(JSON.stringify(response.data))[1]);
            	alert("Uspjesna prijava!")
                router.push('/')
            }

        }
	}

});