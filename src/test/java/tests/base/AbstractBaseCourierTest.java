package tests.base;

import api.client.CourierClient;
import api.models.courier.Courier;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractBaseCourierTest extends BaseTest {
    protected Courier courier;
    protected CourierClient courierClient;

    @Override
    public void setUp() {
        super.setUp();
        courier = new Courier();
        courierClient = new CourierClient();
    }

    @After
    public void tearDown(){
        courier = null;
        courierClient = null;
    }

}
