package com.sathish.processorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.sathish.processorservice",
    "com.sathish.processorserviceservice"
})
public class ProcessorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcessorServiceApplication.class, args);
    }

}