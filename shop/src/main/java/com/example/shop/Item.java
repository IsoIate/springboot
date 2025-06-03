package com.example.shop;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String title;
    public Integer price;
    public String userId;
    public String filename; // 파일 이름이 길어서 mysql에서 varchar(255)를 text로 수동 형변환함

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(Integer price) {
        this.price = price;

    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getItems() {
        String temp = title + ", " + price;
        return temp;
    }
}
