package controllers;

import com.google.gson.Gson;
import dao.Comment;
import database.ManagerFactory;
import database.categories.CategoryManager;
import database.comments.CommentManager;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * Created by TAHKICT on 27/05/14.
 */
public class CommentController extends Controller {

    private static final CommentManager commentManager;

    static
    {
        commentManager = ManagerFactory.getInstance().getCommentManager();
    }


    @BodyParser.Of(BodyParser.Json.class)
    public static Result uploadComment(String id) {

        DynamicForm requestData = Form.form().bindFromRequest();
        String reqJSON = requestData.data().toString();
        try
        {
            Comment comment = new Gson().fromJson(reqJSON, Comment.class);

            commentManager.uploadComment(id, comment.getText());

            return ok();
        }
        catch (Exception e)
        {
            return internalServerError(e.getMessage());
        }
    }

    public static Result getComments(String id) {
        String comments = commentManager.getComments(id);
        return ok(comments);
    }
}
