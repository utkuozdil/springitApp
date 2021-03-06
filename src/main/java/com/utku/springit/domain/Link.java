package com.utku.springit.domain;

import com.utku.springit.service.BeanUtil;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.ocpsoft.prettytime.PrettyTime;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class Link extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @NotEmpty(message = "title can't be empty")
    private String title;

    @NonNull
    @NotEmpty(message = "url can't be empty")
    @URL(message = "need valid URL")
    private String url;

    @ToString.Exclude
    @OneToMany(mappedBy = "link")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "link")
    private List<Vote> voteList = new ArrayList<>();

    private int voteCount = 0;

    @ManyToOne
    private User user;

    public void addComment(Comment comment) {
        commentList.add(comment);
    }

    public String getDomainName() throws URISyntaxException {
        URI uri = new URI(this.url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    public String getPrettyTime() {
        PrettyTime pt = BeanUtil.getBean(PrettyTime.class);
        return pt.format(convertToDateViaInstant(getCreationDate()));
    }

    private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
}
