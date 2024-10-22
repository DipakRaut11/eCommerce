package com.dipakraut.eCommerce.repository.image;

import com.dipakraut.eCommerce.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProducts_Id(Long productId);

}

