package com.dipakraut.eCommerce.service.image;

import com.dipakraut.eCommerce.dto.image.ImageDto;
import com.dipakraut.eCommerce.exception.Image.ImageNotFoundException;
import com.dipakraut.eCommerce.model.Image;
import com.dipakraut.eCommerce.model.Product;
import com.dipakraut.eCommerce.repository.image.ImageRepository;
import com.dipakraut.eCommerce.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

private final ImageRepository imageRepository;
private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).
        orElseThrow(()-> new ImageNotFoundException("Image not found "+id));
    }

    @Override
    public void deleteImageById(Long id) {
       imageRepository.findById(id)
               .ifPresentOrElse(imageRepository::delete,
               ()-> {
                   throw new ImageNotFoundException("Image not found");
               }
               );


    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {

        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDTo = new ArrayList<>();

        for (MultipartFile file : files) {

            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProducts(product);

                String buildDownloadUrl = "/api/v1/images/download/";

                String downloadUrl = buildDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage =    imageRepository.save(image);

                savedImage.setDownloadUrl(buildDownloadUrl +savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDTo.add(imageDto);

            }
           catch (IOException  | SQLException e) {
                throw new RuntimeException("Failed to upload image "+e.getMessage());

            }
        }

        return savedImageDTo;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());

            image.setImage(new SerialBlob(file.getBytes()));
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to update image "+e.getMessage());
        }

    }
}
