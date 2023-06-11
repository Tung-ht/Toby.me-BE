package tunght.toby.be.service.impl;

import tunght.toby.be.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tunght.toby.be.service.TagService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<String> getTagsByPopularity() {
        return tagRepository.getTagsByPopularity();
    }
}
