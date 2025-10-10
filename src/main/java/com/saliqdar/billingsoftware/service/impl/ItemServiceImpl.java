package com.saliqdar.billingsoftware.service.impl;

import com.saliqdar.billingsoftware.entity.CategoryEntity;
import com.saliqdar.billingsoftware.entity.ItemEntity;
import com.saliqdar.billingsoftware.exception.InternalServerErrorException;
import com.saliqdar.billingsoftware.exception.NotFoundException;
import com.saliqdar.billingsoftware.io.ItemRequest;
import com.saliqdar.billingsoftware.io.ItemResponse;
import com.saliqdar.billingsoftware.repository.CategoryRepository;
import com.saliqdar.billingsoftware.repository.ItemRepository;
import com.saliqdar.billingsoftware.service.FileUploadService;
import com.saliqdar.billingsoftware.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final FileUploadService fileUploadService;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    @Override
    public ItemResponse addItem(ItemRequest itemRequest, MultipartFile multipartFile) {
        String imageUrl=fileUploadService.uploadFile(multipartFile);
        ItemEntity itemEntity=convertToEntity(itemRequest);
        itemEntity.setImgUrl(imageUrl);
        itemRepository.save(itemEntity);
        return convertToResponse(itemEntity);
    }

    private ItemResponse convertToResponse(ItemEntity itemEntity) {
        return ItemResponse.builder()
                .itemId(itemEntity.getItemId())
                .name(itemEntity.getName())
                .description(itemEntity.getDescription())
                .price(itemEntity.getPrice())
                .categoryName(itemEntity.getCategory().getName())
                .categoryId(itemEntity.getCategory().getCategoryId())
                .imgUrl(itemEntity.getImgUrl())
                .createdAt(itemEntity.getCreatedAt())
                .updatedAt(itemEntity.getUpdatedAt())
                .build();
    }

    private ItemEntity convertToEntity(ItemRequest itemRequest) {
        CategoryEntity categoryEntity=categoryRepository.findByCategoryId(itemRequest.getCategoryId())
                .orElseThrow(()-> new NotFoundException("Category not found with id: " + itemRequest.getCategoryId()));
        return  ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(itemRequest.getName())
                .description(itemRequest.getDescription())
                .price(itemRequest.getPrice())
                .category(categoryEntity)
                .build();

    }

    @Override
    public List<ItemResponse> fetchAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String itemId) {
     ItemEntity itemEntity= itemRepository.findByItemId(itemId)
             .orElseThrow(()-> new NotFoundException("Item not found with id: " + itemId));
     boolean isDeleted= fileUploadService.deleteFile(itemEntity.getImgUrl());
     if(isDeleted){
         itemRepository.delete(itemEntity);
     }else {
         throw new InternalServerErrorException("Unable to delete item");
     }

    }
}
