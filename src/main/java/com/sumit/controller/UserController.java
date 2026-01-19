package com.sumit.controller;

import com.sumit.entity.User;
import com.sumit.service.UserService;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;


    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> list = userService.getAllUser();
        if (list.isEmpty())
            return ResponseEntity.noContent().build(); // 204
        else
            return ResponseEntity.ok(list);
    }

    @PostMapping("/")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        user = userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/name")
    public ResponseEntity<List<User>> getUserByNameStartsWith(@RequestParam("name") String name){
        List<User> list = userService.getUserByNameStartsWith(name);
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") String id){
        String response = userService.deleteUserById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/age")
    public ResponseEntity<List<User>> getUserByAgeRange(@RequestParam("minAge") Integer minAge, @RequestParam("maxAge") Integer maxAge){
        List<User> list = userService.findUserByAgeBetween(minAge, maxAge);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<User>> searchUsers(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "minAge", required = false) Integer minAge,
            @RequestParam(value = "maxAge", required = false) Integer maxAge,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        Page<User> list = userService.searchUsers(firstName, lastName, city, minAge, maxAge, page, size);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/oldestUserByCity")
    public ResponseEntity<List<Document>> getOldestUserByCity(){
        List<Document> documents = userService.getOldestUserByCity();
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/populationByCity")
    public ResponseEntity<List<Document>> getPopulationByCity(){
        List<Document> documents = userService.getPopulationByCity();
        return ResponseEntity.ok(documents);
    }


}