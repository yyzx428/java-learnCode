package com.example.springwebfluxsample;

import com.example.springwebfluxsample.dto.SampleQueryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@Slf4j
public class SpringWebfluxSampleApplication implements ApplicationRunner {

    @Autowired
    private WebClient client;

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxSampleApplication.class, args);
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("http://127.0.0.1:9000").build();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SampleQueryDTO queryDTO = SampleQueryDTO.builder().age(11).name("张三").build();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        client.post().uri("/getName")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(queryDTO), SampleQueryDTO.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .doFinally(s -> countDownLatch.countDown())
                .subscribeOn(Schedulers.single())
                .subscribe(c -> log.info("返回结果:{}", c));
    }
}
