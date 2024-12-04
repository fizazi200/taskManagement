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


    @PutMapping("/{id}")
    @PreAuthorize("@serviceTask.isOwner(#id)")
    public void updateTask( @PathVariable("id") Long id,@RequestBody Task task){
        serviceTask.update(id, task);
    }


    @GetMapping
    public List<Task> getTasks(@RequestParam(value = "status",required = false) String status){
        return  serviceTask.getTasks(Status.fromValue(status));
     }


    @DeleteMapping("/{id}")
    @PreAuthorize("@serviceTask.isOwner(#id)")
    public void deleteTask(@PathVariable("id")  Long id){
         serviceTask.delete(id);
     }



}



