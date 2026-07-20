package TZJanosi.probaFeladat.service;

import TZJanosi.probaFeladat.dto.CarDto;
import TZJanosi.probaFeladat.dto.CreateCarCommand;
import TZJanosi.probaFeladat.dto.Criteria;
import TZJanosi.probaFeladat.exception.CarNotFoundException;
import TZJanosi.probaFeladat.model.Car;
import TZJanosi.probaFeladat.model.KilometerState;
import TZJanosi.probaFeladat.repository.CarRepository;
import TZJanosi.probaFeladat.repository.KilometerStateRepository;
import TZJanosi.probaFeladat.repository.SellerRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    private ModelMapper modelMapper;
    private CarRepository carRepository;
    private SellerRepository sellerRepository;
    private KilometerStateRepository kmStateRepository;

    public CarService(ModelMapper modelMapper, CarRepository carRepository, SellerRepository sellerRepository, KilometerStateRepository kmStateRepository) {
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;
        this.kmStateRepository = kmStateRepository;
    }

    public List<CarDto> findAllCars() {
        List<Car> cars=carRepository.findAll();
//        cars.stream().forEach(c->System.out.println(c.getKilometerStates()));
        return cars.stream().map(c->modelMapper.map(c, CarDto.class)).sorted(Comparator.comparingInt(c->c.getKilometerStates().get(c.getKilometerStates().size()-1).getActualValue())).toList();
    }

    public CarDto addNewCar(CreateCarCommand command) {
        Car car=modelMapper.map(command,Car.class);
        carRepository.save(car);
        KilometerState kmState=modelMapper.map(command.getKilometerStates().get(0),KilometerState.class);
        kmState.setCar(car);
        kmStateRepository.save(kmState);
        System.out.println(modelMapper.getConfiguration());

        return modelMapper.map(car,CarDto.class);
    }

    public List<CarDto> findFilteredCars(Criteria criteria) {
        if(!criteria.containsCriteria()){
            return findAllCars();
        }
        else {
            return filteredCars(criteria);
        }
    }

    private List<CarDto> filteredCars(Criteria criteria) {
//        System.out.println("in filteredCars criteria: "+criteria);
        int minConditionLevel=criteria.getCondition()==null?0:criteria.getCondition().getValue();
        List<Car> cars=carRepository.findAllByCriteria(criteria.getBrand(), criteria.getModel(), criteria.getMaxAgeInYears(),criteria.getMaxKm(),minConditionLevel);
        Comparator<Car> comparator=Comparator.comparingInt(Car::actualKmState);
        if(criteria.getSortDirection()!=null && criteria.getSortDirection().toString().equals("DESC")){
            comparator=comparator.reversed();
        }
        return cars.stream()
                .sorted(comparator)
                .map(c->modelMapper.map(c, CarDto.class))
                .toList();
    }

    public List<String> getBrands() {
        return carRepository.findBrands();
    }

    public Car findCarById(Long id) {
        Optional<Car> optionalCar=carRepository.findByIdWithKmStates(id);
        if(optionalCar.isEmpty()){
            throw new CarNotFoundException(id);
        }
        return optionalCar.get();
    }
    public CarDto findCarDtoById(Long id) {
        return modelMapper.map(findCarById(id),CarDto.class);
    }

    @Transactional
    public void deleteCarById(Long id) {
//        Car carToDelete=findCarById(id);
//        carRepository.delete(carToDelete);
        carRepository.deleteById(id);
    }

    @Transactional
    public CarDto addKmState(Long id, int km) {
        Car car=findCarById(id);
        car.addKilometerState(km);
        return modelMapper.map(car,CarDto.class);
    }

    @Transactional
    public void deleteAllCar() {
        carRepository.deleteAll();
        carRepository.resetIdGenerator();
    }
}
