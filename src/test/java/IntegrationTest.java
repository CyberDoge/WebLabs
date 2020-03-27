import com.web.app.controller.MainController;
import org.junit.Test;

public class IntegrationTest {
    @Test
    public void test() {
        MainController mainController = new MainController("auto");
        mainController.start();
        mainController.close();
    }
}
