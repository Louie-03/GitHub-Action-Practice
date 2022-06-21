package louie.dong.airbnb;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class AirbnbApplicationTests {

    @Test
    void contextLoads() throws IOException {
        File file = new File("abc");
        System.out.println(file.createNewFile());
    }

}
