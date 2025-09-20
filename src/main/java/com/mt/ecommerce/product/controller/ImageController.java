package com.mt.ecommerce.product.controller;

import com.mt.ecommerce.product.exception.ImageUploadException;
import com.mt.ecommerce.product.model.ImageBO;
import com.mt.ecommerce.product.service.ImageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/image")
public class ImageController {

    private static final String UPLOAD_DIR = "images/";


    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @PostMapping("")
    public void uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("altText") String altText, @RequestParam("vendorId") String vendorId) {
        try {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                File dest = new File(UPLOAD_DIR + fileName);
                file.transferTo(dest);
                this.imageService.createImage(dest.getAbsolutePath(), altText, java.util.UUID.fromString(vendorId));
            } else {
                throw new ImageUploadException("Image is empty. Pls try again");
            }
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed. Pls try again", e);
        }
    }

    @PutMapping(value = "")
    public void updateImage(@RequestParam("file") MultipartFile file, @RequestParam("altText") String altText, @RequestParam("vendorId") String vendorId){
        try {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                File dest = new File(UPLOAD_DIR + fileName);
                file.transferTo(dest);
                this.imageService.updateImage(  java.util.UUID.fromString(vendorId), altText, dest.getAbsolutePath());
            } else {
                throw new ImageUploadException("Image is empty. Pls try again");
            }
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed. Pls try again", e);
        }
    }

    @DeleteMapping("")
    public void deleteImage(@RequestParam("id") String id) {
        this.imageService.deleteImage(java.util.UUID.fromString(id));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ImageBO> getImages(@RequestParam("vendorId") String vendorId) {
        return this.imageService.findAll(java.util.UUID.fromString(vendorId));
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getImage(@PathVariable("imageName") String imageName) throws IOException {
        Path imagePath = Paths.get(imageName);
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
