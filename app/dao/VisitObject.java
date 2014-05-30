package dao;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by TAHKICT on 27/05/14.
 */
public class VisitObject implements Iterator<VisitObject.VisitObjectDescription> {

    private String id;
    private String categoryId;
    private String titleImageUrl;
    private ImagePair[] imagePairs;
    @SerializedName("objects")
    private VisitObjectDescription[] objectDescriptions;
    transient int index = 0;
    //  private Coordinates coordinates;
    private Location location;

    private float rating;
    private int ratingCount;


    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }


    public Location getLocation() {
        return location;
    }

    @Override
    public boolean hasNext() {
        return objectDescriptions != null && objectDescriptions.length > index;
    }

    @Override
    public VisitObject.VisitObjectDescription next() {
        return objectDescriptions[index++];
    }


    public class Location
    {
        double lat;
        double lng;

        public double getLat() {
            return lat;
        }

        public double getLang() {
            return lng;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "lat=" + lat +
                    ", lng=" + lng +
                    '}';
        }
    }


    @Override
    public void remove() {

    }

    public static class VisitObjectDescription
    {

        private String shortDescription;
        private String description;
        private String name;
        private String lang;
        public Hours[] workingHours;
        private ClientGroups[] groups;

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public Hours[] getWorkingHours() {
            return workingHours;
        }

        public void setWorkingHours(Hours[] workingHours) {
            this.workingHours = workingHours;
        }

        public ClientGroups[] getGroups() {
            return groups;
        }

        public void setGroups(ClientGroups[] groups) {
            this.groups = groups;
        }



        public class Hours {
            private String day;
            private String hours;

            public String getDay() {

                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getHours() {
                return hours;
            }

            public void setHours(String hours) {
                this.hours = hours;
            }

            @Override
            public String toString() {
                return "Hours{" +
                        "day='" + day + '\'' +
                        ", hours='" + hours + '\'' +
                        '}';
            }
        }

        public class ClientGroups {
            private String groupName;
            private String groupPrice;

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public String getGroupPrice() {
                return groupPrice;
            }

            public void setGroupPrice(String groupPrice) {
                this.groupPrice = groupPrice;
            }

            @Override
            public String toString() {
                return "ClientGroups{" +
                        "groupName='" + groupName + '\'' +
                        ", groupPrice='" + groupPrice + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "VisitObjectDescription{" +
                    "shortDescription='" + shortDescription + '\'' +
                    ", description='" + description + '\'' +
                    ", name='" + name + '\'' +
                    ", lang='" + lang + '\'' +
                    ", workingHours=" + Arrays.toString(workingHours) +
                    ", groups=" + Arrays.toString(groups) +
                    '}';
        }

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public void setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
    }

    public ImagePair[] getImagePairs() {
        return imagePairs;
    }

    public void setImagePairs(ImagePair[] imagePairs) {
        this.imagePairs = imagePairs;
    }

    public VisitObjectDescription[] getObjectDescriptions() {
        return objectDescriptions;
    }


    @Override
    public String toString() {
        return "VisitObject{" +
                "id='" + id + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", titleImageUrl='" + titleImageUrl + '\'' +
                ", imagePairs=" + Arrays.toString(imagePairs) +
                ", objectDescriptions=" + Arrays.toString(objectDescriptions) +
                ", category_upload=" + index +
                ", location=" + location +
                ", rating=" + rating +
                ", ratingCount=" + ratingCount +
                '}';
    }
}
