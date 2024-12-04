package kata.tasks.repository;

import jakarta.persistence.LockModeType;
import kata.tasks.entity.TaskEntity;
import kata.tasks.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepo  extends JpaRepository<TaskEntity, Long> {


    List<TaskEntity> findByStatus(Status statusTask);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM TaskEntity t WHERE t.id = :id")
    Optional<TaskEntity> findByIdForUpdate(@Param("id") Long id);
}
