package kata.tasks.controllers;


import kata.tasks.model.Status;
import kata.tasks.model.Task;
import kata.tasks.service.ServiceTask;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class Controller {
     @Autowired
    ServiceTask serviceTask;


     @PostMapping
    public void createTask(@RequestBody Task task){
         serviceTask.save(task);
     }


    @PutMapping("/{taskId}")
    public void updateTask( @PathVariable("taskId") Long taskId,@RequestBody Task task){
         serviceTask.update(taskId, task);
     }

     @GetMapping
    public List<Task> getTasks(@RequestParam(value = "status",required = false) String status){
        return  serviceTask.getTasks(Status.fromValue(status));
     }


    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable("taskId")  Long taskId){
         serviceTask.delete(taskId);
     }



}



