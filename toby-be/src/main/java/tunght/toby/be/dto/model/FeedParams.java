package tunght.toby.be.dto.model;

import lombok.*;

import javax.validation.constraints.AssertTrue;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedParams {
    protected Integer offset; // pageNo
    protected Integer limit; // pageSize

    @AssertTrue
    protected boolean getValidPage() {
        return (offset != null && limit != null) || (offset == null && limit == null);
    }
}
