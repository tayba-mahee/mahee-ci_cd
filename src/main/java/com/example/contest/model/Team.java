package com.example.contest.model;

import lombok.Data;
import lombok.SneakyThrows;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Team implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer rank;

    private State state = State.PENDING;

    //team has multiple members
    @ManyToMany(cascade = CascadeType.ALL)
    @Size(min = 1, max = 3)
    @JoinTable(name = "TEAM_MEMBER",
            joinColumns = {@JoinColumn(name = "TEAM_ID", referencedColumnName = "ID")}, //do not forget referencedColumnName if name is different
            inverseJoinColumns = {@JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")})
    private Set<Person> members = new HashSet<>();

    //team has 0 or 1 coach
    @ManyToOne(cascade = CascadeType.ALL)
    private Person coach;

    //self association
    @SneakyThrows
    @Override
    public Team clone() {
        Team t = (Team) super.clone();
        t.state = State.PENDING;
        t.id = null;
        t.rank = null;
        return t;
    }
}
