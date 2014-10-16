package edu.qaware.cc.jaxws.ordermanager;

import edu.qaware.cc.jaxws.ordermanager.dts.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebService;

@WebService(name = "OrderManager")
public class OrderManagerImpl implements OrderManager {

    private static final Logger LOG = LoggerFactory.getLogger(OrderManager.class);

    @Override
    public void placeOrder(Order order) {
        LOG.info("{} pieces of {} for {}", new Object[]{order.getAmount(), order.getItem(), order.getCustomer()});
    }

}
