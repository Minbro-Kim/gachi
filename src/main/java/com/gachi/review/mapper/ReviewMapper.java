package com.gachi.review.mapper;

import com.gachi.review.dto.ReviewDto;
import com.gachi.review.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
  public ReviewDto toDto(Review review, ReviewDto.AuthorDto author) {
    return new ReviewDto(
        review.getId(),
        review.getContentId(),
        author,
        review.getText(),
        review.getRating()
    );
  }
}
