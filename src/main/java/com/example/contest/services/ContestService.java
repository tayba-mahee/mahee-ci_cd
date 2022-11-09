package com.example.contest.services;

import com.example.contest.model.Contest;
import com.example.contest.repository.ContestRepository;
import com.example.contest.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContestService {

    @Autowired
    private ContestRepository contestRepository;

    public ResponseEntity<Object> setReadOnly(Contest contest) {
        contest.setEditable(false);
        contestRepository.save(contest);
        return ResponseEntity.ok().body(contest.getName() + " is readonly");
    }

    public ResponseEntity<Object> setEditable(Contest contest) {
        contest.setEditable(true);
        contestRepository.save(contest);
        return ResponseEntity.ok().body(contest.getName() + " is editable");
    }

    public Map<String, String> contestOccupancy(Long id) {
        Contest contest = contestRepository.findById(id).get();
        Map<String, String> m = new HashMap<>();
        m.put("Contest name", contest.getName());
        m.put("Current Contest occupancy", String.valueOf(contest.getTeams().size()));
        m.put("Contest capacity", String.valueOf(contest.getCapacity()));
        return m;
    }

}
