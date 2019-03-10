package com.utku.springit.controller;

import com.utku.springit.domain.Comment;
import com.utku.springit.domain.Link;
import com.utku.springit.service.CommentService;
import com.utku.springit.service.LinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class LinkController {

    public LinkController(LinkService linkService, CommentService commentService) {
        this.linkService = linkService;
        this.commentService = commentService;
    }

    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);

    private LinkService linkService;

    private CommentService commentService;

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("links", linkService.findAll());
        return "link/list";
    }


    @GetMapping("/link/{id}")
    public String read(@PathVariable Long id, Model model) {
        Optional<Link> optionalLink = linkService.findById(id);
        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            Comment comment = new Comment();
            comment.setLink(link);
            model.addAttribute("comment", comment);
            model.addAttribute("link", link);
            model.addAttribute("success", model.containsAttribute("success"));
            return "link/view";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/link/submit")
    public String newLinkForm(Model model) {
        model.addAttribute("link", new Link());
        return "link/submit";
    }

    @PostMapping("/link/submit")
    public String createLink(@Valid Link link, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            logger.info("validation errors");
            model.addAttribute("link", link);
            return "link/submit";
        } else {
            linkService.save(link);
            logger.info("link save success");
            redirectAttributes.addAttribute("id", link.getId()).addFlashAttribute("success", true);
            return "redirect:/link/{id}";
        }
    }

    @Secured({"ROLE_USER"})
    @PostMapping("/link/comments")
    public String addComment(@Valid Comment comment, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            logger.info("problem adding a new comment");
        else {
            commentService.save(comment);
            logger.info("Success adding a new comment");
        }
        return "redirect:/link/" + comment.getLink().getId();
    }
}
