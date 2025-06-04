package com.example.shop;

import com.example.shop.comment.Comment;
import com.example.shop.comment.CommentRepository;
import com.example.shop.comment.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private final CommentRepository commentRepository;
    private final ItemService itemService;
    private final S3Service s3Service;


    @GetMapping("/list")
    String list(Model model) {
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(0,5));
        int totalPage = result.getTotalPages();
        model.addAttribute("items", result);
        model.addAttribute("totalPage", totalPage);

        return "list.html";
    }

    @GetMapping("/list/page/{page}")
    String getListPage (Model model, @PathVariable Integer page) {
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(page - 1,5));
        int totalPage = result.getTotalPages();
        model.addAttribute("items", result);
        model.addAttribute("totalPage", totalPage);

        return "list.html";
    }

    @GetMapping("/searchItem")
    String postSearchItem(Model model, @RequestParam String searchText) {

        Page<Item> result = itemRepository.searchQuery(searchText, PageRequest.of(0, 5));
        int totalPage = result.getTotalPages();
        model.addAttribute("items", result);
        model.addAttribute("totalPage", totalPage);

        return "list.html";
    }

    @GetMapping("/write")
    String write() {
        return "write.html";
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL (@RequestParam String filename) {

        var result = s3Service.createPresignedUrl("test/" + filename);
        return result;
    }

    @PostMapping("/add")
//    String add(@ModelAttribute Item item) {
    String add(@ModelAttribute Item item, @RequestParam Map<String, String> data, Authentication auth) {
//        Item itemData = new Item();
//        itemData.setTitle(data.get("title"));
//        itemData.setPrice(data.get("price"));

        try  {
            if(!auth.equals(null))
                data.put("userId", auth.getName());
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return "redirect:/list";
        }

        // 서비스 클래스로 분리
        boolean result = itemService.saveItem(item, data);

        if(!result) {
            ResponseEntity.status(400).body("입력값 오류");
        }

        return "redirect:/list";
    }

    /*URL 파라미터*/
    @GetMapping("/detail/{id}")
    // 파라미터 값 가져오는 어노테이션
    String detail(@PathVariable Integer id, Model model) {

        Optional<Item> result = itemRepository.findById(id);
        List<Comment> res = commentRepository.findAllByContentId(Long.valueOf(id));

        if(result.isPresent()) {
            model.addAttribute("itemData", result.get());
            model.addAttribute("commentData", res);
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
