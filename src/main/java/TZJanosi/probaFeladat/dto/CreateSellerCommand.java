package TZJanosi.probaFeladat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateSellerCommand {
    @NotBlank
    private String name;
    private List<CreateCarCommand> cars;

    public CreateSellerCommand(String name) {
        this.name = name;
    }

    public CreateSellerCommand(String name, List<CreateCarCommand> cars) {
        this.name = name;
        this.cars = cars;
    }
}
