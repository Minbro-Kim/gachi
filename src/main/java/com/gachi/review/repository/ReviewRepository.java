package com.gachi.review.repository;

import com.gachi.review.entity.Review;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

  boolean existsByContentIdAndAuthorId(UUID contentId, UUID authorId);
}
