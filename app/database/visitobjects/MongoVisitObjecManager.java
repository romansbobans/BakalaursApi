package database.visitobjects;

import com.google.gson.Gson;
import com.mongodb.*;
import dao.Category;
import dao.ImagePair;
import dao.VisitObject;
import utils.FileManager;
import utils.MongoFieldNames;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static utils.MongoFieldNames.*;
import static utils.MongoFieldNames.Views.*;

/**
 * Created by TAHKICT on 27/05/14.
 */
public class MongoVisitObjecManager implements VisitObjecManager {

    private final DBCollection visitObjectCollection;
    private final Gson gson;

    public MongoVisitObjecManager(DBCollection visitObjectCollection) {
        this.visitObjectCollection = visitObjectCollection;
        this.gson = new Gson();
    }

    @Override
    public void saveView(String catId, String objectRaw) {
        VisitObject parsedObject = gson.fromJson(objectRaw, VisitObject.class);
        saveView(catId, parsedObject);

    }

    @Override
    public void saveView(String catId, VisitObject object) {
        visitObjectCollection.insert(buildVisitObject(object, catId));
    }


    @Override
    public boolean removeVisitObject(String objectId) {

            VisitObject object = getVisitObject(objectId);
            if (object != null && object.getImagePairs() != null)
            {
                for (ImagePair pair : object.getImagePairs())
                {
                    FileManager.removeFile(pair.getImage());
                    FileManager.removeFile(pair.getThumbnail());
                }
            }
        visitObjectCollection.remove(new BasicDBObject(ID, objectId));

        return true;
    }


    @Override
    public VisitObject[] getAllVisitObjects() {
        DBCursor cursor = visitObjectCollection.find();
        VisitObject[] objects = new VisitObject[cursor.count()];
        int i = 0;
        while (cursor.hasNext())
        {
            String raw = cursor.next().toString();
            objects[i++] = gson.fromJson(raw, VisitObject.class);
        }

        return objects;
    }

    @Override
    public String getAllVisitObjectRaw() {
        DBCursor cursor = visitObjectCollection.find();
        String[] objects = new String[cursor.count()];
        int i = 0;
        while (cursor.hasNext())
        {
            objects[i++] = cursor.next().toString();
        }
        return Arrays.toString(objects);
    }

    @Override
    public VisitObject[] getAllVisitObjectsForCategory(String catId) {
        DBCursor cursor = visitObjectCollection.find(new BasicDBObject(CATEGORY_ID, catId));
        VisitObject[] objects = new VisitObject[cursor.count()];
        int i = 0;
        while (cursor.hasNext())
        {
            String raw = cursor.next().toString();
            objects[i++] = gson.fromJson(raw, VisitObject.class);
        }

        return objects;
    }

    @Override
    public String getAllVisitObjectForCategoryRaw(String catId) {
        DBCursor cursor = visitObjectCollection.find(new BasicDBObject(CATEGORY_ID, catId));
        String[] objects = new String[cursor.count()];
        int i = 0;
        while (cursor.hasNext())
        {
            objects[i++] = cursor.next().toString();
        }
        return Arrays.toString(objects);
    }

    @Override
    public VisitObject getVisitObject(String objectId) {
        DBCursor cursor = visitObjectCollection.find(new BasicDBObject(ID, objectId));

        if (cursor.hasNext())
        {
            VisitObject object = gson.fromJson(cursor.next().toString(), VisitObject.class);
           return object;

        }
        return null;
    }




    @Override
    public boolean editView(String objectId, String objectRaw) {
        VisitObject object = gson.fromJson(objectRaw, VisitObject.class);
        return editView(objectId, object);
    }

