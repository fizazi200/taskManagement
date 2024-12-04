package kata.tasks;


import com.fasterxml.jackson.databind.ObjectMapper;
import kata.tasks.entity.TaskEntity;
import kata.tasks.model.Status;
import kata.tasks.model.Task;
import kata.tasks.repository.TaskRepo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    TaskRepo taskRepo;


    @Test
    @Order(1)
    public void testDeleteTasks() throws Exception {

        Task task = new Task();
        task.setTitre("Test");
        task.setDescription("test task decription");
        task.setStatus(Status.IN_PROGRESS);

        String st=objectMapper.writeValueAsString(task);

        mockMvc.perform(post("/api/task").content(st).contentType(MediaType.APPLICATION_JSON)).andReturn();

        mockMvc.perform(delete("/api/task/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/task?status=".concat(Status.IN_PROGRESS.getStatusTask())))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    @Order(2)
    public void testGetTasks() throws Exception {

        Task task = new Task();
        task.setTitre("Test");
        task.setDescription("test task decription");
        task.setStatus(Status.IN_PROGRESS);

        String st=objectMapper.writeValueAsString(task);

        mockMvc.perform(post("/api/task").content(st).contentType(MediaType.APPLICATION_JSON)).andReturn();

        mockMvc.perform(get("/api/task?status=".concat(Status.IN_PROGRESS.getStatusTask())))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"titre\":\"Test\",\"description\":\"test task decription\",\"dateEcheance\":null,\"status\":\"En cours\",\"owner\":null}]"));
    }

    @Test
    @Order(4)
    public void testSaveTask() throws Exception{

        Task task = new Task();
        task.setTitre("Test");
        task.setDescription("test task decription");
        task.setStatus(Status.IN_PROGRESS);

        String st=objectMapper.writeValueAsString(task);

        mockMvc.perform(post("/api/task").content(st).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void testUpdateTask() throws Exception{

        TaskEntity task = new TaskEntity();
        task.setTitre("Test");
        task.setDescription("test task decription");
        task.setStatus(Status.IN_PROGRESS);
        taskRepo.save(task);
        Long id=task.getId();

        task.setTitre("updateTitle");
        String st=objectMapper.writeValueAsString(task);

        mockMvc.perform(put("/api/task/".concat(String.valueOf(id))).content(st).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/task?status=".concat(Status.IN_PROGRESS.getStatusTask())))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"titre\":\"updateTitle\",\"description\":\"test task decription\",\"dateEcheance\":null,\"status\":\"En cours\",\"owner\":null}")));



    }




}