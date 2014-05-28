package images;

import dao.ImagePair;
import exceptions.FileTooLargeException;
import play.Logger;
import play.mvc.Http;
import utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by TAHKICT on 28/05/14.
 */
public class ImageManager {
    public static List<ImagePair> saveFiles(Http.MultipartFormData body) throws IOException{

        int index = -1;
        List<ImagePair> files = new ArrayList<>();
        while (true) {

            Http.MultipartFormData.FilePart picture = body.getFile("picture" + (index == -1 ? "" : index));
            if (picture != null) {
                String fileName = picture.getFilename();
                File file = picture.getFile();

                System.out.println(file.length());


                BufferedImage image = ImageIO.read(file);

                BufferedImage thumb = ImageUtils.resizeImage(image);

                fileName = fileName.substring(0, fileName.lastIndexOf('.'));

                int size = (int) (file.length()/1024/1024);


                File[] fileToSave = generateComplementaryFileNames();

                fileToSave[0].createNewFile();
                ImageIO.write(image, "png", fileToSave[0]);
                fileToSave[1].createNewFile();
                ImageIO.write(thumb, "png", fileToSave[1]);

                ImagePair pair = new ImagePair(fileToSave[0].getAbsolutePath(), fileToSave[1].getAbsolutePath());

                files.add(pair);

                index++;
            } else
                break;
        }

        return files;
    }

    private static File[] generateComplementaryFileNames() {

        File image = new File("public/images/images/" + String.valueOf(UUID.randomUUID()) + ".png");
        File thumbnail = new File("public/images/thumbnails/" + String.valueOf(UUID.randomUUID()) + ".png");
        return new File[]{image, thumbnail};
    }

}
