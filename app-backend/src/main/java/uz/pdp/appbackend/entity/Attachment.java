package uz.pdp.appbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import uz.pdp.appbackend.entity.template.AbsUUIDEntity;

@Getter
@Setter
@Entity
@Table(name = "attachment")
public class Attachment extends AbsUUIDEntity {

    @Column(nullable = false)
    private String originalName;

    private long size;

    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false)
    private String path;
}