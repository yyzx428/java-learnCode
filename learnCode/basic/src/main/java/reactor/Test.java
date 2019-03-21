package reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import java.time.Duration;

public class Test {
    public static void main(String[] args) {
        /*StepVerifier.create(Flux.just("a", "b"))
                .expectNext("a")
                .expectNext("b")
                .verifyComplete();

        StepVerifier.withVirtualTime(() -> Flux.interval(Duration.ofHours(4), Duration.ofDays(1)).take(2))
                .expectSubscription()
                .expectNoEvent(Duration.ofHours(4))
                .expectNext(0L)
                .thenAwait(Duration.ofDays(1))
                .expectNext(1L)
                .verifyComplete();*/

        Flux.just(1, 0).map(x -> 1 / x).checkpoint("test").subscribe(System.out::println);


    }
}
