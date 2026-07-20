package TZJanosi.probaFeladat.controller;

import TZJanosi.probaFeladat.dto.CreateCarCommand;
import TZJanosi.probaFeladat.dto.CreateSellerCommand;
import TZJanosi.probaFeladat.dto.SellerDto;
import TZJanosi.probaFeladat.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {
    private SellerService service;

    public SellerController(SellerService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<SellerDto> findAll(){
        return service.findAll();
    }

    @GetMapping
    public List<SellerDto> findByName(@RequestParam("prefix") Optional<String> prefix){
        return service.findByName(prefix);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SellerDto addSeller(@Valid @RequestBody CreateSellerCommand command){
        return service.addSeller(command);
    }

    @GetMapping("/{id}")
    public SellerDto findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @PostMapping("/{id}")
    public SellerDto addCarToSeller(@PathVariable("id") Long id, @Valid @RequestBody CreateCarCommand carCommand){
        return service.addCarToSeller(id, carCommand);
    }

    @PutMapping("/{id}")
    public SellerDto updateSeller(@PathVariable("id") Long id, @Valid @RequestBody CreateSellerCommand sellerCommand){
        return service.updateSeller(id, sellerCommand);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSeller(@PathVariable("id") Long id){
        service.deleteSeller(id);
    }
}
