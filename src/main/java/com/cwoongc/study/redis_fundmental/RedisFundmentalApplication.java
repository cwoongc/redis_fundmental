package com.cwoongc.study.redis_fundmental;

import com.cwoongc.study.redis_fundmental.service.BidService;
import com.cwoongc.study.redis_fundmental.service.CartService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RedisFundmentalApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisFundmentalApplication.class, args);
    }


    @Bean
    CommandLineRunner run1(CartService cartService, BidService bidService) {
        return args -> {

//            service.printUserCart("hymoon");
//
//
//            service.printUserCart("sjchoi");
//
//            service.incrUserCartProductQuantity("sjchoi","p3",-2);


//            cartService.delUserCart("wcchoi");


            bidService.dealDgbTry();



        };
    }
}
