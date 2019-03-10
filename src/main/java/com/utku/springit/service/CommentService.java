package com.utku.springit.service;

import com.utku.springit.domain.Comment;
import com.utku.springit.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    private final CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }
}
