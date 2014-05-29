package database.visitobjects;

import dao.Category;
import dao.ImagePair;
import dao.VisitObject;

import java.util.List;

/**
 * Created by TAHKICT on 27/05/14.
 */
public interface VisitObjecManager {

    VisitObject[] getAllVisitObjects();

    String getAllVisitObjectRaw();

    VisitObject[] getAllVisitObjectsForCategory(String catId);

    String getAllVisitObjectForCategoryRaw(String catId);

    VisitObject getVisitObject(String objectId);

    void saveView(String catId, VisitObject object);

    void saveView(String catId, String objectRaw);

    boolean removeVisitObject(String objectId);

    boolean editView(String objectId, String objectRaw);

    boolean editView(String objectId, VisitObject objectRaw);

    boolean addImagesToView(String objectId, ImagePair imagePairs);

    boolean setTitleIlage(String objectId, String image);

    boolean removeImagesFromView(String objectId, String link);

}
