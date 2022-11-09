package com.example.contest;

import com.example.contest.model.Contest;
import com.example.contest.model.Person;
import com.example.contest.model.Team;
import com.example.contest.repository.ContestRepository;
import com.example.contest.repository.PersonRepository;
import com.example.contest.repository.TeamRepository;
import com.example.contest.services.SuperRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ContestApplicationTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TeamRepository teamRepository;


    @Test
    public void contestTeams() {
        Contest contest = contestRepository.findByName("Regional Contest");
        System.out.println("\n-----------------------------" + "\n Contest Team Size: " + contest.getTeams().size());
        for (Team team : contest.getTeams()) {
            System.out.println("\n-----------------------------" + "\n Team Name: " + team.getName());
            for (Person member : team.getMembers()) {
                System.out.println("\n Member name:" + member.getName());
            }
        }
        System.out.println("\n-----------------------------");
    }

    @Test
    public void ageGroup() {

        List<Team> teamList = teamRepository.findAll();
        Map<Long, Set<String>> ageGroup = new HashMap<>();

        for (Team team : teamList) {
            for (Person member : team.getMembers()) {
                LocalDate start_date = LocalDate.of(member.getBirthdate().getYear(), member.getBirthdate().getMonth(), member.getBirthdate().getDate());

                // End date
                LocalDate end_date = LocalDate.now();

                Period diff = Period.between(start_date, end_date);
                long year = diff.getYears();

                if (!ageGroup.containsKey(year))
                    ageGroup.put(year, new HashSet<>());
                ageGroup.get(year).add(member.getName());
            }
        }
        System.out.println("\n-----------------------------");
        for (Long age : ageGroup.keySet()) {
            System.out.println("Age: " + age + ", Members: " + ageGroup.get(age));
        }
        System.out.println("\n-----------------------------");
    }

    @Test
    public void contestOccupancy() {
        List<Contest> contests = contestRepository.findAll();
        System.out.println("--------------------------------");
        for (Contest contest : contests) {
            System.out.println("\n \n Contest name: " + contest.getName() +
                    ", \n Current Contest occupancy: " + contest.getTeams().size() + "\n Contest capacity: "
                    + contest.getCapacity() + "\n" + "\n--------------------------------");
        }

    }
}
