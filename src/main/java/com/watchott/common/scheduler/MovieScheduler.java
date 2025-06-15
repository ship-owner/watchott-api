package com.watchott.common.scheduler;

import com.watchott.common.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.watchott.common.schedule
 * fileName       : MovieScheduler
 * author         : shipowner
 * date           : 2025-06-15
 * description    :
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieScheduler {

    private final RedisService redisService;

    @Scheduled(cron = "0 0 0 * * *")
    public void evictAllCachesAtMidnight() {
        redisService.deleteData("movieTrends");
        redisService.deleteData("movieLatest");
    }
}
