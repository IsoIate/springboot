package com.example.shop.sales;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface SalesRepository extends JpaRepository<Sales, Integer> {

    @Query(value="insert into sales (item_id, member_id, count, total_price, created) values (?1, ?2, ?3, (select price from item where id = ?1) * ?3, now() );", nativeQuery = true)   // 직접 쿼리 작성해서 결과를 확인할 때 작성법
    @Modifying
    @Transactional
    Integer inputCartQuery (Integer itemId, Integer memberId, Integer count);

}
