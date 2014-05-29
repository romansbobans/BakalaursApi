package dao;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by Romans on 07/04/14.
 */
public class Category implements Iterator<Category.Description> {
    String id;

    @SerializedName("thumb_url")
    String image;
    private int index;

    @SerializedName("object_description")
    Description[] objectDescription;


    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean hasNext() {
        return objectDescription.length > index;
    }

    @Override
    public Description next() {
        return objectDescription[index++];
    }

    @Override
    public void remove() {

    }

    public void setDescription(Description[] description)
    {
        index = 0;
        this.objectDescription = description;
    }

    public class Description {
        public String lang;
        public String name;
        public String shortDescription;

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        @Override
        public String toString() {
            return "Description{" +
                    "lang='" + lang + '\'' +
                    ", name='" + name + '\'' +
                    ", shortDescription='" + shortDescription + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", category_upload=" + index +
                ", objectDescription=" + Arrays.toString(objectDescription) +
                '}';
    }
}
