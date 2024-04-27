package com.tbd.fifth.group.taskmanager.services;

import com.tbd.fifth.group.taskmanager.models.TaskModel;
import com.tbd.fifth.group.taskmanager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository

public class TaskService implements TaskRepository {

    //    private int task_id;
    //    private int user_id;
    //    private String title;
    //    private String description;
    //    private LocalDate date_of_expire;
    //    private String state;

    @Autowired
    Sql2o sql2o;

    @Autowired
    private JwtMiddlewareService jwtMiddlewareService;

    @Override
    public ResponseEntity<Object> createTask(TaskModel task, String token) {
        if(jwtMiddlewareService.validateToken(token)){
            try(Connection connection = sql2o.open()){
                connection.createQuery("INSERT INTO \"task\" (user_id, title, description, date_of_expire, state) VALUES (:user_id, :title, :description, :date_of_expire, :state)")
                        .addParameter("user_id", task.getUser_id())
                        .addParameter("title", task.getTitle())
                        .addParameter("description", task.getDescription())
                        .addParameter("date_of_expire", task.getDate_of_expire())
                        .addParameter("state", task.getState())
                        .executeUpdate();
            }catch(Exception e){
                System.out.println(e.getMessage());
                return ResponseEntity.badRequest().body("Error al crear la tarea");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
    }

    @Override
    public ResponseEntity<Object> getTask(int task_id, String token) {
        if (jwtMiddlewareService.validateToken(token)) {
            try (Connection connection = sql2o.open()) {
                TaskModel task = connection.createQuery("SELECT * FROM \"task\" WHERE task_id = :task_id")
                        .addParameter("task_id", task_id)
                        .executeAndFetchFirst(TaskModel.class);
                if (task != null) {
                    return ResponseEntity.ok(task);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
    }

    @Override
    public ResponseEntity<Object> updateTask(int task_id, TaskModel task, String token) {
        if(jwtMiddlewareService.validateToken(token)){
            try(Connection connection = sql2o.open()){
                connection.createQuery("UPDATE \"task\" SET user_id = :user_id, title = :title, description = :description, date_of_expire = :date_of_expire, state = :state WHERE task_id = :task_id")
                        .addParameter("user_id", task.getUser_id())
                        .addParameter("title", task.getTitle())
                        .addParameter("description", task.getDescription())
                        .addParameter("date_of_expire", task.getDate_of_expire())
                        .addParameter("state", task.getState())
                        .addParameter("task_id", task_id)
                        .executeUpdate();
                return ResponseEntity.ok(task);
            }catch(Exception e){
                return ResponseEntity.status(500).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
    }

    @Override
    public ResponseEntity<Object> deleteTask(int task_id, String token) {
        if(jwtMiddlewareService.validateToken(token)){
            try(Connection connection = sql2o.open()){
                connection.createQuery("DELETE FROM \"task\" WHERE task_id = :task_id")
                        .addParameter("task_id", task_id)
                        .executeUpdate();
                return ResponseEntity.ok().build();
            }catch(Exception e){
                return ResponseEntity.status(500).body(e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
    }

    @Override
    public ResponseEntity<List<Object>> getAllTasks(String token) {

        if (!jwtMiddlewareService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    List.of("No autorizado")
            );
        }
        try (Connection connection = sql2o.open()) {
            List<TaskModel> tasks = connection.createQuery("SELECT * FROM \"task\"")
                    .executeAndFetch(TaskModel.class);
            List<Object> result = (List) tasks;
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    List.of(e.getMessage())
            );
        }
    }

    @Override
    public ResponseEntity<List<Object>> getTasksByState(String state, String token) {
        if (!jwtMiddlewareService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    List.of("No autorizado")
            );
        }
        try (Connection connection = sql2o.open()) {
            List<TaskModel> tasks = connection.createQuery("SELECT * FROM \"task\" WHERE state = :state")
                    .addParameter("state", state)
                    .executeAndFetch(TaskModel.class);
            List<Object> result = (List) tasks;
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    List.of(e.getMessage())
            );
        }
    }
}
