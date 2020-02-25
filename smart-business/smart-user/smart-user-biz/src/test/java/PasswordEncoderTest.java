import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {
    @Test
    public void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String after = passwordEncoder.encode("client");
        System.out.println(after);
    }
}
