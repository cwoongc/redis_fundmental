package com.cwoongc.study.redis_fundmental.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.annotation.Resources;

@Slf4j
@Component
public class CartService {

    private final String KEY = "cart";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 사용자 장바구니 전체 조회
     * @param userId
     */
    public void printUserCart(String userId) {
        log.info("[hgetall]");
        redisTemplate.boundHashOps(String.format("cart:%s",userId))
                .entries()
                .forEach((k,v)->{
                    log.info(String.format("productId: %s, quantity: %s",k,v));
                });

    }

    /**
     * 사용자 장바구니 상품 등
     * @param userId
     * @param productId
     * @param productQuantity
     */
    public void addUserCart(String userId, String productId, int productQuantity) {
        redisTemplate.boundHashOps(String.format("cart:%s",userId))
                .put(productId, ""+productQuantity);
        log.info("[hset]");
        log.info(String.format("%s %s",productId, ""+productQuantity));

    }
    /**
     * 사용자 장바구니 상품 수량 증감
     *
     */
    public void incrUserCartProductQuantity(String userId, String productId, int incr) {
        log.info("[hincrby key field increment]");
        redisTemplate.boundHashOps(String.format("cart:%s",userId))
                .increment(productId, incr);
        log.info(String.format("userId: %s, productId: %s, amount: %d",userId,productId,incr));
    }

    /**
     * 장바구니 삭제
     * @param userId
     */
    public void delUserCart(String userId) {
        log.info(String.format("[del cart:%s]",userId));
        redisTemplate.delete(String.format("cart:%s", userId));
    }



}
