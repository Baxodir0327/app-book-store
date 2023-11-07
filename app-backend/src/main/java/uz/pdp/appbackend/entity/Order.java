package uz.pdp.appbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.appbackend.entity.template.AbsUUIDEntity;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends AbsUUIDEntity {

    @ManyToOne(optional = false)
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
