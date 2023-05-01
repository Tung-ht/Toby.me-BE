package tunght.gr2.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "follows", uniqueConstraints = {
        @UniqueConstraint(name = "u_follow_followee_pair_must_be_unique", columnNames = {"followee_id", "follower_id"})
})
public class FollowEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "followee_id")
    private UserEntity followee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "follower_id")
    private UserEntity follower;
}
