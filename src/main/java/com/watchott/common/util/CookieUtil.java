package com.watchott.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;

/**
 * packageName    : watchott.util
 * fileName       : CookieUtil
 * author         : shipowner
 * date           : 2023-09-21
 * description    : 쿠키 처리 유틸
 */

public class CookieUtil {

    private static String DOMAIN_NAME;

    /**
     * methodName : removeAllCookies
     * author : shipowner
     * description : 전체 쿠키 삭제
     *
     * @param req the req
     * @param res the res
     */
    public static void removeAllCookies(HttpServletRequest req, HttpServletResponse res) {
        Cookie[] cookies = req.getCookies();
        if(cookies != null)
        {
            for (int i = 0; i < cookies.length; i++)
            {
                cookies[i].setMaxAge(0);
                cookies[i].setPath("/");
                if(StringUtils.isEmpty(DOMAIN_NAME)) cookies[i].setDomain(DOMAIN_NAME);
                res.addCookie(cookies[i]);
            }
        }
    }

    /**
     * methodName : removeCookie
     * author : shipowner
     * description : 쿠키 삭제
     *
     * @param name the name
     * @param res  the res
     */
    public static void removeCookie(String name, HttpServletResponse res) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0); // 유효시간 만료
        cookie.setPath("/");
        if(StringUtils.isEmpty(DOMAIN_NAME))
        {
            cookie.setDomain(DOMAIN_NAME);
        }
        res.addCookie(cookie);
    }

    /**
     * methodName : setCookies
     * author : shipowner
     * description : 쿠키 설정
     *
     * @param name   the name
     * @param val    the val
     * @param res    the res
     * @param maxAge the max age
     */
    public static void setCookies(String name, String val, int maxAge, boolean httpOnly, HttpServletResponse res) {
        Cookie cookie = new Cookie(name, val);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        if(StringUtils.isEmpty(DOMAIN_NAME))
        {
            cookie.setDomain(DOMAIN_NAME);
        }
        cookie.setHttpOnly(httpOnly);
        res.addCookie(cookie);
    }

    /**
     * methodName : getCookieVal
     * author : shipowner
     * description : 쿠키값 조회
     *
     * @return list MovieDto
     */
    public static String getCookieVal(String name, HttpServletRequest req) {
        String result = "";
        Cookie[] cookies = req.getCookies();
        // 읽은 쿠기 정보 출력하기
        if (cookies != null)
        {
            for (Cookie cookie : cookies)
            {
                if(name.equals(cookie.getName()))
                {
                    result = cookie.getValue();
                    break;
                }
            }
        }
        return result;
    }

}
