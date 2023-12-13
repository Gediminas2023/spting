package lt.codeacademy.javau7.codeacademy.services;

import lt.codeacademy.javau7.codeacademy.entities.Events;
import lt.codeacademy.javau7.codeacademy.entities.User;
import lt.codeacademy.javau7.codeacademy.repositories.EventsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventsService {

    private final EventsRepository eventsRepository;
    private final UserService userService;

    EventsService(EventsRepository eventsRepository, UserService userService){
        this.eventsRepository = eventsRepository;
        this.userService = userService;
    }
    public List<Events> getAllEvents(){
        return eventsRepository.findAll();
    }
    public Events getEventById(Long eventId){
        return eventsRepository.findById(eventId).orElse(null);
    }
    public List<Events> getEventsByStuffId(Long stuffId) {
        return eventsRepository.findByStuffId(stuffId);
    }
    public ResponseEntity<String> saveEvent( Long userId, Events events){
        try {
            User user;
            user = userService.getUserById(userId);
            if (user != null) {
                events.setUser(user);
                eventsRepository.save(events);
                return ResponseEntity.ok("Event created successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating event: " + e.getMessage());
        }
    }
    public void deleteEvent(Long eventId){
        eventsRepository.deleteById(eventId);
    }
}
