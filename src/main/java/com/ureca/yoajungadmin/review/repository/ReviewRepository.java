package com.ureca.yoajungadmin.review.repository;

import com.ureca.yoajungadmin.review.entity.Review;
import com.ureca.yoajungadmin.review.repository.custom.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
}
