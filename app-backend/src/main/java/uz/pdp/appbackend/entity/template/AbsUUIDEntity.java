package uz.pdp.appbackend.entity.template;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class AbsUUIDEntity extends AbsDateEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


}
