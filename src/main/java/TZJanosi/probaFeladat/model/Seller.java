package TZJanosi.probaFeladat.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    @OneToMany(mappedBy = "seller", cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @OneToMany(mappedBy = "seller", cascade = CascadeType.REMOVE)
    private List<Car> cars=new ArrayList<>();

    public Seller(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addCar(Car car){
        cars.add(car);
    }
}
