package com.example.contest.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer capacity;
    private Date date;

    @Column(unique = true)
    private String name;

    private Boolean registration_allowed;

    private Date registration_from;

    private Date registration_to;

    private Boolean editable = true;

    //multiple team
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Team> teams = new HashSet<>();

    //person-manager-contest
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "CONTEST_MANAGER",
            joinColumns = {@JoinColumn(name = "CONTEST_ID", referencedColumnName = "ID")}, //do not forget referencedColumnName if name is different
            inverseJoinColumns = {@JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")})
    private Set<Person> managers = new HashSet<>();

    //subcontest
    @ManyToOne(cascade = CascadeType.MERGE)
    //annotation bellow is just for Jackson serialization in controller
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Contest parentContest;

    //adding subcontests to it's parent contest
    public void addSubcontest(Contest subcontest) {
        subcontest.setParentContest(this);
    }

}
