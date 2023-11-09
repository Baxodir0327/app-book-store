package uz.pdp.appbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.appbackend.entity.template.AbsUUIDEntity;
import uz.pdp.appbackend.enums.PayTypeEnum;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment extends AbsUUIDEntity {

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false, columnDefinition = "DOUBLE PRECISION CHECK(amount > 0)")
    private double amount;

    @Column(nullable = false, columnDefinition = "TIMESTAMP CHECK(paid_at <= now())")
    private Timestamp paidAt;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PayTypeEnum payType;
}
