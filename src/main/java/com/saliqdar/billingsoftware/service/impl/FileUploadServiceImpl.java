package com.saliqdar.billingsoftware.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.saliqdar.billingsoftware.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            Map data= cloudinary.uploader().upload(file.getBytes(),Map.of());
            return data.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Image Uploading failed "+e.getMessage());
        }
    }

    @Override
    public boolean deleteFile(String imageUrl) {
        String publicId= extractPublicId(imageUrl);
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Image Deletion failed "+e.getMessage());
        }
        return true;
    }

    private String extractPublicId(String imageUrl) {
        String[] parts = imageUrl.split("/");
        String lastPart = parts[parts.length - 1]; // abc123.png
        return lastPart.substring(0, lastPart.lastIndexOf("."));
    }
}
