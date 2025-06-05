package com.example.shop.sales;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;

    public boolean insertItemCart (Map<String, String> data) {

        Integer itemId = Integer.valueOf(data.get("itemId"));
        Integer userId = Integer.valueOf(data.get("userId"));
        Integer count = Integer.valueOf(data.get("count"));

        Integer result = salesRepository.inputCartQuery(itemId, userId, count);

        if(result == 1)  return true;

        return false;
    }

    public List<Sales> findOrderAll() {
        List<Sales> result = salesRepository.findAll();

        return result;
    }
}
