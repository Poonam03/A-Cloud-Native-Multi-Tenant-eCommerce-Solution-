package com.mt.ecommerce.product.service;

import com.mt.ecommerce.product.entity.Image;
import com.mt.ecommerce.product.model.ImageBO;
import com.mt.ecommerce.product.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {


    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
    public ImageBO findImage(UUID imageID){
        Optional<Image> imageOptional = this.imageRepository.findById(imageID);
        ImageBO imageBO = new ImageBO();
        imageOptional.ifPresent(image -> {
            imageBO.setImageUrl(image.getImageUrl());
            imageBO.setAltText(image.getAltText());
            imageBO.setId(image.getId());
        });
        return imageBO;
    }

    public void createImage(MultipartFile multipartFile, String altText) {
        //TODO: update the code to save the image to S3 bucket
        Image image = new Image();
        image.setImageUrl("http://example.com/images/" + multipartFile.getOriginalFilename());
        image.setAltText(altText);
        this.imageRepository.save(image);
    }

    public void deleteImage(UUID imageID) {
        this.imageRepository.deleteById(imageID);
    }





}
