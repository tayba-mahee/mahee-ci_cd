package com.example.contest.controller;


import com.example.contest.model.Contest;
import com.example.contest.model.Person;
import com.example.contest.model.Team;
import com.example.contest.repository.ContestRepository;
import com.example.contest.repository.PersonRepository;
import com.example.contest.repository.TeamRepository;
import com.example.contest.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TeamService teamService;

    @GetMapping(value = "/teams")
    public ResponseEntity<Object> getTeamsbyID() {
        return new ResponseEntity<>(teamRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/teams")
    public Team postTeam(@RequestBody Team team) {
        return teamRepository.save(team);
    }

    @GetMapping(value = "/teams/{id}")
    public ResponseEntity<Object> getTeamsbyID(@PathVariable("id") Long id) {
        return new ResponseEntity(teamRepository.findById(id), HttpStatus.OK);
    }

    // edit team
    @PatchMapping(path = "/team/{teamID}")
    public ResponseEntity<Object> patchOrder(@PathVariable("teamID") Long teamID,
                                             @RequestBody Team patch) {
        if (contestRepository.findById(teamID).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No contest with teamID " + teamID);
        }
        Team existingTeam = teamRepository.findById(teamID).get();
        if (!teamService.isContestEditable(existingTeam)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assigned contest is readonly");
        }

        if (patch.getName() != null) {
            existingTeam.setName(patch.getName());
        }
        if (patch.getRank() != null) {
            existingTeam.setRank(patch.getRank());
        }
        if (patch.getState() != null) {
            existingTeam.setState(patch.getState());
        }
        if (patch.getMembers().size() != 0) {
            existingTeam.setMembers(patch.getMembers());
        }
        if (patch.getCoach() != null) {
            existingTeam.setCoach(patch.getCoach());
        }

        teamRepository.save(existingTeam);
        return ResponseEntity.ok().body(existingTeam);
    }

}
