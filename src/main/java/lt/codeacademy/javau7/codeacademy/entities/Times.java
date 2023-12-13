package lt.codeacademy.javau7.codeacademy.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Embeddable
@Data
public class Times {
    private String start;
    private String end;
}
