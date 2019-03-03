package com.utku.springit;

import com.utku.springit.domain.Comment;
import com.utku.springit.domain.Link;
import com.utku.springit.domain.Vote;
import com.utku.springit.repository.CommentRepository;
import com.utku.springit.repository.LinkRepository;
import com.utku.springit.repository.VoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringitApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringitApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(LinkRepository linkRepository, CommentRepository commentRepository, VoteRepository voteRepository) {
        return args -> {
            Link link = new Link("title commandline", "url command line");
            linkRepository.save(link);

            Comment comment = new Comment("comment commandline", link);
            commentRepository.save(comment);
            link.addComment(comment);

            Vote vote = new Vote(11);
            voteRepository.save(vote);
        };
    }
}
