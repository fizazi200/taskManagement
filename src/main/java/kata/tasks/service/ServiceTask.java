package kata.tasks.service;


import jakarta.transaction.Transactional;
import kata.tasks.entity.TaskEntity;
import kata.tasks.model.Status;
import kata.tasks.model.Task;
import kata.tasks.repository.TaskRepo;
import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceTask {

    private final TaskRepo taskRepo;
    private final ModelMapper modelMapper;

    public ServiceTask(TaskRepo taskRepo,ModelMapper modelMapper){
        this.taskRepo=taskRepo;
        this.modelMapper=modelMapper;
    }
    public void save(Task task) {
        TaskEntity newTask = modelMapper.map(task, TaskEntity.class);
        taskRepo.save(newTask);
    }
    public void update(Long id,Task updatedTask){
        TaskEntity oldtask=taskRepo.findByIdForUpdate(id).orElseThrow(()->new RuntimeException("task n'exsiste pas"));
        modelMapper.map(updatedTask,oldtask);
        taskRepo.save(oldtask);
    }
     public List<Task> getTasks(Status status){
        List<TaskEntity> tasksEntity=taskRepo.findByStatus(status);
        List<Task> listTask= tasksEntity.stream().map(taskEntity -> modelMapper.map(taskEntity,Task.class))
                .collect(Collectors.toList());
        return  listTask;
     }


     public void delete(Long id){
        taskRepo.deleteById(id);
     }

}
