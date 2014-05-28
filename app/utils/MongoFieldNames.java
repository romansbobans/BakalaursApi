package utils;

public class MongoFieldNames {


    public static class Categories
	{

        public static final String SHORT_DESCRIPTION = "shortDescription";
		public static final String LANG = "lang";
		public static final String NAME = "name";
		public static final String THUMB_URL = "thumb_url";
		public static final String OBJ_DESCR = "object_description";
		
	}

	public static final String ID = "id";
	public static final String OBJECT_ARRAY = "objects";
	public static final String CATEGORY_ID = "category_id";
    public static final String VISIT_OBJECT_ID = "object_id";
	public static final class Views{

		public static final class WorkingHrs
		{
			public static final String DAY = "day";

			public static final String HOURS = "hours";
		}

		public static final class Groups 
		{

			public static final String GROUP = "groupName";
			public static final String PRICE = "groupPrice";
		}

		 public static final String OBJECT_DESCRIPTION = "objectDescription";
		public static final String OBJECT_NAME = "viewName";
		public static final String OBJECT_SHORT_DESCRIPTION = "shortDescription";
		public static final String OBJECT_HOURS = "hours";
		public static final String OBJECT_GROUPS = "groups";
		public static final String PAIR_ARRAY = "imagePairs";
		public static class ImagePairs
		{

			public static final String PATH = "path";
			public static final String THUMB_PATH = "thumbnailPath";
			
		}
	}

    public class Comments {
        public static final String COMMENT_PLOT = "text";
        public static final String COMMENT_TIME = "time";
    }
}
