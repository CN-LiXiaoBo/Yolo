package com.sicnu.yolo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.sicnu.yolo.mapper")
@EnableTransactionManagement
@EnableElasticsearchRepositories
public class YoloApplication {
    public static void main(String[] args) {
        SpringApplication.run(YoloApplication.class, args);
    }
}
