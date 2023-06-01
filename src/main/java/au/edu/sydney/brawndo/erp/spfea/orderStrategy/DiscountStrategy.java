package au.edu.sydney.brawndo.erp.spfea.orderStrategy;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.Map;

public interface DiscountStrategy {
    int getDiscountThreshold();
    double getDiscountRate();

    void setDiscountRate(double discountRate);

    void setDiscountThreshold(int discountThreshold);

    double discountCalculate(Map<Product, Integer> products);
}
