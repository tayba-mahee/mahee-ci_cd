package com.example.contest.controller;

import com.example.contest.model.Contest;
import com.example.contest.model.Promote;
import com.example.contest.model.Team;
import com.example.contest.repository.ContestRepository;
import com.example.contest.repository.TeamRepository;
import com.example.contest.services.ContestService;
import com.example.contest.services.TeamService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ContestController {

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ContestService contestService;

    @Autowired
    private TeamService teamService;
    private Long id;


    @GetMapping(path = "/contest")
    public ResponseEntity<List<Contest>> getContests() {
        List<Contest> contest = contestRepository.findAll();
        return ResponseEntity.ok().body(contest);
    }


    @PostMapping(value = "/contest")
    public Contest postContest(@RequestBody Contest contest) {
        if (contest.getEditable() == null)
            contest.setEditable(true);
        return contestRepository.save(contest);
    }

    @GetMapping(value = "/contest/{id}")
    public ResponseEntity<Object> getContestById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(contestRepository.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/contest/{id}/teams")
    public ResponseEntity<Object> getTeamsofContest(@PathVariable("id") Long id) {
        return new ResponseEntity<>(contestRepository.findById(id).get().getTeams(), HttpStatus.OK);
    }

    @GetMapping(path = "/contest/setEditable/{id}")
    public ResponseEntity<Object> patchOrder(@PathVariable("id") Long id) {
        if (contestRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ContestID " + id + " doesn't exist");
        }

        Contest contest = contestRepository.findById(id).get();
        return contestService.setEditable(contest);
    }


    @GetMapping(path = "/contest/setReadOnly/{id}")
    public ResponseEntity<Object> readonly(@PathVariable("id") Long id) {

        if (contestRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ContestID " + id + " doesn't exist");
        }
        Contest contest = contestRepository.findById(id).get();
        return contestService.setReadOnly(contest);
    }

    @GetMapping(path = "/contest/occupancy/{id}")
    public ResponseEntity<Object> contestOccupancy(@PathVariable("id") Long id) {
        if (contestRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ContestID " + id + " doesn't exist");
        }
        return new ResponseEntity<>(contestService.contestOccupancy(id), HttpStatus.OK);
    }


    // edit contest
    @PatchMapping(path = "/contest/{contestID}")
    public ResponseEntity<Object> patchOrder(@PathVariable("contestID") Long contestID,
                                             @RequestBody Contest patch) {
        if (contestRepository.findById(contestID).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No contest with contestID " + contestID);
        }
        Contest existingContest = contestRepository.findById(contestID).get();
        if (!existingContest.getEditable()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Contest not editable");
        }

        if (patch.getName() != null) {
            existingContest.setName(patch.getName());
        }
        if (patch.getCapacity() != 0) {
            existingContest.setCapacity(patch.getCapacity());
        }
        if (patch.getDate() != null) {
            existingContest.setDate(patch.getDate());
        }
        if (patch.getRegistration_allowed() != null) {
            existingContest.setRegistration_allowed(patch.getRegistration_allowed());
        }
        if (patch.getRegistration_from() != null) {
            existingContest.setRegistration_from(patch.getRegistration_from());
        }
        if (patch.getRegistration_to() != null) {
            existingContest.setRegistration_to(patch.getRegistration_to());
        }
        if (patch.getTeams().size() != 0) {
            existingContest.setTeams(patch.getTeams());
        }
        if (patch.getManagers().size() != 0) {
            existingContest.setManagers(patch.getManagers());
        }
        if (patch.getEditable() != null) {
            existingContest.setEditable(patch.getEditable());
        }
        if (patch.getParentContest() != null) {
            existingContest.setParentContest(patch.getParentContest());
        }
        return new ResponseEntity<>(contestRepository.save(existingContest), HttpStatus.OK);
    }


    @SneakyThrows
    @PostMapping(value = "/promote")
    public ResponseEntity<Object> promote(@RequestBody Promote promote) {
        return teamService.promote(promote.getSuperContestId(), promote.getTeamId());
    }


}

