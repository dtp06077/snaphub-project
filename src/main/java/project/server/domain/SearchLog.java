package project.server.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "search_log")
@NoArgsConstructor
public class SearchLog {

    @Id @GeneratedValue
    private int sequence;

    @Lob
    @Column(nullable = false)
    private String searchWord;

    @Lob
    private String relationWord;

    @Column(nullable = false)
    private boolean relation;

    //생성자
    public SearchLog (String searchWord, String relationWord, boolean relation) {
        this.searchWord = searchWord;
        this.relationWord = relationWord;
        this.relation = relation;
    }
}
