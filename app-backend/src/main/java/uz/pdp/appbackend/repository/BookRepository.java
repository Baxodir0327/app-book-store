package uz.pdp.appbackend.repository;

import com.sun.tools.javac.Main;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.appbackend.entity.Book;
import uz.pdp.appbackend.payload.MainCriteriaDTO;
import uz.pdp.appbackend.utils.AppConstants;
import uz.pdp.appbackend.utils.CommonUtils;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM get_ids_by_query(:query)")
    List<UUID> findAllByMyQuery(String query);

//    List<Book> findAllByAuthorContaining(String author);
}