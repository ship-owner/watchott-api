package com.watchott.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

/**
 * packageName    : watchott.dto
 * fileName       : CommentDto
 * author         : shipowner
 * date           : 2023-10-01
 * description    : 댓글
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long commentId;
    private Long parentId;
    private Integer movieId;
    private String content;
    private String userName;
    private Integer dispOrder;
    private Date modDate;
    private Date regDate;

    private List<CommentDto> children;
}
