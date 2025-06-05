package com.example.shop.sales;

import com.example.shop.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Sales {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer itemId;
//    @ManyToOne
//    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
//    private Member member;
    private Integer memberId;
    private Integer count;
    private Integer totalPrice;

    @CreationTimestamp
    LocalDateTime created;
}
