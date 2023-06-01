package au.edu.sydney.brawndo.erp.spfea.products;

import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.Arrays;
import java.util.Objects;

public class ProductImpl implements Product {
    public static ProductFactory productDataFactory = new ProductFactory();
    private static int incrementId = 0;
    private final int productId;

    private final String name;
    private final double[] manufacturingData;
    private final double cost;
    private final double[] recipeData;
    private final double[] marketingData;
    private final double[] safetyData;
    private final double[] licensingData;

    public ProductImpl(String name,
                       double cost,
                       double[] manufacturingData,
                       double[] recipeData,
                       double[] marketingData,
                       double[] safetyData,
                       double[] licensingData) {
        this.name = name;
        this.cost = cost;
        this.manufacturingData = productDataFactory.getProductData(manufacturingData);
        this.recipeData = productDataFactory.getProductData(recipeData);
        this.marketingData = productDataFactory.getProductData(marketingData);
        this.safetyData = productDataFactory.getProductData(safetyData);
        this.licensingData = productDataFactory.getProductData(licensingData);
        this.productId = incrementId + 1;
    }

    @Override
    public String getProductName() {
        return name;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public double[] getManufacturingData() {
        return manufacturingData;
    }

    @Override
    public double[] getRecipeData() {
        return recipeData;
    }

    @Override
    public double[] getMarketingData() {
        return marketingData;
    }

    @Override
    public double[] getSafetyData() {
        return safetyData;
    }

    @Override
    public double[] getLicensingData() {
        return licensingData;
    }

    public static int getIncrementId() {
        return incrementId;
    }

    @Override
    public String toString() {

        return String.format("%s", name);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this){
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()){
            return false;
        }

        ProductImpl comparisonProduct = (ProductImpl) object;
        return (comparisonProduct.getCost() == this.getCost() &&
                comparisonProduct.getProductName().equals(this.getProductName()) &&
                Arrays.equals(comparisonProduct.getManufacturingData(), this.getManufacturingData()) &&
                Arrays.equals(comparisonProduct.getRecipeData(), this.getRecipeData()) &&
                Arrays.equals(comparisonProduct.getMarketingData(), this.getMarketingData()) &&
                Arrays.equals(comparisonProduct.getSafetyData(), this.getSafetyData()) &&
                Arrays.equals(comparisonProduct.getLicensingData(), this.getLicensingData()));
    }

    @Override
    public int hashCode() {
        int hashResult = Objects.hash(name);
        hashResult = 31 * hashResult + Objects.hash(cost);
        hashResult = 31 * hashResult + Arrays.hashCode(manufacturingData);
        hashResult = 31 * hashResult + Arrays.hashCode(recipeData);
        hashResult = 31 * hashResult + Arrays.hashCode(marketingData);
        hashResult = 31 * hashResult + Arrays.hashCode(safetyData);
        hashResult = 31 * hashResult + Arrays.hashCode(licensingData);
        return hashResult;
    }
}
