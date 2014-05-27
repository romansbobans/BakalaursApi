package controllers;

import database.ManagerFactory;
import database.categories.CategoryManager;
import database.comments.CommentManager;
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

    public static Result uploadComment(String id) {
        commentManager.uploadComment(id, "asdawqeqwd");

        System.out.println("STAR SAVING COMMENT");
        return ok(views.html.index.render());
    }
}
