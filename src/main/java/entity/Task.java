package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Task {
    private Long id;
    private String name;
    private String description;
    private LocalDate deadline;
    private TaskStatus taskStatus;
}
