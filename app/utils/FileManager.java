package utils;

import exceptions.FileTooLargeException;
import org.apache.commons.io.FileUtils;
import play.Logger;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

	public static List<String> createFiles(MultipartFormData body) throws IOException, FileTooLargeException {
		// TODO Auto-generated method stub
		return createFiles(body, "categories/");
	}


	public static List<String> createFiles(MultipartFormData body, String folder) throws IOException, FileTooLargeException {
		// TODO Auto-generated method stub
		int index = -1;
		List<String> files = new ArrayList<>();
		while (true) {

			FilePart picture = body.getFile("picture" + (index == -1 ? "" : index));
			if (picture != null) {
				String fileName = picture.getFilename();
				String contentType = picture.getContentType();
				File file = picture.getFile();
				int size = (int) (file.length()/1024/1024);
				if (size > 1)
				{
					throw new FileTooLargeException(size + " " + 1);
				}


				File fileToSave = new File("public/images/"+folder + fileName);
				if (file.exists())
				{
					fileToSave = new File("public/images/" + folder + "img_" + Math.random() + fileName);
				}
				FileUtils.moveFile(file, fileToSave);
			//	Files.copy(file, fileToSave);

				files.add(fileToSave.getPath().toString());
				index++;
			} else
				break;
		}
		if (files.size() > 0)
		{
			return files;
		}


		return null;
	}

	public static List<String> createThumbnails(MultipartFormData body, String folder) throws IOException, FileTooLargeException {
		// TODO Auto-generated method stub
		int index = -1;
		List<String> files = new ArrayList<>();
		while (true) {

			FilePart picture = body.getFile("picture" + (index == -1 ? "" : index));
			if (picture != null) {
				String fileName = picture.getFilename();
				File file = picture.getFile();

                System.out.println(file.length());


				BufferedImage image = ImageIO.read(file);

				BufferedImage thumb = ImageUtils.resizeImage(image);


                Logger.error("size: " + file.length() + "   name: " + fileName + "  " + fileName.lastIndexOf('.'));

				fileName = fileName.substring(0, fileName.lastIndexOf('.'));

                Logger.error("size: " + file.length() + "   name: " + fileName + "  " + fileName.lastIndexOf('.'));

				int size = (int) (file.length()/1024/1024);
				if (size > 1)
				{
					throw new FileTooLargeException(size + " " + 1);
				}


				File fileToSave = generateFileName(fileName, 0, folder);

				fileToSave.createNewFile();
				ImageIO.write(thumb, "png", fileToSave);
				files.add(fileToSave.getPath().toString());
				index++;
			} else
				break;
		}
		if (files.size() > 0)
		{
			return files;
		}


		return null;
	}


	private static File generateFileName(String fileName, int prefix, String folder)
	{
		File file = new File("public/images/" + folder + fileName + prefix + ".png");
		if (file.exists())
		{
			return (generateFileName(fileName, ++prefix, folder));
		}
		return file;
	}

    public static void removeFile(String url) {
        if (url == null)
            return;
        File file = new File(url);
        if (file.exists())
            file.delete();
    }
/*

	public static List<ImageThumbnailPair> createImageThumbnailPair(
			MultipartFormData fdata, String foldername) throws IOException {
		List<FilePart> files = fdata.getFiles();
		for (FilePart part : files)
			{
			File file = part.getFile();

			String fileName = part.getFilename();


			System.out.println(fileName);

			fileName = fileName.substring(0, fileName.lastIndexOf('.'));

			BufferedImage image = ImageIO.read(file);
			BufferedImage thumb = ImageUtils.resizeImage(image);
			File fileToSaveRealFile = generateFileName(fileName, 0, foldername);

			File fileToSaveThumbFile = generateFileName(fileName, 0, foldername + "thumbs/");

			fileToSaveRealFile.createNewFile();
			ImageIO.write(image, "png", fileToSaveRealFile);

			fileToSaveThumbFile.createNewFile();
			ImageIO.write(thumb, "png", fileToSaveThumbFile);


			}
		return pairs;
	}*/
}
