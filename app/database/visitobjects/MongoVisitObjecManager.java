package database.visitobjects;

import com.mongodb.DB;

/**
 * Created by TAHKICT on 27/05/14.
 */
public class MongoVisitObjecManager implements VisitObjecManager {

    private final DB db;

    public MongoVisitObjecManager(DB db) {
        this.db = db;
    }
}
