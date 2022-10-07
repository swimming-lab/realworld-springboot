package swm.realworld.domain.article.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.realworld.domain.article.Article;
import swm.realworld.domain.common.BaseEntity;
import swm.realworld.domain.user.User;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body", nullable = false)
    private String body;

    @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
}
