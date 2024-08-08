package project.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "search_log")
public class searchLog {
    @Id @GeneratedValue
    private int sequence;

    @Lob
    @Column(nullable = false)
    private String searchWord;

    @Lob
    private String relationWord;

    @Column(nullable = false)
    private boolean relation;
}
