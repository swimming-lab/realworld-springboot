package swm.realworld.domain.article;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swm.realworld.domain.user.UserDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    @Override
    public ArticleDto createArticle(ArticleDto article, UserDto.Auth authUser) {
        String slug = String.join("-", article.getTitle().split(" "));
        UserEntity author = User.builder()
                .id(authUser.getId())
                .name(authUser.getName())
                .bio(authUser.getBio())
                .image(authUser.getImage())
                .build();

        ArticleEntity articleEntity = ArticleEntity.builder()
                .slug(slug)
                .title(article.getTitle())
                .description(article.getDescription())
                .body(article.getBody())
                .author(author)
                .build();
        List<ArticleTagRelationEntity> tagList = new ArrayList<>();
        for (String tag: article.getTagList()) {
            tagList.add(ArticleTagRelationEntity.builder().article(articleEntity).tag(tag).build());
        }
        articleEntity.setTagList(tagList);

        articleEntity = articleRepository.save(articleEntity);
        return convertEntityToDto(articleEntity, false, 0L, false);
    }
}
