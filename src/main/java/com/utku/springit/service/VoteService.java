package com.utku.springit.service;

import com.utku.springit.domain.Vote;
import com.utku.springit.repository.VoteRepository;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    private final VoteRepository voteRepository;

    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }
}
