package com.sumit.service;

import com.sumit.entity.User;
import com.sumit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private MongoTemplate mongoTemplate;


    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getUserByNameStartsWith(String name) {
        return userRepository.findByFirstNameStartsWith(name);
    }

    public String deleteUserById(String id) {
        userRepository.deleteById(id);
        return "Delete user with id : " + id;
    }

    public List<User> findUserByAgeBetween(Integer minAge, Integer maxAge) {
        return userRepository.findUserByAgeBetween(minAge, maxAge);
    }

    public Page<User> searchUsers(String firstName, String lastName, String city, Integer minAge, Integer maxAge, Integer page, Integer size) {

        // define Criteria and add fields if they are not null from the request
        List<Criteria> criteriaList = new ArrayList<>();
        if(firstName != null && !firstName.isEmpty())
            criteriaList.add(Criteria.where("firstName").regex(firstName, "i"));
        if(lastName != null && !lastName.isEmpty())
            criteriaList.add(Criteria.where("lastName").regex(lastName, "i"));
        if(city != null && !city.isEmpty())
            criteriaList.add(Criteria.where("addresses.city").regex(city, "i"));
        if(minAge != null && maxAge != null)
            criteriaList.add(Criteria.where("age").gte(minAge).lte(maxAge));

        // create pageable
        Pageable pageable = PageRequest.of(page, size);

        // any criteria to the query
        Query query = new Query().with(pageable);
        query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));


        return mongoTemplate
                .query(User.class)
                .matching(query)
                .all()
                .stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new PageImpl<>(list, pageable, list.size())
                ));
    }

    public List<Document> getOldestUserByCity() {
        // all the operations
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "age");
        GroupOperation groupOperation = Aggregation.group("addresses.city").first(Aggregation.ROOT).as("oldestUser");

        // aggregate all the operations
        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, sortOperation, groupOperation);

        // use mongoTemplate to fetch data based on the aggregation
        return mongoTemplate.aggregate(aggregation, User.class, Document.class).getMappedResults();
    }


    public List<Document> getPopulationByCity() {
        // all the operations
        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        GroupOperation groupOperation = Aggregation.group("addresses.city").count().as("populationCount");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC, "populationCount");

        // all the projections
        ProjectionOperation projectionOperation = Aggregation.project()
                .andExpression("_id").as("City")
                .andExpression("populationCount").as("count")
                .andExclude("_id");

        // aggregate all the operations
        Aggregation aggregation = Aggregation.newAggregation(unwindOperation, groupOperation, sortOperation, projectionOperation);

        // use mongoTemplate to fetch data based on the aggregation
        return mongoTemplate.aggregate(aggregation, User.class, Document.class).getMappedResults();
    }


}