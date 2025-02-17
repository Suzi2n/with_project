package com.example.with_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing   /// 이 설정이 없으면, @CreatedDate, @LastModifiedDate가 동작하지 않아 createdAt, updatedAt이 null로 들어갈 수 있습니다.
@SpringBootApplication
public class WithProjectApplication {

    public static void main(String[] args) {

        SpringApplication.run(WithProjectApplication.class, args);
    }

}