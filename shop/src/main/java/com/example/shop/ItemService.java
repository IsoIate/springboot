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

    public boolean saveItem(Item item, Map<String, String> data) {
        String title = data.get("title");
        Integer price = Integer.parseInt(data.get("price"));

        if(title.length() <= 20 || price >= 0) {
            item.setTitle(title);
            item.setPrice(price);
            itemRepository.save(item);

            return true;
        }

        return false;
    }

    public boolean updateItem(Item item, Map<String, String> data) {
        Integer id = Integer.valueOf(data.get("id"));
        String title = data.get("title");
        Integer price = Integer.parseInt(data.get("price"));

        if(title.length() <= 20 || price >= 0) {
            item.setId(id);
            item.setTitle(title);
            item.setPrice(price);
            System.out.println(item);
            itemRepository.save(item);

            return true;
        }

        return false;
    }

    public boolean deleteItem(Integer id) {
        itemRepository.deleteById(id);

        return true;
    }
}
