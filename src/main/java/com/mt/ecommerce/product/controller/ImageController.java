package com.mt.ecommerce.product.controller;

import com.mt.ecommerce.product.exception.ImageUploadException;
import com.mt.ecommerce.product.model.ImageBO;
import com.mt.ecommerce.product.service.ImageService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Controller for managing image uploads and retrievals.
 * Provides endpoints for uploading, updating, deleting, and fetching images.
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    private static final String UPLOAD_DIR = "C:\\workplace\\Poonam Khandelwal\\mt-ecommerce-product\\images";

    @PostConstruct
    public void createDir() throws IOException {
        Path path = Paths.get(UPLOAD_DIR);
        Files.createDirectories(path);
    }


    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    /**
     * Endpoint to upload a new image.
     * Accessible by users with ROLE_VENDOR or ROLE_ADMIN.
     *
     * @param userDetails the authenticated user's details
     * @param file        the image file to upload
     * @param altText     the alternative text for the image
     */
    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    public void uploadImage(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("file") MultipartFile file, @RequestParam("altText") String altText) {
        try {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                File dest = new File(UPLOAD_DIR + "\\" + fileName);
                file.transferTo(dest);
                this.imageService.createImage(fileName, altText, userDetails.getUsername());
            } else {
                throw new ImageUploadException("Image is empty. Pls try again");
            }
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed. Pls try again", e);
        }
    }

    /**
     * Endpoint to update an existing image.
     * Accessible by users with ROLE_VENDOR or ROLE_ADMIN.
     *
     * @param file      the new image file
     * @param altText   the new alternative text for the image
     * @param iamgeID   the ID of the image to update
     */
    @PutMapping(value = "")
    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    public void updateImage(@RequestParam("file") MultipartFile file, @RequestParam("altText") String altText, @RequestParam("iamgeID") String iamgeID) {
        try {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                File dest = new File(UPLOAD_DIR + "\\" + fileName);
                file.transferTo(dest);
                this.imageService.updateImage(java.util.UUID.fromString(iamgeID), altText, fileName);
            } else {
                throw new ImageUploadException("Image is empty. Pls try again");
            }
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed. Pls try again", e);
        }
    }

    /**
     * Endpoint to delete an image.
     * Accessible by users with ROLE_VENDOR or ROLE_ADMIN.
     *
     * @param id        the ID of the image to delete
     * @param imageName the name of the image file to delete
     * @throws IOException if an I/O error occurs
     */
    @DeleteMapping("")
    @PreAuthorize("hasAnyAuthority('ROLE_VENDOR', 'ROLE_ADMIN')")
    public void deleteImage(@RequestParam("id") String id, @RequestParam("imageName") String imageName) throws IOException {
        this.imageService.deleteImage(java.util.UUID.fromString(id), Paths.get(UPLOAD_DIR, imageName));
    }

    /**
     * Endpoint to fetch all images for the authenticated user.
     * Accessible by users with ROLE_VENDOR or ROLE_ADMIN.
     *
     * @param userDetails the authenticated user's details
     * @return a list of ImageBO objects
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ImageBO> getImages(@AuthenticationPrincipal UserDetails userDetails) {
        return this.imageService.findAll(userDetails.getUsername());
    }

    /**
     * Endpoint to fetch and display an image by its name.
     * This endpoint is unsecured and can be accessed without authentication.
     *
     * @param imageName the name of the image file to retrieve
     * @return ResponseEntity containing the image bytes and appropriate headers, or NOT_FOUND status if the image does not exist
     * @throws IOException if an I/O error occurs while reading the image file
     */
    @GetMapping("/unsecured/display/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable("imageName") String imageName) throws IOException {
        Path imagePath = Paths.get(UPLOAD_DIR, imageName);
        if (Files.exists(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            HttpHeaders headers = new HttpHeaders();
            // Determine content type based on file extension
            String contentType = Files.probeContentType(imagePath);
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }
            headers.setContentType(MediaType.parseMediaType(contentType));
            return new ResponseEntity<>(imageBytes, headers, org.springframework.http.HttpStatus.OK);
        } else {
            return new ResponseEntity<>(org.springframework.http.HttpStatus.NOT_FOUND);
        }
    }
}
