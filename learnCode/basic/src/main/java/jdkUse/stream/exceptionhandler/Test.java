package jdkUse.stream.exceptionhandler;

import com.google.common.collect.Lists;

import java.util.function.Function;

public class Test {
    public static void main(String[] args) {
        Lists.newArrayList(0, 1, 2, 3, 4, 5, 6)
                .stream().map(lift(Test::dosomeThing)).forEach(System.out::println);
    }

    private static Integer dosomeThing(Integer num) {
        return num / 0;
    }

    public static <T, R> Function<T, Either> lift(CheckedFunction<T, R> function) {
        return t -> {
            try {
                return Either.Right(function.apply(t));
            } catch (Exception ex) {
                return Either.Left(ex);
            }
        };
    }

    @FunctionalInterface
    public interface CheckedFunction<T, R> {
        R apply(T t) throws Exception;
    }
}
