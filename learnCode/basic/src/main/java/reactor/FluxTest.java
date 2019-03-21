package reactor;

import org.openjdk.jmh.annotations.Threads;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class FluxTest {
    public static void main(String[] args) {
       /* Flux.just("Hello", "World").subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
        AtomicInteger i = new AtomicInteger();
        Flux.generate(HashMap::new, (map, sink) -> {
            int value = new Random().nextInt();
            map.put(value, value);
            sink.next(map);
            if (i.get() == 2) {
                sink.complete();
            }
            i.getAndIncrement();
            return map;
        }).subscribe(System.out::println);

        Flux.create(sink -> {
            for (int  k= 0; k < 10; k++) {
                sink.next(k);
            }
            sink.complete();
        }).subscribe(System.out::println);

        Flux.range(1, 100).window(20).subscribe(System.out::println);
        Flux.interval(Duration.ofMillis(100)).windowTimeout(1001,Duration.ofMillis(1000)).take(2).toStream().forEach(System.out::println);

        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"))
                .subscribe(System.out::println);

        Flux.just("a", "b")
                .zipWith(Flux.just("c", "d"), (s1, s2) -> String.format("%s-%s", s1, s2))
                .subscribe(System.out::println);

        Flux.merge(Flux.interval(Duration.ofMillis(0),Duration.ofMillis(100)).take(5), Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5))
                .toStream()
                .forEach(System.out::println);
        Flux.mergeSequential(Flux.interval(Duration.ofMillis(0),Duration.ofMillis(100)).take(5), Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5))
                .toStream()
                .forEach(System.out::println);

        Flux.just(5, 10)
                .flatMap(x -> Flux.interval(Duration.ofMillis(x * 10), Duration.ofMillis(100)).take(x))
                .toStream()
                .forEach(System.out::println);
        Flux.just(5, 10)
                .concatMap(x -> Flux.interval(Duration.ofMillis(x * 10), Duration.ofMillis(100)).take(x))
                .toStream()
                .forEach(System.out::println);*/
    /*    Flux.combineLatest(
                Arrays::toString,
                Flux.interval(Duration.ofMillis(50)).take(5),
                Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(5)
        ).toStream().forEach(System.out::println);

        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .subscribe(System.out::println, System.err::println);
        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .onErrorReturn(0)
                .subscribe(System.out::println);

        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .onErrorResume(e -> {
                    if(e instanceof IllegalStateException )
                     return Mono.just(0);
                    return Mono.empty();
                }).subscribe(System.out::println);

        Flux.just(1, 2)
                .concatWith(Mono.error(new IllegalStateException()))
                .retry(2)
                .subscribe(System.out::println);*/

      /*  Flux.create(sink -> {
            sink.next(jdkUse.Thread.currentThread().getName());
            sink.complete();
        })
                .publishOn(Schedulers.single())
                .map(x -> String.format("[%s] %s", jdkUse.Thread.currentThread().getName(), x))
                .publishOn(Schedulers.elastic())
                .map(x -> String.format("[%s] %s", jdkUse.Thread.currentThread().getName(), x))
                .subscribeOn(Schedulers.parallel())
                .toStream()
                .forEach(System.out::println);

        SimpleBaseSubscriber<Integer> ss = new SimpleBaseSubscriber<>();
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(System.out::println,
                error -> System.err.println("Error " + error),
                () -> {System.out.println("Done");},
                s -> ss.request(4));
        ints.subscribe(ss);

        Flux.range(1, 10000)
                .publishOn(Schedulers.single())
                .map(x -> String.format("[%s] %s", jdkUse.Thread.currentThread().getName(), x))
                .toStream().forEach(System.out::println);*/

        Flux.range(1, 10)
                .parallel(2)
                .runOn(Schedulers.parallel())
                .subscribe(i -> System.out.println(Thread.currentThread().getName() + " -> " + i));
    }
}
