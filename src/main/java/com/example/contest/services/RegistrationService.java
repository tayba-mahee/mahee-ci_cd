package com.example.contest.services;

import com.example.contest.model.*;
import com.example.contest.repository.ContestRepository;
import com.example.contest.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Service
@AllArgsConstructor
public class RegistrationService {

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private ContestService contestService;


    @Autowired
    private PersonRepository personRepository;


    public ResponseEntity<Object> Register(Registration registration) {
        if (contestRepository.findById(registration.getContestID()).isEmpty() ){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Contest doesn't exist ");
        }

        Contest contest = contestRepository.findById(registration.getContestID()).get();

        // Find existing member
        Team team = registration.getTeam();
        Set<Person> persons = new HashSet<>();
        for (Person p: team.getMembers()){
            if(p.getId() != null){
                Person nPerson = personRepository.findById(p.getId()).get();
                p.setEmail(nPerson.getEmail());
                p.setBirthdate(nPerson.getBirthdate());
                p.setName(nPerson.getName());
                p.setUniversity(nPerson.getUniversity());
            }
            persons.add(p);
        }
        team.setMembers(persons);

        // Find existing coach
        if(team.getCoach().getId() != null){
            Person c = personRepository.findById(team.getCoach().getId()).get();
            team.setCoach(c);
        }


        team.setState(State.PENDING);

        String str = Eligibility(contest, team);
        if (str != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(str);
        }

        contest.getTeams().add(team);
        contestRepository.save(contest);
        return ResponseEntity
                .ok()
                .body("Successful");

    }

    String Eligibility(Contest contest, Team team) {
        if(!contest.getRegistration_allowed()){
            return "Contest registration is not allowed";
        }
        if(!contest.getEditable()){
            return "Contest registration is not allowed because it is not editable";
        }
        if (contest.getTeams().size() >= contest.getCapacity()) {
            return "Contest doesn't have available capacity,";
        }
        if (!isUnique(team)) {
            return "Team members need to be distinct from each other";
        }
        if (!isYoungerThan24(team)) {
            return "Team members should be younger than 24";
        }
        if (!isUniqueMember(contest, team)) {
            return "Team members cannot be in a different team in the contest";
        }
        return null;
    }

    public Boolean isUnique(Team team) {
        ArrayList<Person> personArrayList = new ArrayList<>(team.getMembers());
        for (int i = 0; i < personArrayList.size(); i++) {
            Person person1 = personArrayList.get(i);
            if (team.getCoach().getEmail().equals(person1.getEmail())) {
                return false;
            }
            for (int j = i + 1; j < personArrayList.size(); j++) {
                Person person2 = personArrayList.get(j);
                if (person2.getEmail().equals(person1.getEmail())) {
                    return false;
                }
            }
        }
        return true;
    }

    public Boolean isYoungerThan24(Team team) {
        for (Person person : team.getMembers()) {
            long currentMillis = System.currentTimeMillis();
            long years = TimeUnit.MILLISECONDS.toDays(currentMillis - person.getBirthdate().getTime()) / 365;
            if (years >= 24) {
                return false;
            }
        }
        return true;
    }

    public Boolean isUniqueMember(Contest contest, Team team) {
        for (Person person : team.getMembers()) {
            if (!isUniqueMember(contest, person))
                return false;
        }
        return true;
    }

    public Boolean isUniqueMember(Contest contest, Person person) {
        for (Team team : contest.getTeams()) {
            for (Person member : team.getMembers()) {
                if (member.getEmail().equals(person.getEmail()))
                    return false;
            }
        }
        return true;
    }
}
