package com.dipakraut.eCommerce.controller;

import com.dipakraut.eCommerce.exception.Product.ProductAlreadyExistException;
import com.dipakraut.eCommerce.exception.Product.ProductNotFoundException;
import com.dipakraut.eCommerce.model.Product;
import com.dipakraut.eCommerce.request.AddProductRequest;
import com.dipakraut.eCommerce.request.ProductUpdateRequest;
import com.dipakraut.eCommerce.response.ApiResponse;
import com.dipakraut.eCommerce.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProduct() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse("All Products", products));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Internal Server Error", INTERNAL_SERVER_ERROR));
        }


    } @GetMapping("/product/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse(" Product found by id", product));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }


    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request){
        try {
            Product theProduct = productService.addProduct(request);
            return ResponseEntity.ok(new ApiResponse("Product Added Successfully", theProduct));
        } catch (ProductAlreadyExistException e) {
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse("Product Already Exist", null));
        }
    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request,@PathVariable Long productId){

        try {
            Product updateProduct = productService.updateProduct(request, productId);
            return ResponseEntity.ok(new ApiResponse("Product Updated Successfully", updateProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }
    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){

        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(new ApiResponse("Product Deleted Successfully", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }
    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {


        try {
            List<Product> products = productService.getProductByBrandAndName(brandName, productName);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Product Found", null));
            }
            else {
                return ResponseEntity.ok(new ApiResponse("Product Retrieved Successfully", products));
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }
    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@PathVariable String category, @PathVariable String brand) {


        try {
            List<Product> products = productService.getProductByCategoryAndBrand(category, brand);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Product Found", null));
            }
            else {
                return ResponseEntity.ok(new ApiResponse("Product Retrieved Successfully", products));
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }
    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {


        try {
            List<Product> products = productService.getProductByName(name);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Product Found", null));
            }
            else {
                return ResponseEntity.ok(new ApiResponse("Product Retrieved Successfully", products));
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }

     @GetMapping("/products/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@PathVariable String brand) {


        try {
            List<Product> products = productService.getProductByBrand(brand);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No Product Found", null));
            }
            else {
                return ResponseEntity.ok(new ApiResponse("Product Retrieved Successfully", products));
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }

    }

     @GetMapping("/products/{category}/all/products")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category) {


         try {
             List<Product> products = productService.getProductsByCategory(category);
             if (products.isEmpty()) {
                 return ResponseEntity.status(NOT_FOUND)
                         .body(new ApiResponse("No Product Found", null));
             } else {
                 return ResponseEntity.ok(new ApiResponse("Product Retrieved Successfully", products));
             }
         } catch (Exception e) {
             return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                     .body(new ApiResponse(e.getMessage(), null));
         }
     }

        @GetMapping("/products/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brand, @RequestParam String name) {


        try {
            var  productCount = productService.countProductByBrandAndName(brand, name);
                return ResponseEntity.ok(new ApiResponse("Product count", productCount));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), null));
        }



    }

}
