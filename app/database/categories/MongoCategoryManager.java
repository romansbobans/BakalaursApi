package database.categories;

import com.mongodb.DB;

/**
 * Created by TAHKICT on 27/05/14.
 */
public class MongoCategoryManager implements CategoryManager {


    private final DB db;

    public MongoCategoryManager(DB db) {
        this.db = db;
    }
}
