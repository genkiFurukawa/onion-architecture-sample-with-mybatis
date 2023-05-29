package ddd.playground.sample.domain.value;

import java.util.regex.Pattern;

public record UserMailAddress(String value) {
    public UserMailAddress {
        // NOTE: ChatGPTを使って生成した
        String emailRegex = "^[A-Za-z0-9]+([._%+-]?[A-Za-z0-9]+)*@[A-Za-z0-9]+([.-]?[A-Za-z0-9]+)*\\.[A-Za-z]{2,}$";
        boolean isValid = Pattern.matches(emailRegex, value);
        if (!isValid) {
            throw new IllegalArgumentException("invalid format mail address: " + value);
        }
    }
}
