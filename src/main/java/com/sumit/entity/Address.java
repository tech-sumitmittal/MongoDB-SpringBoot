package com.sumit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Document(collection = "address")   not required, bcz we do not want to store it as separate collection
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Address {

    private String apartmentNo;
    private String building;
    private String streetName;
    private String city;
    private String province;
    private String country;

}