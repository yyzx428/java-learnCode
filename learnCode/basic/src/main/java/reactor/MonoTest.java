package reactor;

import reactor.core.publisher.Mono;

public class MonoTest {
    public static void main(String[] args) {
        Mono.fromSupplier(()-> "Hello").subscribe(System.out::println);
        Mono.justOrEmpty("Hello").subscribe(System.out::println);
        Mono.create(sink->sink.success("hello")).subscribe(System.out::println);
    }
}
