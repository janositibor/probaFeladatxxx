package TZJanosi.probaFeladat.repository;

import TZJanosi.probaFeladat.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {
    @Modifying
    @NativeQuery("ALTER TABLE sellers AUTO_INCREMENT=1")
    void resetIdGenerator();

    @Query("SELECT s FROM Seller s Left JOIN FETCH s.cars WHERE LOWER(s.name) LIKE CONCAT(LOWER(:prefix), '%')")
    List<Seller> findWithName(String prefix);

    @Query("SELECT s FROM Seller s Left JOIN FETCH s.cars WHERE s.id=:id")
//    @Query("SELECT s FROM Seller s")
    Seller findWithCarsById(Long id);
}
