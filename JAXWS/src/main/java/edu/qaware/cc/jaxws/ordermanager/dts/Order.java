package edu.qaware.cc.jaxws.ordermanager.dts;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order", namespace = "http://qaware.edu/cc/jaxws")
public class Order {
    @XmlElement
    private String item;
    @XmlElement
    private int amount;
    @XmlElement
    private String customer;

    public Order(){}

    public Order(
            String item,
            int amount,
            String customer) {
        this.item = item;
        this.amount = amount;
        this.customer = customer;
    }

    public String getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public String getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "item='" + item + '\'' +
                ", amount='" + amount + '\'' +
                ", customer='" + customer + '\'' +
                '}';
    }
}
