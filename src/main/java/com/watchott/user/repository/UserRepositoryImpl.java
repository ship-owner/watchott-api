package com.watchott.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.watchott.user.dto.UserDto;
import lombok.RequiredArgsConstructor;

import static com.watchott.user.entity.QUser.user;

/**
 * packageName    : watchott.repository
 * fileName       : UserRepositoryImpl
 * author         : shipowner
 * date           : 2023-09-21
 * description    : User 엔티티를 관리하기 위한 Custom Repository 구현체
 */

@RequiredArgsConstructor
public class UserRepositoryImpl  implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * methodName : findUserDtoByEmail
     * author : shipowner
     * description : 사용자 이메일로 조회하여 DTO로 반환
     *
     * @return list CommentDto
     */
    @Override
    public UserDto findUserDtoByEmail(String email) {
        return queryFactory
                .select(Projections.fields(UserDto.class
                        , user.userId
                        , user.email
                        , user.name
                ))
                .from(user)
                .where(user.email.eq(email))
                .fetchOne();
    }

    @Override
    public UserDto findUserDtoByUserId(String userId) {
        return queryFactory
                .select(Projections.fields(UserDto.class
                        , user.userId
                        , user.email
                        , user.name
                ))
                .from(user)
                .where(user.userId.eq(userId))
                .fetchOne();
    }
}
