package com.cwoongc.study.redis_fundmental.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class BidService {

    private final String DGB_BUY = "dgb:buy";
    private final String DGB_SELL = "dgb:sell";



    /*
    zadd dgb:buy 250000 '{"time":"2018/08/09 13:04:01", "amount":1.53234, "user":"wcchoi"}'
    zadd dgb:buy 254000 '{"time":"2018/08/09 13:05:05", "amount:100.0, "user":"sjchoi"}'
    zadd dgb:buy 254000 '{"time":"2018/08/09 13:05:00", "amount:5.64321, "user":"hymoon"}'

    zadd dgb:sell 255000 '{"time":"2018/08/09 13:05:10", "amount:50.0, "user":"gradenstone"}'
    zadd dgb:sell 253000 '{"time":"2018/08/09 13:05:15", "amount:30.0, "user":"jwjeong"}'
     */

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public void dealDgbTry() {

        while(true) {

            Set<ZSetOperations.TypedTuple<String>> buyers = redisTemplate.boundZSetOps(DGB_BUY)
                    .reverseRangeWithScores(0L, 0L);

            Set<ZSetOperations.TypedTuple<String>> sellers = redisTemplate.boundZSetOps(DGB_SELL)
                    .rangeWithScores(0L, 0L);


            Object[] buyersArray = null;

            buyersArray = buyers.toArray();

            ZSetOperations.TypedTuple<String> buyer = (ZSetOperations.TypedTuple<String>) buyersArray[0];

            double buyPrice = buyer.getScore();


            Object[] sellersArray = null;

            sellersArray = sellers.toArray();

            ZSetOperations.TypedTuple<String> seller = (ZSetOperations.TypedTuple<String>) sellersArray[0];

            double sellPrice = seller.getScore();

            log.info(String.format("SellPrice: %s | BuyPrice: %s", sellPrice,buyPrice));

            if (buyPrice >= sellPrice) {
                log.info(String.format("Deal!!\nSeller: %s\nBuyer: %s", seller.getValue(), buyer.getValue()));

            } else {
                log.info("Deal Failed.");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }





    }
}
