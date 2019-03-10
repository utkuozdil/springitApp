package com.utku.springit.controller;

import com.utku.springit.domain.Link;
import com.utku.springit.domain.Vote;
import com.utku.springit.service.LinkService;
import com.utku.springit.service.VoteService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VoteController {

    public VoteController(VoteService voteService, LinkService linkService) {
        this.voteService = voteService;
        this.linkService = linkService;
    }

    private VoteService voteService;

    private LinkService linkService;

    @Secured({"ROLE_USER"})
    @GetMapping("vote/link/{linkID}/direction/{direction}/votecount/{voteCount}")
    public int vote(@PathVariable Long linkID, @PathVariable short direction, @PathVariable int voteCount) {
        Optional<Link> optionalLink = linkService.findById(linkID);
        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            Vote vote = new Vote(direction, link);
            voteService.save(vote);
            voteCount = voteCount + direction;
            link.setVoteCount(voteCount);
            linkService.save(link);
        }
        return voteCount;
    }
}
