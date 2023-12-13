package lt.codeacademy.javau7.codeacademy.controller;

import lt.codeacademy.javau7.codeacademy.entities.Date;
import lt.codeacademy.javau7.codeacademy.services.DateService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dashboard/settings/date")
public class DateController {

    private final DateService dateService;

    DateController(DateService dateService) {
        this.dateService = dateService;
    }

    @GetMapping
    public List<Date> getAllDates() {
        return dateService.getAllDates();
    }

    @PostMapping("/stuff")
    public ResponseEntity<List<Date>> getUserByStuffId(@RequestBody Map<String, Long> stuffId) {
        if (stuffId.containsKey("stuffId")) {
            Long id = stuffId.get("stuffId");
            List<Date> datesByStuffId = dateService.getDatesByStuffId(id);

            if (!datesByStuffId.isEmpty()) {
                return ResponseEntity.ok(datesByStuffId);
            } else {
                return ResponseEntity.notFound().build();
            }
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> saveDate(@RequestBody Date date){
        return dateService.saveDate(date);
    }

    @PutMapping("/{dateId}")
    public Date updateDateById(@PathVariable Long dateId, @RequestBody Date updateDate){
        return dateService.updateDateById(dateId, updateDate);
    }

    @DeleteMapping("/{dateId}")
    public void deleteDate(@PathVariable Long dateId){
        dateService.deleteDate(dateId);
    }
}

