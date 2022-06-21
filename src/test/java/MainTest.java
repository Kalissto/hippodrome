import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {
   // @Disabled("отключен")
    @Test
    @Timeout(value =21)
    void main() throws Exception {
       Main.main(null);
    }
}
