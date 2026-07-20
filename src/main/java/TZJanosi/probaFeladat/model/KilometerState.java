package TZJanosi.probaFeladat.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kilometerStates")
public class KilometerState {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="car_id")
    private Car car;
    @Column(name="km")
    private Integer actualValue;
    private LocalDate date;

    public KilometerState(Integer actualValue, LocalDate date) {
        this.actualValue = actualValue;
        this.date = date;
    }

    @Override
    public String toString() {
        return "KilometerState{" +
                "actualValue=" + actualValue +
                ", date=" + date +
                '}';
    }
}
