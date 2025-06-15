package com.watchott.movie.repository;

import com.watchott.movie.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : watchott.repository
 * fileName       : CommentRepository
 * author         : shipowner
 * date           : 2023-10-01
 * description    : Comment 엔티티를 관리하기 위한 Repository
 */

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustorm {
}
