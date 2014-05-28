package database.visitobjects;

import com.mongodb.DBCollection;
import dao.ImagePair;
import dao.VisitObject;

import java.util.List;

/**
 * Created by TAHKICT on 27/05/14.
 */
public class MongoVisitObjecManager implements VisitObjecManager {

    private final DBCollection visitObjectCollection;

    public MongoVisitObjecManager(DBCollection visitObjectCollection) {
        this.visitObjectCollection = visitObjectCollection;
    }

    @Override
    public VisitObject[] getAllVisitObjects() {
        return new VisitObject[0];
    }

    @Override
    public String getAllVisitObjectRaw() {
        return null;
    }

    @Override
    public VisitObject[] getAllVisitObjectsForCategory(String catId) {
        return new VisitObject[0];
    }

    @Override
    public String getAllVisitObjectForCategoryRaw(String catId) {
        return null;
    }

    @Override
    public VisitObject getVisitObject(String objectId) {
        return null;
    }

    @Override
    public void saveView(String catId, VisitObject object) {

    }

    @Override
    public void saveView(String catId, String objectRaw) {

    }

    @Override
    public boolean removeVisitObject(String objectId) {
        return false;
    }

    @Override
    public boolean editView(String objectId, String objectRaw) {
        return false;
    }

    @Override
    public boolean editView(String objectId, VisitObject objectRaw) {
        return false;
    }

    @Override
    public boolean addImagesToView(String objectId, List<ImagePair> imagePairs) {
        return false;
    }

    @Override
    public boolean removeImagesFromView(String objectId, String... link) {
        return false;
    }
}
