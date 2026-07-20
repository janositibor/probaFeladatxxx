package TZJanosi.probaFeladat.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class SellerDto {
    private Long id;
    private String name;
    private List<CarDto> cars=new ArrayList<>();

    public SellerDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SellerDto(Long id, String name, List<CarDto> cars) {
        this.id = id;
        this.name = name;
        this.cars = cars;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SellerDto sellerDto = (SellerDto) o;
        return Objects.equals(id, sellerDto.id) && Objects.equals(name, sellerDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
