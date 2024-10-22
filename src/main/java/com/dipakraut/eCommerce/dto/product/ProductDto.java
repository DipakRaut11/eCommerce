package com.dipakraut.eCommerce.dto.product;

import com.dipakraut.eCommerce.dto.image.ImageDto;
import com.dipakraut.eCommerce.model.Category;
import com.dipakraut.eCommerce.model.Image;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDto> images;
}
