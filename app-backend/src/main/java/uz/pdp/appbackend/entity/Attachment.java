package uz.pdp.appbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import uz.pdp.appbackend.entity.template.AbsUUIDEntity;

@Entity
@Table(name = "attachment")
public class Attachment extends AbsUUIDEntity {


}