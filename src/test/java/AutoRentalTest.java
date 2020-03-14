import com.web.app.model.Auto;
import com.web.app.model.AutoRental;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;

public class AutoRentalTest {

    @Test
    public void testIdExist() throws RemoteException {
        AutoRental autoRental = new AutoRental();
        Assert.assertNotNull(autoRental.getPointOfRental());
    }

    @Test
    public void testAddingAutos() throws RemoteException {
        AutoRental autoRental = new AutoRental();
        Assert.assertEquals(0, autoRental.getAutosLength());
        for (int i = 0; i < 20; i++) {
            autoRental.addAuto(new Auto("model" + i, "producer" + i, i, i * 1000));
        }
        Assert.assertEquals(20, autoRental.getAutosLength());
    }

    @Test
    public void testGetAutos() throws RemoteException {
        AutoRental autoRental = new AutoRental();
        autoRental.addAuto(new Auto("model", "producer", 9, 999.99f));
        Assert.assertEquals(999.99f, autoRental.getAutoByIndex(0).getPrice(), 0);
        Assert.assertEquals(9, autoRental.getAutoByIndex(0).getCount());
        Assert.assertEquals("model", autoRental.getAutoByIndex(0).getModel());
        Assert.assertEquals("producer", autoRental.getAutoByIndex(0).getProducer());
    }

    @Test
    public void testSetters() throws RemoteException {
        AutoRental autoRental = new AutoRental();
        autoRental.addAuto(new Auto("oldmodel", "oldproducer", 1, 0.99f));
        autoRental.setAutoModel(0, "model");
        autoRental.setAutoProducer(0, "producer");
        autoRental.setAutoCount(0, 9);
        autoRental.setAutoPrice(0, 999.99f);
        Assert.assertEquals(999.99f, autoRental.getAutoByIndex(0).getPrice(), 0);
        Assert.assertEquals(9, autoRental.getAutoByIndex(0).getCount());
        Assert.assertEquals("model", autoRental.getAutoByIndex(0).getModel());
        Assert.assertEquals("producer", autoRental.getAutoByIndex(0).getProducer());
    }


    @Test
    public void testRemoveAutos() throws RemoteException {
        AutoRental autoRental = new AutoRental();
        Assert.assertEquals(0, autoRental.getAutosLength());
        for (int i = 0; i < 20; i++) {
            autoRental.addAuto(new Auto("model" + i, "producer" + i, i, i * 1000));
        }
        autoRental.removeAutoByIndex(19);
        Assert.assertEquals(19, autoRental.getAutosLength());
    }

    @Test
    public void testSorting() throws RemoteException {
        AutoRental autoRental = new AutoRental();
        for (int i = 20; i > 0; i--) {
            autoRental.addAuto(new Auto("model" + i, "producer" + i, i, i * 1000));
        }
        autoRental.sort();
        Assert.assertEquals(autoRental.getAutoByIndex(0).getPrice(), 1000, 0);
    }
}
