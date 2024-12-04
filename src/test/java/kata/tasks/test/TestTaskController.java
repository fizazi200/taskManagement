package kata.tasks.test;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kata.tasks.entity.TaskEntity;
import kata.tasks.model.Status;
import kata.tasks.model.Task;
import kata.tasks.repository.TaskRepo;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@ActiveProfiles("test")
public class TestTaskController {

    @Autowired
    private MockMvc mockMvc;

    private static  ObjectMapper objectMapper;

    @Autowired
    TaskRepo taskRepo;

    @BeforeAll
    public static void setUp() {
         objectMapper=new ObjectMapper();
         objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
         objectMapper.registerModule(new JavaTimeModule());
    }

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