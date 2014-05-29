package database.categories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import dao.Category;
import utils.FileManager;
import utils.MongoFieldNames;

import java.util.Arrays;
import java.util.UUID;

/**
 * Created by TAHKICT on 27/05/14.
 */
public class MongoCategoryManager implements CategoryManager {


    private final DBCollection categoryCollection;
    private final Gson gson;

    public MongoCategoryManager(DBCollection collection) {
        this.categoryCollection = collection;
        this.gson = new Gson();
    }

    @Override
    public Category[] getAllCategories() {
        DBCursor cursor = categoryCollection.find();

        Category[] group = new Category[cursor.count()];
        int i = 0;
        while (cursor.hasNext()) {
            DBObject object = cursor.next();

            System.out.println(object.toString());
            Category g = gson.fromJson(object.toString(),
                    Category.class);

            group[i++] = g;

        }
        return group;
    }

    @Override
    public String getAllCategoriesRaw() {
        DBCursor cursor = categoryCollection.find();

        System.out.println(cursor.count());

        String[] objects = new String[cursor.count()];
        int i = 0;

        while (cursor.hasNext())
        {
            DBObject object = cursor.next();
            objects[i++] = object.toString();
        }

        return Arrays.toString(objects);
    }

    @Override
    public Category getCategory(String catId) {
        Gson gson = new GsonBuilder().create();
        DBObject object = categoryCollection.findOne(new BasicDBObject(
                MongoFieldNames.ID, catId));
        Category g = gson.fromJson(object.toString(), Category.class);
        return g;
    }

    @Override
    public String saveCategory(Category category) {
        String id = String.valueOf(UUID.randomUUID());
        categoryCollection.insert(createCategoryObject(category, id));
        //Object not inserted
    //    if ().getN() == 0)
         //   return null;


        return id;
    }

    @Override
    public String saveCategory(String rawCategory) {
        Category.Description[] c = gson.fromJson(rawCategory, Category.Description[].class);

        Category category = new Category();
        category.setDescription(c);
        return saveCategory(category);
    }

    @Override
    public boolean removeCategory(String catId) {
        removeThumbnail(catId);
        categoryCollection.remove(new BasicDBObject(MongoFieldNames.ID, catId));
        return false;
    }

    private void removeThumbnail(String catId)
    {
        DBCursor cursor = categoryCollection.find(new BasicDBObject(MongoFieldNames.ID, catId), new BasicDBObject(MongoFieldNames.Categories.THUMB_URL, 1));
        if (cursor.hasNext())
        {
            String url = (String) cursor.next().get(MongoFieldNames.Categories.THUMB_URL);
            if (url != null)
            {
                FileManager.removeFile(url);
            }
        }
    }

    @Override
    public boolean editCategory(String catId, String rawCategory, String lang) {
        System.out.println(rawCategory + "\n");

        Category c = gson.fromJson(rawCategory, Category.class);

        Category.Description d = c.next();

        DBObject updateQuery = new BasicDBObject(MongoFieldNames.Categories.NAME,
                d.getName()).append(MongoFieldNames.Categories.SHORT_DESCRIPTION, d.getShortDescription()).append(MongoFieldNames.Categories.LANG, d.getLang());
        System.out.println(rawCategory);
        DBObject statusQuery = new BasicDBObject(MongoFieldNames.Categories.LANG, lang);
        DBObject fields = new BasicDBObject("$elemMatch", statusQuery);
        DBObject query = new BasicDBObject(MongoFieldNames.ID, catId).append(MongoFieldNames.Categories.OBJ_DESCR,fields);
        categoryCollection.update(query, new BasicDBObject("$set", new BasicDBObject(new BasicDBObject(MongoFieldNames.Categories.OBJ_DESCR + ".$", updateQuery))));
        return true;
    }

    @Override
    public boolean editCategory(String catId, Category newCategory, String lang) {
        String rawCategory = gson.toJson(newCategory);
        return editCategory(catId, rawCategory, lang);
    }

    @Override
    public boolean addImageToCategory(String catId, String image) {

        return changeCategoryImage(catId, image);
    }

    @Override
    public boolean changeCategoryImage(String catId, String image) {
        removeThumbnail(catId);
        categoryCollection.update(new BasicDBObject(MongoFieldNames.ID, catId), new BasicDBObject("$set",new BasicDBObject(MongoFieldNames.Categories.THUMB_URL, image)));
        return true;
    }


    private DBObject createCategoryObject(Category cat, String id) {
        BasicDBList object = new BasicDBList();
        while (cat.hasNext()) {
            Category.Description descr = cat.next();
            object.add(new BasicDBObject(MongoFieldNames.Categories.LANG, descr
                    .getLang()).append(MongoFieldNames.Categories.NAME,
                    descr.getName()).append(MongoFieldNames.Categories.SHORT_DESCRIPTION, descr.getShortDescription()));
        }
        BasicDBObject obj = new BasicDBObject(MongoFieldNames.ID, id).append(
                MongoFieldNames.Categories.OBJ_DESCR, object);
        if (cat.getImage() != null) {
            obj.append(MongoFieldNames.Categories.THUMB_URL, cat.getImage());

        }

        return obj;
    }
}
