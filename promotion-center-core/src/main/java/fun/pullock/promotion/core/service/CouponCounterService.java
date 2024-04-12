package fun.pullock.promotion.core.service;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CouponCounterService {

    private static final String COUPON_CLAIMED_COUNTER_PREFIX = "COUPON_CLAIMED_COUNT_";

    private static final String USER_DAILY_CLAIMED_COUNTER_PREFIX = "USER_DAILY_CLAIMED_COUNT_";

    private static final String USER_TOTAL_CLAIMED_COUNTER_PREFIX = "USER_TOTAL_CLAIMED_COUNT_";


    @Resource
    private RedisTemplate<String, Object> stringObjectRedisTemplate;


    public Long claimed(Long couponId) {
        String key = String.format("%s%s", COUPON_CLAIMED_COUNTER_PREFIX, couponId);
        Object claimed = stringObjectRedisTemplate.opsForValue().get(key);
        if (claimed == null) {
            return null;
        }

        return ((Number) claimed).longValue();
    }

    public Long claim(Long couponId, Long count) {
        String key = String.format("%s%s", COUPON_CLAIMED_COUNTER_PREFIX, couponId);
        return stringObjectRedisTemplate.opsForValue().increment(key, count);
    }

    public Long userDailyClaimed(Long userId, Long couponId, LocalDateTime now) {
        String key = String.format(
                "%s%s_%s_%s",
                USER_DAILY_CLAIMED_COUNTER_PREFIX,
                userId,
                couponId,
                now.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        );
        Object claimed = stringObjectRedisTemplate.opsForValue().get(key);
        if (claimed == null) {
            return null;
        }

        return ((Number) claimed).longValue();
    }

    public Long userTotalClaimed(Long userId, Long couponId) {
        String key = String.format("%s%s_%s", USER_TOTAL_CLAIMED_COUNTER_PREFIX, userId, couponId);
        Object claimed = stringObjectRedisTemplate.opsForValue().get(key);
        if (claimed == null) {
            return null;
        }

        return ((Number) claimed).longValue();
    }

    public Long userDailyClaim(Long userId, Long couponId, Long count, LocalDateTime now) {
        String key = String.format(
                "%s%s_%s_%s",
                USER_DAILY_CLAIMED_COUNTER_PREFIX,
                userId,
                couponId,
                now.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        );
        return stringObjectRedisTemplate.opsForValue().increment(key, count);
    }

    public Long userTotalClaim(Long userId, Long couponId, Long count) {
        String key = String.format("%s%s_%s", USER_TOTAL_CLAIMED_COUNTER_PREFIX, userId, couponId);
        return stringObjectRedisTemplate.opsForValue().increment(key, count);
    }
}
