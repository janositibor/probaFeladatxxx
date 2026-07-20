package TZJanosi.probaFeladat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProblemDetails {
    private String type;
    private String title;
    private int status;
    private String detail;
}
