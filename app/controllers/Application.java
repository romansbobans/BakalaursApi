package controllers;

import database.ManagerFactory;
import database.categories.CategoryManager;
import play.*;
import play.mvc.*;

import views.html.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Application extends Controller {

    private static final CategoryManager categoryManager;

    static {
        categoryManager = ManagerFactory.getInstance().getCategoryManager();
    }

    public static Result index() {
        return ok(main.render("TESTS", index.render(categoryManager.getAllCategories())));
    }

    public static Result at(String filename) {
        response().setContentType("image");
        System.out.print("getting image");
        ByteArrayOutputStream img_stream = null;
        try {
            File file = new File("public/images/" + filename);
            BufferedImage thumbnail = ImageIO.read(file);
            img_stream = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, "png", img_stream);
        } catch (FileNotFoundException e) {
            return badRequest("image not found  " + filename);
        } catch (IOException e) {
            return badRequest("image not found  " + "public/" + filename);
        }
        return ok(img_stream.toByteArray());
    }


}
