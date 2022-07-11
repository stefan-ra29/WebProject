package service;

import beans.Comment;
import com.google.gson.Gson;
import repository.CommentRepository;

import java.util.ArrayList;

public class CommentService {
    CommentRepository commentRepository = new CommentRepository();
    SportFacilityService sportFacilityService = new SportFacilityService();
    Gson gson = new Gson();

    public void CreateUnfilledComment(String customerId, String facilityId){

        Comment newComment = new Comment(customerId, facilityId, "", 0, false, false);
        commentRepository.addOne(newComment);
    }
    public String checkForUnfilledComments(String customerId, String facilityId){

        ArrayList<Comment> allComments = (ArrayList<Comment>) commentRepository.getAll();

        for(Comment c : allComments){

            if(!c.getIsFilled() && c.getCustomerID().equals(customerId) && c.getFacilityID().equals(facilityId))
                return gson.toJson(c);
        }
        return gson.toJson(new Comment("", "", "",0,false, false ));
    }
    public String getCommentsForFacility(String role, String facilityId){

        ArrayList<Comment> allComments = (ArrayList<Comment>) commentRepository.getAll();
        ArrayList<Comment> filteredComments = new ArrayList<Comment>();

        if(role.equals("Customer") || role.equals("")){
            for(Comment c : allComments){
                if(c.getIsApproved() && c.getFacilityID().equals(facilityId))
                    filteredComments.add(c);
            }
        }else{
            for(Comment c : allComments){
                if(c.getIsFilled() && c.getFacilityID().equals(facilityId))
                    filteredComments.add(c);
            }
        }
        return gson.toJson(filteredComments);
    }
    public String addNewUnapprovedComment(Comment comment){

        comment.setIsApproved(false);
        comment.setIsFilled(true);
        commentRepository.update(comment.getId(), comment);
        return gson.toJson(comment);
    }
    public String approveComment(Comment comment){

        comment.setIsApproved(true);
        commentRepository.update(comment.getId(), comment);
        sportFacilityService.recalculateAverageGrade(comment.getFacilityID());
        return gson.toJson(comment);
    }
    public String getUnapprovedComments(){

        ArrayList<Comment> allComments = (ArrayList<Comment>) commentRepository.getAll();
        ArrayList<Comment> filteredComments = new ArrayList<Comment>();
        for(Comment c : allComments){
            if(!c.getIsApproved() && c.getIsFilled())
                filteredComments.add(c);
        }
        return gson.toJson(filteredComments);
    }
}
