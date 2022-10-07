package swm.realworld.domain.article;

import lombok.Getter;
import lombok.NoArgsConstructor;
import swm.realworld.domain.common.BaseEntity;
import swm.realworld.domain.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;

@Getter
@Entity
@Table(name = "articles")
@NoArgsConstructor
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "body")
    private String body;

    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @JoinTable(name = "article_favorites",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false))
    @ManyToMany(fetch = FetchType.LAZY, cascade = PERSIST)
    private Set<User> userFavorited = new HashSet<>();
}
