package dat3.cars.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import dat3.cars.entity.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CarRepositoryTest {
  @Autowired
  CarRepository carRepository;

  @BeforeEach
  void setupBeforeEach(){
    Car volvo = Car.builder().brand("Volvo").model("V70").pricePrDay(700).bestDiscount(30).build();
    Car ww = Car.builder().brand("VW").model("Up").pricePrDay(250).bestDiscount(10).build();
    carRepository.save(volvo);
    carRepository.save(ww);
  }

  @Test
  void findCarByBrand() {
    List<Car> cars = carRepository.findCarByBrand("Volvo");
    assertEquals(1,cars.size());
  }

  @Test
  void findCarByPricePrDayBetween() {
    List<Car> cars = carRepository.findCarByPricePrDayBetween(0,300);
    assertEquals(1,cars.size());

    cars = carRepository.findCarByPricePrDayBetween(251,800);
    assertEquals(1,cars.size());

    cars = carRepository.findCarByPricePrDayBetween(0,701);
    assertEquals(2,cars.size());
  }
}