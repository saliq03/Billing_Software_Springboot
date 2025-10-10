package com.saliqdar.billingsoftware.service.impl;

import com.saliqdar.billingsoftware.entity.CategoryEntity;
import com.saliqdar.billingsoftware.exception.InternalServerErrorException;
import com.saliqdar.billingsoftware.exception.NotFoundException;
import com.saliqdar.billingsoftware.io.CategoryRequest;
import com.saliqdar.billingsoftware.io.CategoryResponse;
import com.saliqdar.billingsoftware.repository.CategoryRepository;
import com.saliqdar.billingsoftware.repository.ItemRepository;
import com.saliqdar.billingsoftware.service.CategoryService;
import com.saliqdar.billingsoftware.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

   private  final FileUploadService fileUploadService;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    @Override
    public CategoryResponse add(CategoryRequest request, MultipartFile file) {
        String imageUrl=fileUploadService.uploadFile(file);
        CategoryEntity entity= convertToEntity(request);
        entity.setImgUrl(imageUrl);
        entity= categoryRepository.save(entity);
        return convertToResponse(entity);
    }

    @Override
    public List<CategoryResponse> read() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryEntity -> convertToResponse(categoryEntity))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse readByCategoryId(String categoryId) {
        CategoryEntity entity=categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + categoryId));
        return convertToResponse(entity);
    }

    @Override
    public void delete(String categoryId) {
        CategoryEntity entity=categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found with id: " + categoryId));
       boolean isDeleted= fileUploadService.deleteFile(entity.getImgUrl());
        if (isDeleted) {
            categoryRepository.delete(entity);
        }else  {
            throw new InternalServerErrorException("Unable to delete category with id: " + categoryId);
        }
    }

    private CategoryEntity convertToEntity(CategoryRequest request) {
        return  CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .bgColor(request.getBgColor())
                .description(request.getDescription())
                .build();

    }
    private CategoryResponse convertToResponse(CategoryEntity entity) {
        Integer itemCount= itemRepository.countByCategoryId(entity.getId());
        return  CategoryResponse.builder()
                .categoryId(entity.getCategoryId())
                .name(entity.getName())
                .description(entity.getDescription())
                .imgUrl(entity.getImgUrl())
                .itemCount(itemCount)
                .bgColor(entity.getBgColor())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
