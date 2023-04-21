package com.elife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ruoyi
 */

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class }, scanBasePackages = {"com.elife"})
public class RuoYiApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println(
                " ┏━━━━━━━━━━━━━━━━┓\n" +
                " ┃   系统起动成功   ┃\n" +
                " ┃   不用写遗书了   ┃\n" +
                " ┃     <se.2>     ┃\n" +
                " ┃                ┃\n" +
                " ┃  spring跑起来了 ┃\n" +
                " ┃                ┃\n" +
                " ┗━━━━━━━━━━━━━━━━┛\n" +
                " \\ (´;ω;｀) ﾉ +衰弱"
        );
    }
}
