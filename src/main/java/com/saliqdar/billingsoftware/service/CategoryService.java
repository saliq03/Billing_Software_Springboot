package com.saliqdar.billingsoftware.service;
import com.saliqdar.billingsoftware.io.CategoryRequest;
import com.saliqdar.billingsoftware.io.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;


public interface CategoryService {

    CategoryResponse add(CategoryRequest request, MultipartFile file);
    List<CategoryResponse> read();
    CategoryResponse readByCategoryId(String categoryId);
    void delete(String categoryId);
}
