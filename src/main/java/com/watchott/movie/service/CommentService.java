package com.watchott.movie.service;

import com.watchott.movie.dto.CommentDto;
import com.watchott.movie.entity.Comment;
import com.watchott.movie.repository.CommentRepository;
import com.watchott.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : watchott.core.service
 * fileName       : CommentService
 * author         : shipowner
 * date           : 2023-10-01
 */

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    /**
     * methodName : save
     * author : shipowner
     * description : 댓글 저장
     *
     */
    public void save(CommentDto commentDto, String userId){
        if (userId != null) {
            userRepository.findByUserId(userId).ifPresentOrElse(user -> {
                Comment parent = null;

                if (commentDto.getParentId() != null)
                    parent = commentRepository.findById(commentDto.getParentId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 상위댓글입니다."));

                Integer depth = parent != null ? parent.getDepth() + 1 : 0;

                commentRepository.save(Comment.builder()
                        .user(user)
                        .content(commentDto.getContent())
                        .movieId(commentDto.getMovieId())
                        .parent(parent)
                        .depth(depth)
                        .build());
            }, () -> {
                throw new IllegalStateException("존재하지 않는 회원입니다.");
            });
        } else {
            throw new IllegalArgumentException("ID 값이 null입니다.");
        }
    }

    /**
     * methodName : findCommentByMovieId
     * author : shipowner
     * description : 댓글 목록 조회 (계층형 구조)
     *
     */
    public List<CommentDto> findCommentByMovieId(Integer movieId){
        List<CommentDto> commentList = commentRepository.findCommentDtoByMovieId(movieId);

        List<CommentDto> result = new ArrayList<>();

        for (CommentDto comment : commentList) {
            if(comment.getParentId() == null) {
                addCommentReply(commentList, comment);
                result.add(comment);
            }
        }

        return result;
    }

    private void addCommentReply(List<CommentDto> commentList, CommentDto comment){
        for (CommentDto child : commentList) {
            if(comment.getCommentId().equals(child.getParentId())) {
                if(comment.getChildren() == null) comment.setChildren(new ArrayList<>());
                comment.getChildren().add(child);

                addCommentReply(commentList, child);
            }
        }
    }
}
