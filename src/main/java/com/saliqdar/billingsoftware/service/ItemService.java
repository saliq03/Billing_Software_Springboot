package com.saliqdar.billingsoftware.service;

import com.saliqdar.billingsoftware.io.ItemRequest;
import com.saliqdar.billingsoftware.io.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    ItemResponse addItem(ItemRequest itemRequest, MultipartFile multipartFile);
    List<ItemResponse> fetchAllItems();
    void deleteItem(String itemId);
}
