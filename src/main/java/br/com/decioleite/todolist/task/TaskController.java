package br.com.decioleite.todolist.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    private TaskModel create(@RequestBody TaskModel TaskModel) {
       System.out.println("Chegou no controller");
       var task = this.taskRepository.save(TaskModel);
       return task;
    }
}
