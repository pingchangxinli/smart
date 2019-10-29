import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

import static org.assertj.core.api.Assertions.assertThat;

public class BCryptPasswordEncoderTests {

    @Test
    // gh-5548
    public void emptyRawPasswordDoesNotMatchPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode("password");
        assertThat(encoder.matches("", result)).isFalse();
    }

    @Test
    public void $2yMatches() {
        // $2y is default version
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = encoder.encode("password");
        assertThat(result.equals("password")).isFalse();
        assertThat(encoder.matches("password", result)).isTrue();
    }

}