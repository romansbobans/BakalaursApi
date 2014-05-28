package controllers;

import dao.ImagePair;
import database.ManagerFactory;
import database.visitobjects.VisitObjecManager;
import images.ImageManager;
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
}
