package controllers;

import com.mongodb.util.Base64Codec;
import com.ning.http.multipart.MultipartBody;
import dao.Category;
import dao.ImagePair;
import database.ManagerFactory;
import database.categories.CategoryManager;
import images.ImageManager;
import org.jboss.netty.handler.codec.base64.Base64Decoder;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.index;
import views.html.main;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
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

    public static Result prepareCategoryEditWindow(String id) {
        return ok(Arrays.toString(categoryManager.getAllCategories()));
    }


    public static Result index() {
        return ok(main.render("Welcome!", index.render()));
    }

    public static Result saveCategory() {
        Http.MultipartFormData fdata = request().body().asMultipartFormData();

        String[] data = fdata.asFormUrlEncoded().get("json");
        System.out.println(Arrays.toString(data));
        try {
            String thumbnail = ImageManager.saveFile(fdata);
            String id = categoryManager.saveCategory(data[0]);

            if (id == null)
            {
                return internalServerError();
            }
            Category category = categoryManager.getCategory(id);
            if (category == null)
            {
                return internalServerError();
            }
            System.out.println("GOT IMAGE: " + thumbnail);

            if (thumbnail != null)
            {

                System.out.println("GOT IMAGE: " + thumbnail);
                categoryManager.addImageToCategory(category.getId(), thumbnail);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return redirect("/");
    }

    public static Result deleteCategory(String id) {
        categoryManager.removeCategory(id);
        return redirect("/");
    }

    //Syntax of JSON: {"object_description":[{"lang":"LV","name":"Muzeji","shortDescription":"texthere"}]}
    @BodyParser.Of(BodyParser.Json.class)
    public static Result editCategory(String id, String lang) {

        String reqJSON = request().body().asJson().toString();

        categoryManager.editCategory(id, reqJSON, lang);
        //categoryManager.editCategory()
        return play.mvc.Results.TODO;
    }


    public static Result changeImage(String id) {

        Http.MultipartFormData fdata = request().body().asMultipartFormData();
        String pair = null;
        try {
            pair = ImageManager.saveFile(fdata);
            categoryManager.getCategory(id).getImage();
            categoryManager.changeCategoryImage(id, pair);
            return redirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return internalServerError();
    }
}
