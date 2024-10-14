package com.dipakraut.eCommerce.service.category;
import java.util.List;
import com.dipakraut.eCommerce.model.Category;

public interface ICategoryService {
    Category getCategoryById(Long id);
    void deleteCategory(Long id);
    Category addCategory(Category category);
    Category updateCategory(Category category, Long categoryId);
    List<Category> getAllCategories();
    Category getCategoriesByName(String name);


}
