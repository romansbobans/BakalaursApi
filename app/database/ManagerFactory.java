package database;

import database.categories.CategoryManager;
import database.categories.MongoCategoryManager;
import com.mongodb.*;
import database.comments.CommentManager;
import database.comments.MongoCommentManager;
import database.visitobjects.MongoVisitObjecManager;
import database.visitobjects.VisitObjecManager;

import java.net.UnknownHostException;

/**
 * Created by TAHKICT on 27/05/14.
 */
public class ManagerFactory {


    DB db;

    private static ManagerFactory INSTANCE;

    public synchronized ManagerFactory getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new ManagerFactory();
        return INSTANCE;
    }

    private ManagerFactory() {
        try
        {
            db = Mongo.connect(new DBAddress(DBConstants.HOST_NAME, DBConstants.MONGO_PORT, DBConstants.MONGO_DB_NAME));
        }
        catch (UnknownHostException e)
        {
            throw new RuntimeException("Unable to connect to MongoDB");
        }
    }

    public CategoryManager getCategoryManager()
    {

        return new MongoCategoryManager(db);
    }

    public VisitObjecManager getVisitObjectManager()
    {

        return new MongoVisitObjecManager(db);
    }

    public CommentManager getCommentManager()
    {
        return new MongoCommentManager(db);
    }
}
