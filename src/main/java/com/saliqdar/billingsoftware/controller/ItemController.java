package com.saliqdar.billingsoftware.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saliqdar.billingsoftware.exception.BadRequestException;
import com.saliqdar.billingsoftware.io.ItemRequest;
import com.saliqdar.billingsoftware.io.ItemResponse;
import com.saliqdar.billingsoftware.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("admin/items")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse addItem(@RequestPart("file") MultipartFile file, @RequestPart("data") String data){
        ObjectMapper mapper = new ObjectMapper();
        try {
            ItemRequest itemRequest = mapper.readValue(data, ItemRequest.class);
            return itemService.addItem(itemRequest,file);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
    @GetMapping("/items")
    public List<ItemResponse> readItems(){
        return  itemService.fetchAllItems();
    }

    @DeleteMapping("/admin/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable String itemId){
        itemService.deleteItem(itemId);
    }
}
