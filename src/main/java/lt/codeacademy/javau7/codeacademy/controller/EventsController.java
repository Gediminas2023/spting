package lt.codeacademy.javau7.codeacademy.controller;

import lt.codeacademy.javau7.codeacademy.entities.Events;
import lt.codeacademy.javau7.codeacademy.entities.User;
import lt.codeacademy.javau7.codeacademy.services.EventsService;
import lt.codeacademy.javau7.codeacademy.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/events")
public class EventsController {

    private final EventsService eventsService;

    EventsController(EventsService eventsService){
        this.eventsService = eventsService;
    }

    @GetMapping
    public List<Events> getAllEvents(){
        return eventsService.getAllEvents();
    }

    @GetMapping("/{eventId}")
    public Events getEventById(@PathVariable Long eventId) {
        return eventsService.getEventById(eventId);
    }

    @GetMapping("/byStuffId/{stuffId}")
    public List<Events> getEventsByStuffId(@PathVariable Long stuffId) {
        return eventsService.getEventsByStuffId(stuffId);
    }

    @PostMapping("/{userId}/create")
    private ResponseEntity<String> saveEvent(@PathVariable Long userId , @RequestBody Events events){
        return eventsService.saveEvent(userId, events);
    }
    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable Long eventId){
        eventsService.deleteEvent(eventId);
    }
}
