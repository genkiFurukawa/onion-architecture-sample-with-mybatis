package ddd.playground.sample.domain.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class UserMailAddressTest {
    @ParameterizedTest(name = "メールアドレスが「{0}」の時、インスタンスが生成できること")
    @MethodSource("コンストラクタに渡す引数と期待値")
    void メールアドレスのフォーマットが正しいときにインスタンスが生成できること(String arg, String expected) {
        UserMailAddress mailAddress = new UserMailAddress(arg);
        assertEquals(expected, mailAddress.value());
    }

    static Stream<Arguments> コンストラクタに渡す引数と期待値() {
        return Stream.of(
                arguments("test@example.com", "test@example.com"),
                arguments("first.last@example.com", "first.last@example.com"),
                arguments("user+test123@example.com", "user+test123@example.com")
        );
    }

    @ParameterizedTest(name = "メールアドレスが「{0}」の時、IllegalArgumentExceptionがスローされること")
    @ValueSource(strings = {"test@", "test.@example.com", "john-@hotmail.com", "test@example"})
    void メールアドレスのフォーマットが不正なときにIllegalArgumentExceptionをスローすること(String arg) {
        var e = assertThrows(IllegalArgumentException.class, () -> {
            new UserMailAddress(arg);
        });
    }
}