package com.example.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    // 페이지네이션
    Page<Item> findPageBy(Pageable page);

    // 상품명 조회
    @Query(value="select * from item where match(title) against(?1)", nativeQuery = true)   // 직접 쿼리 작성해서 결과를 확인할 때 작성법
    Page<Item> searchTextQuery (String searchText, Pageable page);

    // 상품가격 조회
    @Query(value="select * from item where price between ?1 and ?2", nativeQuery = true)
    Page<Item> searchPriceQuery (String min, String max, Pageable page);
}
