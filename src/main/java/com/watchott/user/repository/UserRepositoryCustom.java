package com.watchott.user.repository;

import com.watchott.user.dto.UserDto;

/**
 * packageName    : watchott.repository
 * fileName       : UserRepositoryCustom
 * author         : shipowner
 * date           : 2023-09-21
 * description    : User 엔티티를 관리하기 위한 Custom Repository
 */
public interface UserRepositoryCustom {

    UserDto findUserDtoByEmail(String email);
    UserDto findUserDtoByUserId(String userId);
}
