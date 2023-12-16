package lt.codeacademy.javau7.codeacademy.controller;

import lt.codeacademy.javau7.codeacademy.entities.Date;
import lt.codeacademy.javau7.codeacademy.services.DateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dashboard/settings/date")
public class DateController {
    @Autowired
    DateService dateService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Date> getAllDates() {
        return dateService.getAllDates();
    }

    @PostMapping("/stuff")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
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
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> saveDate(@RequestBody Date date){
        return dateService.saveDate(date);
    }

    @PutMapping("/{dateId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> updateDateById(@PathVariable Long dateId, @RequestBody Date updateDate){
        return dateService.updateDateById(dateId, updateDate);
    }

    @DeleteMapping("/{dateId}")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteDate(@PathVariable Long dateId){
        dateService.deleteDate(dateId);
    }
}

