package com.saliqdar.billingsoftware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saliqdar.billingsoftware.io.CategoryRequest;
import com.saliqdar.billingsoftware.io.CategoryResponse;
import com.saliqdar.billingsoftware.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestPart("data") String data, @RequestPart("file")MultipartFile file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            CategoryRequest request = mapper.readValue(data, CategoryRequest.class);
            return categoryService.add(request,file);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> fetchCategories() {
        return  categoryService.read();
    }

    @GetMapping("/categories/{categoryId}")
    public CategoryResponse fetchByCategoryId(@PathVariable String categoryId) {
        try{
            return categoryService.readByCategoryId(categoryId);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }


    @DeleteMapping("/admin/categories/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable String categoryId) {
        try{
            categoryService.delete(categoryId);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
