package repository;

import beans.Comment;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CommentRepository extends Repository<Comment, String>{
    @Override
    protected String getKey(Comment comment) {
        return comment.getId();
    }

    @Override
    protected Type getTokenType() {
        return new TypeToken<ArrayList<LogicalEntity<Comment>>>() {}.getType();
    }
}