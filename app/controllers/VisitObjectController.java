package controllers;

import dao.ImagePair;
import database.ManagerFactory;
import database.visitobjects.VisitObjecManager;
import images.ImageManager;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.IOException;
import java.util.List;

/**
 * Created by TAHKICT on 27/05/14.
 */
public class VisitObjectController extends Controller {

    private static final VisitObjecManager visitObjectManager;

    static
    {
        visitObjectManager = ManagerFactory.getInstance().getVisitObjectManager();
    }

    public static Result uploadImages(String id) {

        Http.MultipartFormData fdata = request().body().asMultipartFormData();
        try {
            List<ImagePair> pairs = ImageManager.saveFiles(fdata);
            visitObjectManager.addImagesToView(id, pairs);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return TODO;
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result saveVisitObject(String categoryId) {

        String reqJSON = request().body().asJson().toString();
        visitObjectManager.saveView(categoryId, reqJSON);
        return redirect("/views/" + categoryId);
    }

    public static Result prepareVisitObjectPage(String categoryId) {
        return play.mvc.Results.TODO;
    }

    public static Result prepareVisitObjectUploadPage(String categoryId) {
        return play.mvc.Results.TODO;
    }

    public static Result deleteVisitObject(String objectId) {

        visitObjectManager.removeVisitObject(objectId);
        return ok();
    }

    public static Result prepareAddLanguagePage(String id) {
        return play.mvc.Results.TODO;
    }


    @BodyParser.Of(BodyParser.Json.class)
    public static Result editVisitObject(String id) {

        String reqJSON = request().body().asJson().toString();
        visitObjectManager.editView(id, reqJSON);
        return play.mvc.Results.TODO;
    }

    public static Result addImage(String id) {
        return play.mvc.Results.TODO;
    }

    public static Result removeImage(String id, String imageName) {
        return play.mvc.Results.TODO;
    }

    public static Result prepareEditPage(String id, String lang) {
        return play.mvc.Results.TODO;
    }
}
