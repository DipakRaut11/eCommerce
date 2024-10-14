package com.dipakraut.eCommerce.service.product;

import com.dipakraut.eCommerce.model.Product;
import com.dipakraut.eCommerce.request.AddProductRequest;
import com.dipakraut.eCommerce.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    void deleteProduct(Long id);
    Product updateProduct(ProductUpdateRequest request, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndName(String brand, String name);
    Long countProductByBrandAndName(String brand, String name);


}
