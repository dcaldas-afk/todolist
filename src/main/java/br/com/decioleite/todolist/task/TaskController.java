package br.com.decioleite.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.decioleite.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel TaskModel, HttpServletRequest request) {
       System.out.println("Chegou no controller " + request.getAttribute("idUser"));
       var idUser = request.getAttribute("idUser");
       TaskModel.setIdUser((UUID)idUser);
       var currentDate = LocalDateTime.now();
       if(currentDate.isAfter(TaskModel.getStartAt()) || currentDate.isAfter(TaskModel.getEndat()))
            return ResponseEntity.status(400).body("A data de início e de fim devem ser maior do que a data atual");
       if (TaskModel.getStartAt().isAfter(TaskModel.getEndat()) || TaskModel.getEndat().isEqual(TaskModel.getStartAt()))
            return ResponseEntity.status(400).body("A data de fim deve ser maior que a data de início");
       var task = this.taskRepository.save(TaskModel);
       return ResponseEntity.status(200).body(task);
    }

    @GetMapping("/")
    public java.util.List<TaskModel> list(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID)idUser);
        return tasks;
    }

    // .../tasks/"idUSER"
    @PutMapping("/{id}")
    public TaskModel update(@RequestBody TaskModel TaskModel, HttpServletRequest request, @PathVariable UUID id) {
        var task = this.taskRepository.findById(id).orElse(null);
        Utils.copyNonNullProperties(TaskModel, task);
        return this.taskRepository.save(task);
    }
}
