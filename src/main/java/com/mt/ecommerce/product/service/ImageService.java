package com.mt.ecommerce.product.service;

import com.mt.ecommerce.product.entity.Image;
import com.mt.ecommerce.product.exception.ImageUploadException;
import com.mt.ecommerce.product.model.ImageBO;
import com.mt.ecommerce.product.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<ImageBO> findAll(UUID vendorId){
        return this.imageRepository.findByVendorId(vendorId)
                .stream().map(image -> {
                    ImageBO imageBO = new ImageBO();
                    imageBO.setImageUrl(image.getImageUrl());
                    imageBO.setAltText(image.getAltText());
                    imageBO.setId(image.getId());
                    return imageBO;
                }).collect(Collectors.toList());
    }

    public void createImage(String imageUrl, String altText, UUID vendorId) {
        //TODO: update the code to save the image to S3 bucket
        Image image = new Image();
        image.setImageUrl(imageUrl);
        image.setAltText(altText);
        image.setVendorId(vendorId);
        this.imageRepository.save(image);
    }

    @Transactional
    public void updateImage(UUID imageID, String altText, String imageUrl) {
        Optional<Image> imageOptional = this.imageRepository.findById(imageID);
        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
            image.setAltText(altText);
            image.setImageUrl(imageUrl);
            this.imageRepository.save(image);
            File fileToDelete = new File(imageOptional.get().getImageUrl());
            if (!fileToDelete.delete()) {
                throw new ImageUploadException("Failed to delete old file. Pls try again");
            }
        }

    }

    public void deleteImage(UUID imageID) {
        this.imageRepository.deleteById(imageID);
    }





}
