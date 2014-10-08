package edu.qaware.cc.amqp.dts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
    private String item;
    private int amount;
    private String customer;

    @JsonCreator
    public Order(
            @JsonProperty("item")
            String item,
            @JsonProperty("amount")
            int amount,
            @JsonProperty("customer")
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
