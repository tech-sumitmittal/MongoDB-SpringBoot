package com.sumit.repository;

import com.sumit.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmail(String email);

    List<User> findByFirstNameStartsWith(String name);

    @Query(value = "{'age' : {$gt : ?0, $lt : ?1}}",
           fields = "{addresses : 0,  hobbies: 0}")
    List<User> findUserByAgeBetween(Integer minAge, Integer maxAge);
    // could also use : List<User> findByAgeBetween(minAge, maxAge)

}