package tunght.toby.be.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "available_tags")
public class AvailableTag {
    @Id
    private Long id;
    private String tagName;
    private Integer isPinned;
}
