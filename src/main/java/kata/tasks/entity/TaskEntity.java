package kata.tasks.entity;


import jakarta.persistence.*;
import kata.tasks.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    String titre;
    String description;
    @Temporal(TemporalType.DATE)
    private Date dateEcheance;

    @Version
    private Long version;

    private Status status = Status.IN_PROGRESS;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity owner;

}
