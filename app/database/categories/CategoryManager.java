package database.categories;

import dao.Category;
import dao.ImagePair;

import java.util.List;

/**
 * Created by TAHKICT on 27/05/14.
 */
public interface CategoryManager {
    Category[] getAllCategories();

    String getAllCategoriesRaw();

    Category getCategory(String catId);

    String saveCategory(Category category);

    String saveCategory(String rawCategory);

    boolean removeCategory(String catId);

    boolean editCategory(String catId, String rawCategory, String lang);

    boolean editCategory(String catId, Category newCategory, String lang);

    boolean addImageToCategory(String catId, List<ImagePair> images);

    boolean changeCategoryImage(String catId, List<ImagePair> images);
}
