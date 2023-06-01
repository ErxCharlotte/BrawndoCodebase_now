package au.edu.sydney.brawndo.erp.spfea.orderStrategy;

import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.Map;

public class FlatDiscount implements DiscountStrategy {
    private double discountRate;
    private final int discountThreshold = -1;

    public FlatDiscount(double discountRate){
        this.discountRate = discountRate;
    }

    @Override
    public double getDiscountRate() {
        return discountRate;
    }

    @Override
    public int getDiscountThreshold() {
        return this.discountThreshold;
    }

    @Override
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public void setDiscountThreshold(int discountThreshold) {
    }


    @Override
    public double discountCalculate(Map<Product, Integer> products) {
        double cost = 0.0;
        for (Product product: products.keySet()) {
            cost +=  products.get(product) * product.getCost() * this.discountRate;
        }
        return cost;
    }
}
