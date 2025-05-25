package com.example.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @GetMapping("/list")
    String list(Model model) {
        List<Item> result = itemRepository.findAll();
        model.addAttribute("items", result);

        return "list.html";
    }

    @GetMapping("/write")
    String write() {
        return "write.html";
    }

    @PostMapping("/add")
//    String add(@ModelAttribute Item item) {
    String add(@ModelAttribute Item item, @RequestParam Map<String, String> data) {
//        Item itemData = new Item();
//        itemData.setTitle(data.get("title"));
//        itemData.setPrice(data.get("price"));

        // 서비스 클래스로 분리
        boolean result = itemService.saveItem(item, data);

        if(!result)
        {
            ResponseEntity.status(400).body("입력값 오류");
        }
        return "redirect:/list";
    }

    /*URL 파라미터*/
    @GetMapping("/detail/{id}")
    // 파라미터 값 가져오는 어노테이션
    String detail(@PathVariable Integer id, Model model) {

        Optional<Item> result = itemRepository.findById(id);
        if(result.isPresent()) {
            model.addAttribute("itemData", result.get());
            return "detail.html";
        }
        else {
            return "redirect:/list";
        }
    }

    @GetMapping("/modify/{id}")
    String update(@PathVariable Integer id, Model model) {

        Optional<Item> result = itemRepository.findById(id);
        if(result.isPresent()) {
            model.addAttribute("itemData", result.get());
            return "modify.html";
        }
        else {
            return "redirect:/list";
        }
    }

    @PostMapping("/update")
    String update(@ModelAttribute Item item, @RequestParam Map<String, String> data) {

        boolean result = itemService.updateItem(item, data);

        if(!result)
        {
            ResponseEntity.status(400).body("입력값 오류");
        }
        return "redirect:/list";
    }

    @PostMapping("/delete")
    String delete(@RequestBody Map<String, Integer> data) {
        itemService.deleteItem(data.get("id"));

        return "redirect:/list";
    }
}
