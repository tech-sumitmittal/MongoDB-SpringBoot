package com.sumit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

//@Document(collection = "photo")   not required, bcz we do not want to store it as separate collection
@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class Photo {

    @Id
    private Long id;
    private String title;
    private String photoBase64; // store Base64 string from JSON

    public Photo(String title, String photoBase64) {
        this.title = title;
        this.photoBase64 = photoBase64;
    }

}