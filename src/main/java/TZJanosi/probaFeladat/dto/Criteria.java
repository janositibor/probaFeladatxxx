package TZJanosi.probaFeladat.dto;

import TZJanosi.probaFeladat.model.CarCondition;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Criteria {
    private String brand;
    private String model;
    private Integer maxAgeInYears;
    private Integer maxKm;
    private CarCondition condition;
    private SortDirection sortDirection;

    public boolean containsCriteria() {
        if(brand!=null){
            return true;
        }
        if(model!=null){
            return true;
        }
        if(maxAgeInYears!=null){
            return true;
        }
        if(maxKm!=null){
            return true;
        }
        if(condition!=null){
            return true;
        }
        if(sortDirection!=null){
            return true;
        }
        return false;
    }
}
