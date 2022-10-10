package swm.realworld.domain.article;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swm.realworld.domain.article.tag.Tag;
import swm.realworld.domain.user.UserDto;
import swm.realworld.domain.user.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;

    @Transactional
    public Article createArticle(ArticleDto articleDto, UserDto.Auth authUser) {
        final var author = userService.getUserById(authUser.getId()).orElseThrow();
        String slug = String.join("-", articleDto.getTitle().split(" "));

        Article article = Article.builder()
                .slug(slug)
                .title(articleDto.getTitle())
                .description(articleDto.getDescription())
                .body(articleDto.getBody())
                .author(author)
                .build();

        Set<Tag> tags = new HashSet<>();
        for (String tag: articleDto.getTagList()) {
            tags.add(Tag.builder().name(tag).build());
        }
        article.setTags(tags);

        return articleRepository.save(article);
    }

    public List<Article> getListByAuthorUsername(String username, Pageable pageable) {
        return articleRepository.findByAuthorUsername(username, pageable);
    }

    public List<Article> getListAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
}
