package kata.tasks.test;



import jakarta.transaction.Transactional;
import kata.tasks.entity.TaskEntity;
import kata.tasks.model.Status;
import kata.tasks.model.Task;
import kata.tasks.repository.TaskRepo;
import kata.tasks.service.ServiceTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class TestServiceTask {

    @Mock
    private TaskRepo taskRepo;

    @Autowired
    private ModelMapper modelMapper;

    private ServiceTask serviceTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceTask = new ServiceTask(taskRepo, modelMapper);
    }

    @Test
    void testSave() {
        System.out.println("#######################################################");
        Task task = new Task();
        task.setTitre("Test");
        task.setDescription("test task decription");
        task.setStatus(Status.IN_PROGRESS);
        TaskEntity taskEntity = new TaskEntity();
        modelMapper.map(task,taskEntity);

        serviceTask.save(task);

        verify(taskRepo).save(taskEntity);  // Vérifier si la méthode save du repository a été appelée
    }

    @Test
    void testUpdate() {
        Long taskId = 1L;
        Task task = new Task();
        task.setTitre("Test");
        task.setDescription("test task decription");
        task.setStatus(Status.IN_PROGRESS);
        TaskEntity oldTaskEntity = new TaskEntity();
        modelMapper.map(task,oldTaskEntity);

        task.setTitre("New title");

        when(taskRepo.findByIdForUpdate(taskId)).thenReturn(Optional.of(oldTaskEntity));

        serviceTask.update(taskId, task);

        verify(taskRepo).save(argThat(tsk-> tsk.getTitre().equals(task.getTitre())));  // Vérifier si la méthode save a bien été appelée pour enregistrer l'ancienne tâche mise à jour

    }

    @Test
    void testGetTasks() {
        // Données de test
        Status status = Status.IN_PROGRESS;
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitre("Test");
        taskEntity.setDescription("test task decription");
        taskEntity.setStatus(Status.IN_PROGRESS);
        List<TaskEntity> taskEntities = Arrays.asList(taskEntity);

        when(taskRepo.findByStatus(status)).thenReturn(taskEntities);

        List<Task> tasks = serviceTask.getTasks(status);

        assertEquals(1, tasks.size());  // Vérifier que la taille de la liste retournée est correcte
        verify(taskRepo).findByStatus(status);  // Vérifier si la méthode du repository a bien été appelée
    }



}

