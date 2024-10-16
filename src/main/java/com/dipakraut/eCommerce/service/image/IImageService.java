package com.dipakraut.eCommerce.service.image;

import com.dipakraut.eCommerce.dto.image.ImageDto;
import com.dipakraut.eCommerce.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {

    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);

}
