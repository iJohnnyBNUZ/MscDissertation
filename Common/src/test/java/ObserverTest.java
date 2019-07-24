import Utils.example.Camera;
import Utils.example.Guard;
import Utils.example.Shop;
import org.junit.Test;

public class ObserverTest {

    @Test
    public void observerTest(){
        Shop shop = new Shop();

        //添加摄像头1号
        Camera camera = new Camera(1);
        shop.addObserver(camera);

        //添加门卫一号
        Guard guard1 = new Guard("guard1");
        shop.addObserver(guard1);

        //来访登记、出门登记
        shop.comeIn("BlogJava");
        shop.comeIn("JavaEye");
        shop.getOut("BlogJava");

        //移除门卫一号，派他去做别的事
        shop.removeObserver(guard1);

        //进入无人观察状态，随进随出
        shop.comeIn("CSDN");
        shop.getOut("JavaEye");

        //添加门卫二号
        Guard guard2 = new Guard("guard2");
        shop.addObserver(guard2);

        //来访登记，出门登记
        shop.comeIn("xxx");
        shop.getOut("CSDN");

    }
}
