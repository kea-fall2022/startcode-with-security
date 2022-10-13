package dat3.cars.api;

import dat3.cars.dto.ReservationRequest;
import dat3.cars.dto.ReservationResponse;
import dat3.cars.service.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("api/reservations")
@CrossOrigin
public class ReservationController {

  ReservationService reservationService;

  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping("/{memberId}/{carId}/{date}")
  public void makeReservation(@PathVariable String memberId , @PathVariable int carId, @PathVariable String date) {
    //date is assumed to come in as a string formatted like: day-month-year
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
    LocalDate reservationDate = LocalDate.parse(date,formatter);
    reservationService.reserveCar(memberId,carId,reservationDate);
  }
  @PostMapping()
  public ReservationResponse makeReservation(@RequestBody ReservationRequest request) {
    //date is assumed to come in as a string formatted like: day-month-year
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
//    LocalDate reservationDate = LocalDate.parse(request.getDate(),formatter);
//    return reservationService.reserveCar(request.getUsername(),request.getCarId(),reservationDate);
    System.out.println(request.getUsername());
    return reservationService.reserveCar(request.getUsername(),request.getCarId(),request.getDate());

  }
  //Admin
  @GetMapping
  public List<ReservationResponse> getReservations(){
    List<ReservationResponse> res = reservationService.getReservations();
    return res;
  }
  @GetMapping("/{username}")
  public List<ReservationResponse> getReservationsForUser(@PathVariable String username){
    List<ReservationResponse> res = reservationService.getReservationsForUser(username);
    return res;
  }




}
