package dao;

/**
 * Created by TAHKICT on 28/05/14.
 */
public class ImagePair {

    private String image;
    private String thumbnail;

    public ImagePair(String image, String thumbnail) {
        this.image = image;
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
