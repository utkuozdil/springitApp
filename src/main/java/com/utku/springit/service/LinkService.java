package com.utku.springit.service;

import com.utku.springit.domain.Link;
import com.utku.springit.repository.LinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LinkService {

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    private final LinkRepository linkRepository;

    public List<Link> findAll() {
        return linkRepository.findAll();
    }

    public Optional<Link> findById(Long id) {
        return linkRepository.findById(id);
    }

    public Link save(Link link) {
        return linkRepository.save(link);
    }
}
