package kata.tasks.model;


import lombok.Data;

import java.util.Date;

@Data
public class Task {

    private String titre;
    private String description;
    private Date dateEcheance;
    private Status status = Status.IN_PROGRESS;
    private User owner;

}
