package controllers;

import database.ManagerFactory;
import database.categories.CategoryManager;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Arrays;

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
        return ok(Arrays.toString(categoryManager.getAllCategories()));
    }

    public static Result saveCategory() {
        return play.mvc.Results.TODO;
    }
}
