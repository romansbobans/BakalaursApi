package database.comments;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import utils.MongoFieldNames;

import java.util.Arrays;
import java.util.Date;

/**
 * Created by TAHKICT on 27/05/14.
 */
public class MongoCommentManager implements CommentManager {
    private final DBCollection commentsCollection;

    public MongoCommentManager(DBCollection collection) {
        this.commentsCollection = collection;
    }

    @Override
    public void uploadComment(String objectId, String text) {
        Date date= new Date();
        commentsCollection.insert(new BasicDBObject(MongoFieldNames.VISIT_OBJECT_ID, objectId)
                .append(MongoFieldNames.Comments.COMMENT_PLOT, text).
                        append(MongoFieldNames.Comments.COMMENT_TIME, date.getTime() + ""));
    }

    @Override
    public String getComments(String id) {
        DBCursor cursor = commentsCollection.find(new BasicDBObject(MongoFieldNames.VISIT_OBJECT_ID, id));
        String[] comments = new String[cursor.count()];
        int i = 0;
        while (cursor.hasNext())
        {
            comments[i++] = cursor.next().toString();
        }
        return Arrays.toString(comments);
    }
}
