package com.example.shop;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 상품 등록
    public boolean saveItem(Item item, Map<String, String> data) {
        String title = data.get("title");
        Integer price = Integer.parseInt(data.get("price"));
        String userId = data.get("userId") != null ? data.get("userId") : "guest";
        String imageURL = data.get("imageURL");

        if(title.length() <= 20 || price >= 0) {
            item.setTitle(title);
            item.setPrice(price);
            item.setUserId(userId);
            item.setFilename(imageURL);
            itemRepository.save(item);

            return true;
        }

        return false;
    }

    // 상품 수정
    public boolean updateItem(Item item, Map<String, String> data) {
        Integer id = Integer.valueOf(data.get("id"));
        String title = data.get("title");
        Integer price = Integer.parseInt(data.get("price"));
        String imageURL = data.get("imageURL");

        if(title.length() <= 20 || price >= 0) {
            item.setId(id);
            item.setTitle(title);
            item.setPrice(price);
            item.setFilename(imageURL);

            itemRepository.save(item);

            return true;
        }

        return false;
    }

    // 상품 삭제
    public boolean deleteItem(Integer id) {
        itemRepository.deleteById(id);
        return true;
    }
}
