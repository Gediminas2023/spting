package lt.codeacademy.javau7.codeacademy.services;

import lt.codeacademy.javau7.codeacademy.entities.Date;
import lt.codeacademy.javau7.codeacademy.entities.User;
import lt.codeacademy.javau7.codeacademy.repositories.DateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DateService {
    protected final DateRepository dateRepository;
    protected final UserService userService;

    DateService(DateRepository dateRepository, UserService userService){
        this.dateRepository = dateRepository;
        this.userService = userService;
    }
    public List<Date> getAllDates(){
        return dateRepository.findAll();
    }
    public List<Date> getDatesByStuffId(Long stuffId) {
        return dateRepository.findByStuffId(stuffId);
    }
    public ResponseEntity<String> saveDate(Date date){
        try {
            Long id = date.getStuffId();
            User user = userService.getUserById(id);
            if (user != null) {
                date.setUser(user);
                dateRepository.save(date);
                return ResponseEntity.ok("Date created successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + id);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating date: " + e.getMessage());
        }
    }
    public ResponseEntity<String> updateDateById(Long dateId, Date updateDate) {
        Optional<Date> existingDate = dateRepository.findById(dateId);
        if(existingDate.isPresent()){
            Date date = existingDate.get();
            date.setDate(updateDate.getDate());
            date.setTimes(updateDate.getTimes());
            dateRepository.save(date);
            return ResponseEntity.ok("Date updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Date not found with ID: " + dateId);
    }
    public void deleteDate(Long dateId){
        dateRepository.deleteById(dateId);
    }
}
