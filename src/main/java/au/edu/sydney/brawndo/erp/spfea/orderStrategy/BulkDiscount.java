package au.edu.sydney.brawndo.erp.spfea.orderStrategy;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
//import au.edu.sydney.brawndo.erp.spfea.ordering.BusinessBulkDiscountOrder;
import au.edu.sydney.brawndo.erp.spfea.ordering.OrderImpl;

import java.util.Map;

public class BulkDiscount implements DiscountStrategy {
    private int discountThreshold;
    private double discountRate;

    public BulkDiscount(int discountThreshold, double discountRate){
        this.discountThreshold = discountThreshold;
        this.discountRate = discountRate;
    }

    @Override
    public double getDiscountRate() {
        return discountRate;
    }

    @Override
    public int getDiscountThreshold() {
        return discountThreshold;
    }

    @Override
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public void setDiscountThreshold(int discountThreshold) {
        this.discountThreshold = discountThreshold;
    }

    @Override
    public double discountCalculate(Map<Product, Integer> products) {
        double cost = 0.0;
        for (Product product: products.keySet()) {
            int count = products.get(product);
            if (count >= this.discountThreshold) {
                cost +=  count * product.getCost() * this.discountRate;
            } else {
                cost +=  count * product.getCost();
            }
        }
        return cost;
    }
}
