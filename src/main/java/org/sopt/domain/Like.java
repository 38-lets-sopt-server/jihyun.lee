package org.sopt.domain;

import jakarta.persistence.*;

@Entity
@Table(
        name = "likes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"})
)
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    protected Like() {}

    public Like(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Post getPost() { return post; }
}
