package tunght.toby.be.service;

import java.util.List;

public interface TagService {
    List<String> getTagsByPopularity(Integer isApproved);
}
