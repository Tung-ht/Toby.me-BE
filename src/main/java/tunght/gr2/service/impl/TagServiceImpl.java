package tunght.gr2.service.impl;

import tunght.gr2.entity.ArticleTagRelationEntity;
import tunght.gr2.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tunght.gr2.service.TagService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<String> listOfTags() {
        return tagRepository.findAll().stream().map(ArticleTagRelationEntity::getTag).distinct().collect(Collectors.toList());
    }
}
