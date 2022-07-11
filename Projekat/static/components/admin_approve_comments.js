Vue.component("admin_approve_comments", {
	data: function () {
	    return {
	      comments: {},
          facilityNames: {},
	    }
	},
	    template: `
    	<div v-if="comments != null && facilityNames != null" >
            <h1 class="single_facility_header">Komentari za odobravanje</h1>

            <div v-if="comments.length == 0">Nema komentara za odobravanje!</div>
            <div v-else>
            <table>
                 <tr>
                     <th>Ime objekta</th>
                     <th>Korisnicko ime</th>
                     <th>Komentar</th>
                     <th>Ocena</th>
                 <tr>
                 <tr v-for="(comment, index) in comments">
                     <td>{{facilityNames[index]}}</td>
                     <td>{{comment.customerID}}</td>
                     <td>{{comment.text}}</td>
                     <td>{{comment.grade}}</td>
                     <td><button v-on:click="approveComment(comment)">Odobri</button></td>
                     <td><button v-on:click="deleteComment(comment)">Obrisi</button></td>
                 </tr>
             </table>
            </div>
    	</div>
    	`,
    mounted () {
        this.loadCommentsAndFacilities()
    },
    methods: {

        approveComment(comm){
            axios
            .put("rest/comments/approve_comment", comm)
            .then(response => {
                var changedComment = response.data
                this.loadCommentsAndFacilities()
            });
        },
        loadCommentsAndFacilities: function(){
            axios
            .get("rest/comments/get_unapproved_comments")
            .then(response => {
                this.comments = response.data
                axios
                .post("rest/facilities/facility_names", this.comments)
                .then(response => {
                    this.facilityNames = response.data
                });
            });
        },
        deleteComment(comm){

        }
    }
});