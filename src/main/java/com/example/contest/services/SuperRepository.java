package com.example.contest.services;

import com.example.contest.model.Contest;
import com.example.contest.model.Person;
import com.example.contest.model.State;
import com.example.contest.model.Team;
import com.example.contest.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Repository
@Transactional
public class SuperRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TeamRepository teamRepository;

    public void populate() throws ParseException {
//        Person person1 = new Person();
//        person1.setName("Alex");
//        person1.setEmail("alex@baylor.com");
//        person1.setBirthdate(new Date(2000, 1, 1));
//        person1.setUniversity("Baylor University");
//        em.persist(person1);
//
//        Person person2 = new Person();
//        person2.setName("Becky");
//        person2.setEmail("becky@baylor.com");
//        person2.setBirthdate(new Date(2001, 1, 1));
//        person2.setUniversity("Baylor University");
//        em.persist(person2);
//
//        Person person3 = new Person();
//        person3.setName("Cathy");
//        person3.setEmail("cathy@baylor.com");
//        person3.setBirthdate(new Date(2002, 1, 1));
//        person3.setUniversity("Baylor University");
//        em.persist(person3);
//
//        Person person4 = new Person();
//        person4.setName("Dillan");
//        person4.setEmail("dillan@baylor.com");
//        person4.setBirthdate(new Date(2000, 1, 1));
//        person4.setUniversity("Baylor University");
//        em.persist(person4);
//
//        Person person5 = new Person();
//        person5.setName("El");
//        person5.setEmail("el@baylor.com");
//        person5.setBirthdate(new Date(2000, 1, 1));
//        person5.setUniversity("Baylor University");
//        em.persist(person5);
//
//        Person person6 = new Person();
//        person6.setName("Fiona");
//        person6.setEmail("fiona@baylor.com");
//        person6.setBirthdate(new Date(2000, 1, 1));
//        person6.setUniversity("Baylor University");
//        em.persist(person6);
//
//        Person person7 = new Person();
//        person7.setName("Gini");
//        person7.setEmail("gini@baylor.com");
//        person7.setBirthdate(new Date(2004, 1, 1));
//        person7.setUniversity("Baylor University");
//        em.persist(person7);
//
//        Person person8 = new Person();
//        person8.setName("Han");
//        person8.setEmail("han@baylor.com");
//        person8.setBirthdate(new Date(2005, 1, 1));
//        person8.setUniversity("Baylor University");
//        em.persist(person8);
//
//        Person person9 = new Person();
//        person9.setName("Ian");
//        person9.setEmail("ian@baylor.com");
//        person9.setBirthdate(new Date(2000, 1, 1));
//        person9.setUniversity("Baylor University");
//        em.persist(person9);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Person manager1 = new Person();
        manager1.setName("Alex");
        manager1.setEmail("alex@baylor.com");
        manager1.setBirthdate(formatter.parse("2000-10-10"));
        manager1.setUniversity("Baylor University");
        em.persist(manager1);
//
//        Person coach1 = new Person();
//        coach1.setName("Alex");
//        coach1.setEmail("alex@baylor.com");
//        coach1.setBirthdate(new Date(2000, 1, 1));
//        coach1.setUniversity("Baylor University");
//        em.persist(coach1);

//        Team team1 = new Team();
//        team1.setName("A");
//        team1.setRank(0);
//        team1.setState(State.PENDING);
//        team1.getMembers().add(person1);
//        team1.getMembers().add(person2);
//        team1.getMembers().add(person3);
//        team1.setCoach(coach1);
//        em.persist(team1);
//
//        Team team2 = new Team();
//        team2.setName("B");
//        team2.setRank(1);
//        team2.setState(State.ACCEPTED);
//        team2.getMembers().add(person4);
//        team2.getMembers().add(person5);
//        team2.getMembers().add(person6);
//        team2.setCoach(coach1);
//        em.persist(team2);
//
//        Team team3 = new Team();
//        team3.setName("C");
//        team3.setRank(2);
//        team3.setState(State.ACCEPTED);
//        team3.getMembers().add(person7);
//        team3.getMembers().add(person8);
//        team3.getMembers().add(person9);
//        team3.setCoach(coach1);
//        em.persist(team3);


        Contest contest = new Contest();
        contest.setCapacity(100);
        contest.setName("Final Contest");
        contest.getManagers().add(manager1);
        contest.setRegistration_allowed(true);
        contest.setRegistration_from(formatter.parse("2022-12-10"));
        contest.setRegistration_to(formatter.parse("2022-11-10"));
        contest.setDate(formatter.parse("2022-10-10"));

        Contest subcontest = new Contest();
        subcontest.setCapacity(100);
        subcontest.setName("Regional Contest");
        subcontest.getManagers().add(manager1);
        subcontest.setRegistration_allowed(true);
        subcontest.setRegistration_from(formatter.parse("2022-10-10"));
        subcontest.setRegistration_to(formatter.parse("2022-10-10"));
        subcontest.setDate(formatter.parse("2022-10-10"));

        contest.addSubcontest(subcontest);
        em.persist(contest);
        em.persist(subcontest);

    }

    public List<Map<String, Object>> ageGroup() {

        List<Team> teamList = teamRepository.findAll();
        Map<Long, Set<String>> ageGroup = new HashMap<>();

        for (Team team : teamList) {
            for (Person member : team.getMembers()) {
                long currentMillis = System.currentTimeMillis();
                long year = TimeUnit.MILLISECONDS.toDays(currentMillis - member.getBirthdate().getTime()) / 365;

                if (!ageGroup.containsKey(year))
                    ageGroup.put(year, new HashSet<>());
                ageGroup.get(year).add(member.getName());
            }
        }
        List<Map<String, Object>> result = new ArrayList<>();
        for (Long age : ageGroup.keySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("age", age);
            map.put("Persons", ageGroup.get(age));
            result.add(map);
        }
        return result;
    }

}
