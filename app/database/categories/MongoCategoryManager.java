package database.categories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import dao.Category;
import database.DBConstants;

/**
 * Created by TAHKICT on 27/05/14.
 */
public class MongoCategoryManager implements CategoryManager {


    private final DBCollection categoryCollection;

    public MongoCategoryManager(DBCollection collection) {
        this.categoryCollection = collection;
    }

    @Override
    public Category[] getAllCategories() {
        DBCursor cursor = categoryCollection.find();

        Category[] group = new Category[cursor.count()];
        int i = 0;
        Gson gson = new GsonBuilder().create();
        while (cursor.hasNext()) {
            DBObject object = cursor.next();

            System.out.println(object.toString());
            Category g = gson.fromJson(object.toString(),
                    Category.class);

            group[i++] = g;

        }
        return group;
    }
}
