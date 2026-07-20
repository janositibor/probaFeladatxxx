package TZJanosi.probaFeladat.service;

import TZJanosi.probaFeladat.dto.CreateCarCommand;
import TZJanosi.probaFeladat.dto.CreateSellerCommand;
import TZJanosi.probaFeladat.dto.SellerDto;
import TZJanosi.probaFeladat.model.Car;
import TZJanosi.probaFeladat.model.Seller;
import TZJanosi.probaFeladat.repository.CarRepository;
import TZJanosi.probaFeladat.repository.KilometerStateRepository;
import TZJanosi.probaFeladat.repository.SellerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    private ModelMapper modelMapper;
    private CarRepository carRepository;
    private SellerRepository sellerRepository;
    private KilometerStateRepository kmStateRepository;
    @Autowired
    private CarService carService;

    public SellerService(ModelMapper modelMapper, CarRepository carRepository, SellerRepository sellerRepository, KilometerStateRepository kmStateRepository) {
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;
        this.kmStateRepository = kmStateRepository;
    }

    public List<SellerDto> findAll(){
        List<Seller> sellers=sellerRepository.findAll();

        return sellers.stream().map(s->modelMapper.map(s, SellerDto.class)).toList();
    }


    public List<SellerDto> findByName(Optional<String> prefix) {
        if(prefix.isEmpty()){
            return findAll();
        }
        else{
            return findWithName(prefix.get());
        }
    }

    private List<SellerDto> findWithName(String prefix) {
        List<Seller> sellers=sellerRepository.findWithName(prefix);
        return sellers.stream().map(s->modelMapper.map(s, SellerDto.class)).toList();
    }

    @Transactional
    public SellerDto addSeller(@Valid CreateSellerCommand command) {
        Seller seller=modelMapper.map(command, Seller.class);
        sellerRepository.save(seller);
        if(command.getCars()!=null){
            for(CreateCarCommand carCommand : command.getCars()){
                carCommand.setSeller(seller);
                carService.addNewCar(carCommand);
            }
        }



        return modelMapper.map(seller, SellerDto.class);
    }

    @Transactional
    public void deleteAll() {
        sellerRepository.deleteAll();
        sellerRepository.resetIdGenerator();
    }

    public SellerDto findById(Long id) {
        Seller seller=getById(id);
        return modelMapper.map(seller,SellerDto.class);
    }

    public SellerDto addCarToSeller(Long id, @Valid CreateCarCommand carCommand) {
        Seller seller=getById(id);
        carCommand.setSeller(seller);
        carService.addNewCar(carCommand);
        seller.addCar(modelMapper.map(carCommand, Car.class));
        SellerDto sellerDto=modelMapper.map(seller,SellerDto.class);
        return sellerDto;
    }

    private Seller getById(Long id){
        Seller seller=sellerRepository.findWithCarsById(id);
        return seller;
    }

    @Transactional
    public SellerDto updateSeller(Long id, @Valid CreateSellerCommand sellerCommand) {
        Seller seller=sellerRepository.findWithCarsById(id);
        seller.setName(sellerCommand.getName());
        return modelMapper.map(seller,SellerDto.class);
    }

    @Transactional
    public void deleteSeller(Long id) {
    sellerRepository.deleteById(id);
    }
}
