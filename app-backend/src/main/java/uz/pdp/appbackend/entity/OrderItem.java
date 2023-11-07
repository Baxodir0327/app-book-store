package uz.pdp.appbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import uz.pdp.appbackend.entity.template.AbsUUIDEntity;

@Getter
@Entity
@Table(name = "order_item")
public class OrderItem extends AbsUUIDEntity {

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private int count;

    private double unitPrice;
}