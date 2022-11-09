package com.example.contest.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Person {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String university;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date birthdate;
//    private java.sql.Date birthdate;

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}


