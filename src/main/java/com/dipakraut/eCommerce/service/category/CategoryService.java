package com.dipakraut.eCommerce.service.category;

import com.dipakraut.eCommerce.exception.Category.CategoryAlreadyExistsException;
import com.dipakraut.eCommerce.exception.Category.CategoryNotFoundException;
import com.dipakraut.eCommerce.model.Category;
import com.dipakraut.eCommerce.repository.Category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Category not found"));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(category -> categoryRepository.delete(category),
                        () ->{
                            throw new CategoryNotFoundException("Category not found");
                        }
                );

    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
                .filter(c -> !categoryRepository.existsByName(c.getName()))
        .map(categoryRepository::save)
                .orElseThrow(()-> new CategoryAlreadyExistsException(category.getName()+" Category already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        return Optional.ofNullable(getCategoryById(categoryId))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                })
                .orElseThrow(()-> new CategoryNotFoundException("Category not found"));
    }



    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoriesByName(String name) {
        return categoryRepository.findByName(name);
    }
}