    @Override
    public boolean editView(String objectId, VisitObject object) {
        System.out.println(object + "\n");

        VisitObject.VisitObjectDescription d = object.next();

        DBObject updateQuery = createVisitObjectDescription(object);
        DBObject statusQuery = new BasicDBObject(MongoFieldNames.Categories.LANG, object.getObjectDescriptions()[0].getLang());
        DBObject fields = new BasicDBObject("$elemMatch", statusQuery);
        DBObject query = new BasicDBObject(MongoFieldNames.ID, objectId).append(OBJECT_ARRAY,fields);
        visitObjectCollection.update(query, new BasicDBObject("$set", new BasicDBObject(new BasicDBObject(MongoFieldNames.Categories.OBJ_DESCR + ".$", updateQuery))));
        return true;
    }

    @Override
    public boolean addImagesToView(String objectId, List<ImagePair> imagePairs) {
        return false;
    }

    @Override
    public boolean removeImagesFromView(String objectId, String... link) {
        VisitObject object = getVisitObject(objectId);

        return false;
    }



    //Helper methods

    private BasicDBObject buildVisitObject(VisitObject object, String categoryId)
    {
        System.out.print(object + "\n" + categoryId);
        BasicDBObject visitObject = new BasicDBObject(ID, String.valueOf(UUID.randomUUID()));
        visitObject.append(CATEGORY_ID, categoryId).
                append(IMAGE_PAIRS, buildImagePairs(object)).append(TITLE_IMAGE_URL, object.getTitleImageUrl());


                visitObject.append(LOCATION, new BasicDBObject(Location.LAT, object.getLocation().getLat()).append(Location.LNG, object.getLocation().getLang()))
                .append(RATING, object.getRating()).append(RATING_COUNT, object.getRatingCount())
                .append(OBJECT_ARRAY, createVisitObjectDescription(object));
        return visitObject;
    }

    private BasicDBList buildImagePairs(VisitObject object)
    {
        BasicDBList list = new BasicDBList();
        if (object.getImagePairs() == null)
        {
            return list;
        }
        for (ImagePair pair : object.getImagePairs())
        {
            list.add(new BasicDBObject(THUMBNAIL, pair.getThumbnail()).append(IMAGE, pair.getImage()));
        }
        return list;
    }

    private BasicDBList createVisitObjectDescription(VisitObject object) {
        BasicDBList list = new BasicDBList();

        System.out.println("HAS NEXT:?");
        while(object.hasNext())
        {
            VisitObject.VisitObjectDescription description = object.next();
            BasicDBObject oneDescription = new BasicDBObject(SHORT_DESCRIPTION, description.getShortDescription()).
                    append(DESCRIPTION, description.getDescription()).
                    append(LANG, description.getLang()).append(NAME, description.getName());
            oneDescription.append(WORKING_HOURS, buildWorkingHours(description)).append(GROUPS, buildGroups(description));
            list.add(oneDescription);
        }

        return list;
    }

    private BasicDBList buildGroups(VisitObject.VisitObjectDescription description) {
        BasicDBList groups = new BasicDBList();
        VisitObject.VisitObjectDescription.ClientGroups[] clientGroups = description.getGroups();

        if (clientGroups == null)
        {
            return groups;
        }

        for(VisitObject.VisitObjectDescription.ClientGroups g : clientGroups)
        {
            BasicDBObject object = new BasicDBObject(Groups.GROUP, g.getGroupName()).append(Groups.PRICE, g.getGroupPrice());
            groups.add(object);
        }
        return groups;


    }

    private BasicDBList buildWorkingHours(VisitObject.VisitObjectDescription description) {
        BasicDBList workingHrsList = new BasicDBList();
        VisitObject.VisitObjectDescription.Hours[] workingHrs = description.getWorkingHours();

        if (workingHrs == null)
        {
            return workingHrsList;
        }

        for(VisitObject.VisitObjectDescription.Hours g : workingHrs)
        {
            BasicDBObject object = new BasicDBObject(WorkingHrs.DAY, g.getDay()).append(WorkingHrs.HOURS, g.getHours());
            workingHrsList.add(object);
        }
        return workingHrsList;
    }
}
