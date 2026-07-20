package TZJanosi.probaFeladat.repository;

import TZJanosi.probaFeladat.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    @Query("SELECT DISTINCT c.brand FROM Car c")
    List<String> findBrands();

    @Modifying
    @NativeQuery("ALTER TABLE cars AUTO_INCREMENT=1")
    void resetIdGenerator();

    @Query("SELECT c from Car c WHERE ("
            + "(:brand is null OR LOWER(c.brand) = LOWER(:brand) )"
            + "AND (:model is null OR LOWER(c.model) = LOWER(:model) )"
            + "AND (:maxAgeInYears is null OR c.ageInYears<=:maxAgeInYears) "
            + "AND (:maxKm is null OR ((SELECT MAX(ks.actualValue) FROM KilometerState ks WHERE ks.car = c ) < :maxKm)) "
            + "AND (:minConditionLevel=0 OR :minConditionLevel<=c.conditionLevel)"
            + ")"
    )
    List<Car> findAllByCriteria(@Param("brand") String brand, @Param("model") String model, @Param("maxAgeInYears") Integer maxAgeInYears, @Param("maxKm") Integer maxKm, @Param("minConditionLevel") int minConditionLevel);

    @Query("SELECT DISTINCT c from Car c LEFT JOIN FETCH c.kilometerStates WHERE c.id=:id")
    Optional<Car> findByIdWithKmStates(Long id);
}
