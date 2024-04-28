package com.tbd.fifth.group.taskmanager.services;

import com.tbd.fifth.group.taskmanager.models.UserModel;
import com.tbd.fifth.group.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

@Repository
public class UserService implements UserRepository {
    //Creamos el CRUD de user model
    //    private int user_id;
    //    private String name;
    //    private String email;
    //    private String password;

    @Autowired
    private Sql2o sql2o;

    @Autowired
    private JwtMiddlewareService jwtMiddlewareService;

    @Autowired
    private VerificationsService verificationsService;

    @Override
    public ResponseEntity<Object> createUser(UserModel user){
        try(Connection connection = sql2o.open()){
            //verificar si el urusiop ya existe dado mail y nombre
            Integer count = connection.createQuery("SELECT COUNT(*) FROM \"userm\" WHERE email = :email AND name = :name")
                    .addParameter("email", user.getEmail())
                    .addParameter("name", user.getName())
                    .executeScalar(Integer.class);

            if(count != null && count > 0){
                //devolver un codigo de error 409 conflict directamente
                return ResponseEntity.status(409).body("Ya existe un usuario con el mismo email.");
            }

            //si no existe, proceder con la creacion del usuario
            Integer userId = (Integer) connection.createQuery("INSERT INTO \"userm\" (name, email, password) VALUES (:name, :email, :password)", true)
                    .addParameter("name", user.getName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword())
                    .executeUpdate().getKey();

            user.setUser_id(userId); //establecer el id del usuario

            String token = jwtMiddlewareService.generateToken(user);

            //devolver el token con un codigo 200 ok
            return ResponseEntity.ok(token);

        }catch (Exception e){
            //manejar otras excepciones no esperadas
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getUser(int user_id) {
        try(Connection connection = sql2o.open()){
            UserModel user = connection.createQuery("SELECT * FROM \"userm\" WHERE user_id = :user_id")
                    .addParameter("user_id", user_id)
                    .executeAndFetchFirst(UserModel.class);
            if(user != null){
                return ResponseEntity.ok(user);
            }else{
                return ResponseEntity.notFound().build();
            }
        }
    }

    @Override
    public ResponseEntity<Object> deleteUser(int user_id) {
        try (Connection connection = sql2o.open()) {
            connection.createQuery("DELETE FROM \"userm\" WHERE user_id = :user_id")
                    .addParameter("user_id", user_id)
                    .executeUpdate();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> updateUser(UserModel user) {
        try (Connection connection = sql2o.open()) {
            connection.createQuery("UPDATE \"userm\" SET name = :name, email = :email, password = :password WHERE user_id = :user_id")
                    .addParameter("name", user.getName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword())
                    .addParameter("user_id", user.getUser_id())
                    .executeUpdate();
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> getUserByEmail(String email) {
        try(Connection connection = sql2o.open()){
            UserModel user = connection.createQuery("SELECT * FROM \"userm\" WHERE email = :email")
                    .addParameter("email", email)
                    .executeAndFetchFirst(UserModel.class);
            if(user != null){
                return ResponseEntity.ok(user);
            }else{
                return ResponseEntity.notFound().build();
            }
        }
    }

    @Override
    public ResponseEntity<Object> loginUser(String name, String password) {
        try {
            UserModel user = (UserModel) getUserByName(name).getBody();
            if (user == null) {
                // Usuario no encontrado
                return ResponseEntity.status(401).body("Usuario no encontrado");
            }
            if (user.getPassword().compareTo(password) == 0) {
                String token = jwtMiddlewareService.generateToken(user);
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
    @Override
    public ResponseEntity<Object> getUserByName(String name) {
        try(Connection connection = sql2o.open()){
            UserModel user = connection.createQuery("SELECT * FROM \"userm\" WHERE name = :name")
                    .addParameter("name", name)
                    .executeAndFetchFirst(UserModel.class);
            if(user != null){
                return ResponseEntity.ok(user);
            }else{
                return ResponseEntity.notFound().build();
            }
        }
    }


}
