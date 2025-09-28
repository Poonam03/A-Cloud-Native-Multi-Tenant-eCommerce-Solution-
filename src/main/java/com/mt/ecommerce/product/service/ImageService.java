package com.mt.ecommerce.product.service;

import com.mt.ecommerce.product.entity.Image;
import com.mt.ecommerce.product.exception.ImageUploadException;
import com.mt.ecommerce.product.model.ImageBO;
import com.mt.ecommerce.product.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
/**
 * Service class for managing images.
 * Provides methods for creating, updating, deleting, and retrieving images.
 */
@Service
public class ImageService {


    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Finds an image by its ID.
     *
     * @param imageID the ID of the image to find
     * @return the ImageBO representing the found image, or an empty ImageBO if not found
     */
    public ImageBO findImage(UUID imageID) {
        Optional<Image> imageOptional = this.imageRepository.findById(imageID);
        ImageBO imageBO = new ImageBO();
        imageOptional.ifPresent(image -> {
            imageBO.setImageUrl(image.getImageUrl());
            imageBO.setAltText(image.getAltText());
            imageBO.setId(image.getId());
        });
        return imageBO;
    }

    /**
     * Finds all images for a specific user.
     *
     * @param userId the ID of the user whose images are to be retrieved
     * @return a list of ImageBO objects representing the user's images
     */
    public List<ImageBO> findAll(String userId) {
        return this.imageRepository.findByUserId(userId)
                .stream().map(image -> {
                    ImageBO imageBO = new ImageBO();
                    imageBO.setImageUrl(image.getImageUrl());
                    imageBO.setAltText(image.getAltText());
                    imageBO.setId(image.getId());
                    imageBO.setUserId(image.getUserId());
                    return imageBO;
                }).collect(Collectors.toList());
    }

    /**
     * Creates a new image.
     *
     * @param imageUrl the URL of the image
     * @param altText  the alternative text for the image
     * @param userId   the ID of the user uploading the image
     */
    public void createImage(String imageUrl, String altText, String userId) {
        if(this.imageRepository.findByUserId(userId).size() >= 15) {
            throw new ImageUploadException("You can upload maximum 15 images");
        }
        Image image = new Image();
        image.setImageUrl(imageUrl);
        image.setAltText(altText);
        image.setUserId(userId);
        this.imageRepository.save(image);
    }

    /**
     * Updates an existing image.
     *
     * @param imageID  the ID of the image to update
     * @param altText  the new alternative text for the image
     * @param imageUrl the new URL of the image
     */
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

    /**
     * Deletes an image by its ID and removes the associated file from the filesystem.
     *
     * @param imageID  the ID of the image to delete
     * @param imageUrl the path to the image file to delete
     * @throws IOException if an I/O error occurs while deleting the file
     */
    @Transactional
    public void deleteImage(UUID imageID, Path imageUrl) throws IOException {
        this.imageRepository.deleteById(imageID);
        Files.delete(imageUrl);
    }


}
