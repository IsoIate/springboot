package com.example.shop.sales;

import com.example.shop.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    @PostMapping("/buyItem")
    @ResponseBody
    public boolean buyItem (@RequestBody Map<String, String> data, Authentication auth) {

        CustomUser user = (CustomUser) auth.getPrincipal();
        data.put("userId", String.valueOf(user.getUserId()));

        return salesService.insertItemCart(data);
    }

    @GetMapping("/order/all")
    public String getOrderAll() {

        List<Sales> result = salesService.findOrderAll();

        System.out.println(result);

        return "write.html";
    }
}
