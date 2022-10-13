package dat3.cars.service;

import dat3.cars.dto.CarRequest;
import dat3.cars.dto.CarResponse;
import dat3.cars.entity.Car;
import dat3.cars.repository.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

  CarRepository carRepository;

  public CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  public CarResponse addCar(CarRequest carRequest, boolean includeAll){
    Car newCar = CarRequest.getCarEntity(carRequest);
    newCar = carRepository.save(newCar);
    return new CarResponse(newCar, includeAll);
  }

  public String editCar(CarRequest body, int carId){
    Car car = carRepository.findById(carId).orElseThrow(
            ()->  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Car with this ID dont exist"));
    car.setBrand(body.getBrand());
    car.setModel(body.getModel());
    car.setPricePrDay(body.getPricePrDay());
    car.setBestDiscount(body.getBestDiscount());
    carRepository.save(car);
    return "{\"status\":\"OK\"}";
  }

  public List<CarResponse> getCars(boolean includeAll) {
    List<Car> cars = carRepository.findAll();
    List<CarResponse> response  = cars.stream().map(car -> new CarResponse(car,includeAll)).collect(Collectors.toList());

    //See MemberService for how to do this with streams and map
    return response;
  }

  public CarResponse findCarById(int id, boolean includeAll) throws Exception {
    Car found = carRepository.findById(id).orElseThrow(()->
            new ResponseStatusException(HttpStatus.NOT_FOUND,"Car not found"));
    return new CarResponse(found,includeAll);
  }

  public void setPricePrDay(int carId, double price) {
    Car car = carRepository.findById(carId).orElseThrow(()->
            new ResponseStatusException(HttpStatus.BAD_REQUEST,"Car with this id already exist"));
    car.setPricePrDay(price);
    carRepository.save(car);
  }

  public String deleteCar(int carId) {
    Car car = carRepository.findById(carId).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"No car with this ID, exists"));
    carRepository.delete(car);
    return "{\"status\":\"OK\"}";
  }

  //Dont implement this, you would (almost) NEVER delete a car in a real application
  //public void deleteById(int carId) {};

}
