package com.example.demoCsrf.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class User {

    private String name;

    private String secretWord;

}
