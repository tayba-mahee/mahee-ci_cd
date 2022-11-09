package com.example.contest.services;


import com.example.contest.model.Contest;
import com.example.contest.model.Team;
import com.example.contest.repository.ContestRepository;
import com.example.contest.repository.TeamRepository;
import com.example.contest.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private RegistrationService registrationService;

    public boolean isContestEditable(Team team) {
        List<Contest> contests = contestRepository.findAll();

        for (Contest contest : contests) {
            if (contest.getTeams().contains(team) && !contest.getEditable()) {
                return false;
            }
        }
        return true;
    }

    public ResponseEntity<Object> promote(Long superContestID, Long teamId) throws CloneNotSupportedException {

        if (contestRepository.findById(superContestID).isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Contest not found");
        }

        if (teamRepository.findById(teamId).isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Team not found");
        }

        Contest superContest = contestRepository.findById(superContestID).get();
        Team team = teamRepository.findById(teamId).get();


        if (!(team.getRank() >= 1 && team.getRank() <= 5)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Team rank is not within 1-5");
        }

        // check if the team is part of sub-contest of super-contest
        boolean flag = false;
        List<Contest> contests = contestRepository.findAll();
        for (Contest contest : contests) {
            if (contest.getParentContest() == superContest && contest.getTeams().contains(team)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Team is not part of any sub-contest of this super-Contest ");
        }

        String str = registrationService.Eligibility(superContest, team);
        if (str != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(str);
        }

        Team newTeam = team.clone();

        superContest.getTeams().add(newTeam);
        contestRepository.save(superContest);

        return ResponseEntity.ok().body(superContest);

    }

}
