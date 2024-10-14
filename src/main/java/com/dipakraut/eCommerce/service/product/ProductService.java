package com.dipakraut.eCommerce.service.product;

import com.dipakraut.eCommerce.exception.ProductNotFoundException;
import com.dipakraut.eCommerce.model.Category;
import com.dipakraut.eCommerce.model.Product;
import com.dipakraut.eCommerce.repository.CategoryRepository.CategoryRepository;
import com.dipakraut.eCommerce.repository.product.ProductRepository;
import com.dipakraut.eCommerce.request.AddProductRequest;
import com.dipakraut.eCommerce.request.ProductUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService implements IProductService{


    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        //check if the category is found in the db
        //if yes, set it as a new product category
        // if no, then save it as a new category
        //then set is as the new product category


        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                            Category newCategory = new Category(request.getCategory().getName());
                            return categoryRepository.save(newCategory);

                        }
                        );

        request.setCategory(category);
        return productRepository.save(createProduct(request, category));

    }

    private Product createProduct(AddProductRequest request, Category category){

        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );

    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProduct(Long id) {

        productRepository.findById(id)

                //Lamda expression
               .ifPresentOrElse(product -> productRepository.delete(product),
                       () -> {throw new ProductNotFoundException("Product not found!");
                            }
                       );

              //  .ifPresent( productRepository::delete); --> method reference


    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {

        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));


    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
