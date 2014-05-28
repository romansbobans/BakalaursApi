package controllers;

import com.ning.http.multipart.MultipartBody;
import dao.Category;
import dao.ImagePair;
import database.ManagerFactory;
import database.categories.CategoryManager;
import images.ImageManager;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.index;
import views.html.main;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static play.mvc.Results.ok;

/**
 * Created by TAHKICT on 27/05/14.
 */
public class CategoryController extends Controller {

    private static final CategoryManager categoryManager;

    static
    {
        categoryManager = ManagerFactory.getInstance().getCategoryManager();
    }


    public static Result prepareCategoryUploadWindow() {
        return ok(Arrays.toString(categoryManager.getAllCategories()));
    }


    public static Result index() {
        return ok(main.render("Welcome!", index.render()));
    }

    public static Result saveCategory() {
        Http.MultipartFormData fdata = request().body().asMultipartFormData();
        try {
            List<ImagePair> pairs = ImageManager.saveFiles(fdata);
            String id = categoryManager.saveCategory("");
            if (id == null)
            {
                return internalServerError();
            }
            Category category = categoryManager.getCategory(id);
            if (category == null)
            {
                return internalServerError();
            }
            if (pairs.size() > 0)
            {
                categoryManager.addImageToCategory(category.getId(), pairs);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return play.mvc.Results.TODO;
    }

    public static Result deleteCategory(String id) {
        categoryManager.removeCategory(id);
        return redirect("/");
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result editCategory(String id) {
        DynamicForm requestData = Form.form().bindFromRequest();
        String reqJSON = requestData.data().toString();
        //categoryManager.editCategory()
        return play.mvc.Results.TODO;
    }


}
