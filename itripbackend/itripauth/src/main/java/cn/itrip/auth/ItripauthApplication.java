package cn.itrip.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("cn.itrip.dao")  //自动扫描dao层接口
@ComponentScan("cn.itrip")  //自动扫描注解的
public class ItripauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItripauthApplication.class, args);
    }

}
