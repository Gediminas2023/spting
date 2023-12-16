package lt.codeacademy.javau7.codeacademy.controller;

import lt.codeacademy.javau7.codeacademy.entities.Events;
import lt.codeacademy.javau7.codeacademy.entities.User;
import lt.codeacademy.javau7.codeacademy.services.EventsService;
import lt.codeacademy.javau7.codeacademy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/events")
public class EventsController {

    EventsService eventsService;
    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Events> getAllEvents(){
        return eventsService.getAllEvents();
    }

    @GetMapping("/{eventId}")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public Events getEventById(@PathVariable Long eventId) {
        return eventsService.getEventById(eventId);
    }

    @GetMapping("/byStuffId/{stuffId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Events> getEventsByStuffId(@PathVariable Long stuffId) {
        return eventsService.getEventsByStuffId(stuffId);
    }

    @PostMapping("/{userId}/create")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> saveEvent(@PathVariable Long userId , @RequestBody Events events){
        return eventsService.saveEvent(userId, events);
    }
    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public void deleteEvent(@PathVariable Long eventId){
        eventsService.deleteEvent(eventId);
    }
}
