package uz.pdp.appbackend.entity;

import jakarta.persistence.*;
import jakarta.xml.bind.attachment.AttachmentMarshaller;
import lombok.*;
import uz.pdp.appbackend.entity.template.AbsAuditingEntity;
import uz.pdp.appbackend.entity.template.AbsIntegerEntity;
import uz.pdp.appbackend.entity.template.AbsUUIDEntity;
import uz.pdp.appbackend.enums.RoleEnum;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book extends AbsUUIDEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private boolean status;

    private int pageCount;

    private double price;

    @OneToOne
    @JoinColumn(name = "read_sample_id")
    private Attachment readSample;

    @OneToMany
    private List<Attachment> photos;

}
