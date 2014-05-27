package database.categories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.*;
import dao.Category;
import database.DBConstants;
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
    public boolean saveCategory(Category category) {
        categoryCollection.insert(createCategoryObject(category));
        return true;
    }

    @Override
    public boolean saveCategory(String rawCategory) {

        return saveCategory(gson.fromJson(rawCategory, Category.class));
    }

    @Override
    public boolean removeCategory(String catId) {
        categoryCollection.remove(new BasicDBObject(MongoFieldNames.CATEGORY_ID, catId));
        return false;
    }

    @Override
    public boolean editCategory(String catId, String rawCategory, String lang) {
        return false;
    }

    @Override
    public boolean editCategory(String catId, Category newCategory, String lang) {
        return false;
    }

    @Override
    public boolean addImageToCategory(String catId, String link) {

        return changeCategoryImage(catId, link);
    }

    @Override
    public boolean changeCategoryImage(String catId, String newLink) {
        categoryCollection.update(new BasicDBObject(MongoFieldNames.CATEGORY_ID, catId), new BasicDBObject(MongoFieldNames.Categories.THUMB_URL, newLink));
        return true;
    }


    private DBObject createCategoryObject(Category cat) {
        BasicDBList object = new BasicDBList();
        while (cat.hasNext()) {
            Category.Description descr = cat.next();
            object.add(new BasicDBObject(MongoFieldNames.Categories.LANG, descr
                    .getLang()).append(MongoFieldNames.Categories.NAME,
                    descr.getName()).append(MongoFieldNames.Categories.SHORT_DESCRIPTION, descr.getShortDescription()));
        }
        BasicDBObject obj = new BasicDBObject(MongoFieldNames.ID, String.valueOf(UUID.randomUUID())).append(
                MongoFieldNames.Categories.OBJ_DESCR, object);
        if (cat.getImage() != null) {
            obj.append(MongoFieldNames.Categories.THUMB_URL, cat.getImage());

        }

        return obj;
    }
}
