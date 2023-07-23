package tunght.toby.be.service.impl;

import tunght.toby.be.entity.AvailableTag;
import tunght.toby.be.repository.AvailableTagRepository;
import tunght.toby.be.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tunght.toby.be.service.TagService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final AvailableTagRepository availableTagRepository;

    @Override
    public List<String> getTagsByPopularity(Integer isApproved) {
        return tagRepository.getTagsByPopularity(isApproved);
    }

    @Override
    public List<String> getPinnedTags() {
        return availableTagRepository.getAllByIsPinned(1)
                .stream()
                .map(AvailableTag::getTagName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getDropDownTags() {
        return availableTagRepository.findAll()
                .stream()
                .map(AvailableTag::getTagName)
                .collect(Collectors.toList());
    }
}
