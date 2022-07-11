package controller;

import beans.Comment;
import com.google.gson.Gson;
import service.CommentService;

import static spark.Spark.*;

public class CommentController {
    private static Gson gson = new Gson();
    public static CommentService commentService = new CommentService();

    public static void checkForUnfilledComments(){
        get("rest/comments/check_possible_commenting", (req, res) ->{
            res.type("application/json");

            String customerID = req.queryParams("customerID");
            String facilityID = req.queryParams("facilityID");
            return commentService.checkForUnfilledComments(customerID, facilityID);
        });
    }
    public static void getCommentsForFacility(){
        get("rest/comments/get_comments_for_facility", (req, res) ->{
            res.type("application/json");

            String role = req.queryParams("role");
            String facilityID = req.queryParams("facilityID");
            return commentService.getCommentsForFacility(role, facilityID);
        });
    }
    public static void addNewUnapprovedComment(){
        put("rest/comments/create_filled_comment", (req, res) ->{
            res.type("application/json");

            Comment comment = gson.fromJson(req.body(), Comment.class);
            return commentService.addNewUnapprovedComment(comment);
        });
    }

    public static void approveComment(){
        put("rest/comments/approve_comment", (req, res) ->{
            res.type("application/json");

            Comment comment = gson.fromJson(req.body(), Comment.class);
            return commentService.approveComment(comment);
        });
    }
    public static void getUnapprovedComments(){
        get("rest/comments/get_unapproved_comments", (req, res) ->{
            res.type("application/json");

            return commentService.getUnapprovedComments();
        });
    }
    public static void deleteComment(){
        delete("rest/comments/delete", (req, res) ->{
            res.type("application/json");

            String id = req.queryParams("id");
            return commentService.deleteComment(id);
        });
    }
}
