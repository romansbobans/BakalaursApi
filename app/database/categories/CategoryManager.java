package database.categories;

import dao.Category;

/**
 * Created by TAHKICT on 27/05/14.
 */
public interface CategoryManager {
    Category[] getAllCategories();

    String getAllCategoriesRaw();

    Category getCategory(String catId);

    boolean saveCategory(Category category);

    boolean saveCategory(String rawCategory);

    boolean removeCategory(String catId);

    boolean editCategory(String catId, String rawCategory, String lang);

    boolean editCategory(String catId, Category newCategory, String lang);

    boolean addImageToCategory(String catId, String link);

    boolean changeCategoryImage(String catId, String newLink);
}
