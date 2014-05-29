package controllers;

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

    public static Result index() {
        return ok(main.render("TESTS", index.render()));
    }

    public static Result at(String filename) {
        response().setContentType("image");
        ByteArrayOutputStream img_stream = null;
        try {
            File file = new File("/" + filename);
            BufferedImage thumbnail = ImageIO.read(file);
            img_stream = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, "png", img_stream);
        } catch (FileNotFoundException e) {
            return badRequest("image not found");
        } catch (IOException e) {
            return badRequest("image not found");
        }
        return ok(img_stream.toByteArray());
    }

}
