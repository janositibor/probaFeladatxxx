package TZJanosi.probaFeladat.model;

import TZJanosi.probaFeladat.exception.KmStateNotValidException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="seller_id")
    private Seller seller;
    private String brand;
    private String model;
    private int ageInYears;
    @Enumerated(EnumType.STRING)
    @Column(name = "car_condition")
    private CarCondition condition;
    private int conditionLevel;
//    @OneToMany(mappedBy = "car", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @OneToMany(mappedBy = "car", cascade = {CascadeType.REMOVE})

//    @ElementCollection
//    @CollectionTable(name="kilometer_state", joinColumns=@JoinColumn(name="car_id"))
//    @AttributeOverride(name="actualValue", column=@Column(name="km"))

    @OrderBy("date ASC")
    private List<KilometerState> kilometerStates=new ArrayList<>();

    public void addKilometerState(int km){
        if(actualKmState()>=km){
            throw new KmStateNotValidException(km);
        }
        kilometerStates.add(new KilometerState(km, LocalDate.now()));
    }



    public int actualKmState() {
        if(kilometerStates.isEmpty()){
            return 0;
        }
        return kilometerStates.get(kilometerStates.size()-1).getActualValue();
    }

    public void setCondition(CarCondition condition) {
        this.condition = condition;
        this.conditionLevel=condition.getValue();
    }
}
