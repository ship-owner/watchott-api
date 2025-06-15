package com.watchott.movie.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.watchott.movie.dto.CommentDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.watchott.movie.entity.QComment.comment;

/**
 * packageName    : watchott.repository
 * fileName       : CommentRepositoryImpl
 * author         : shipowner
 * date           : 2023-10-01
 * description    : Comment 엔티티를 관리하기 위한 Custom Repository 구현체
 */

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustorm {

    private final JPAQueryFactory queryFactory;

    /**
     * methodName : findCommentDtoByMovieId
     * author : shipowner
     * description : 댓글 조회하여 DTO로 반환
     *
     * @return list CommentDto
     */
    @Override
    public List<CommentDto> findCommentDtoByMovieId(Integer movieId) {
        return queryFactory
                .select(Projections.fields(
                                        CommentDto.class
                                        ,comment.id.as("commentId")
                                        ,comment.movieId
                                        ,comment.parent.id.as("parentId")
                                        ,comment.content
                                        ,comment.user.name.as("userName")
                                        ,comment.modDate
                                        ,comment.regDate)
                )
                .from(comment)
                .where(comment.movieId.eq(movieId))
                .orderBy(comment.id.asc())
                .fetch();
    }
}
