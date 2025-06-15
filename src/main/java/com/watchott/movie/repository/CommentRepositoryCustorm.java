package com.watchott.movie.repository;

import com.watchott.movie.dto.CommentDto;
import java.util.List;

/**
 * packageName    : watchott.repository
 * fileName       : CommentRepositoryCustorm
 * author         : shipowner
 * date           : 2023-10-01
 * description    : Comment 엔티티를 관리하기 위한 Custorm Repository
 */

public interface CommentRepositoryCustorm {

    public List<CommentDto> findCommentDtoByMovieId(Integer movieId);
}
