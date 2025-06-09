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

import java.util.HashMap;
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

    @GetMapping("/")
    String hello() {
        return "index.html";
    }

    // 상품 리스트 페이지
    @GetMapping("/list")
    String list(Model model) {
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(0,5));
        int totalPage = result.getTotalPages();
        model.addAttribute("items", result);
        model.addAttribute("totalPage", totalPage);

        return "list.html";
    }

    // 상품 리스트 페이지네이션
    @GetMapping("/list/page/{page}")
    String getListPage (Model model, @RequestParam Map<String, String> data, @PathVariable Integer page) {
        System.out.println(data);
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(page - 1,5));
        int totalPage = result.getTotalPages();
        model.addAttribute("items", result);
        model.addAttribute("totalPage", totalPage);

        return "list.html";
    }

    // 상품 검색 API
    @GetMapping("/searchItem")
    String postSearchItem(Model model, @RequestParam Map<String, String> data) {

        Page<Item> result = null;

        if(data.get("searchValue").equals("title")) {
            result = itemRepository.searchTextQuery(data.get("searchText"), PageRequest.of(0, 5));
        }
        else {
            result = itemRepository.searchPriceQuery(data.get("searchPriceMin"), data.get("searchPriceMax"), PageRequest.of(0, 5));
        }

        int totalPage = result.getTotalPages();
        model.addAttribute("items", result);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("searchValue", data.get("searchValue"));
        model.addAttribute("searchText", data.get("searchText"));
        model.addAttribute("min", data.get("searchPriceMin"));
        model.addAttribute("max", data.get("searchPriceMax"));

        return "list.html";
    }

    // 상품 등록 페이지
    @GetMapping("/write")
    String write() {
        return "write.html";
    }

    // 상품 이미지 추가 시 S3 클라우드에 업로드 및 URL 받아오는 API
    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL (@RequestParam String filename) {

        var result = s3Service.createPresignedUrl("test/" + filename);
        return result;
    }

    // 상품 추가 API
    @PostMapping("/add")
    String add(@ModelAttribute Item item, @RequestParam Map<String, String> data, Authentication auth) {
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

    // 상세정보 페이지
    // URL 파라미터 사용 시 {example} 형태로 사용
    // 파라미터 값 가져오는 어노테이션 @PathVariable
    @GetMapping("/detail/{id}")
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

    // 수정 페이지 API
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

    // 상품 정보 수정 API
    @PostMapping("/update")
    String update(@ModelAttribute Item item, @RequestParam Map<String, String> data) {

        boolean result = itemService.updateItem(item, data);

        if(!result)
        {
            ResponseEntity.status(400).body("입력값 오류");
        }
        return "redirect:/list";
    }

    // 상품 삭제 API
    @PostMapping("/delete")
    @ResponseBody
    boolean delete(@RequestBody Map<String, Integer> data) {
        itemService.deleteItem(data.get("id"));

        return true;
    }
}
