package au.edu.sydney.brawndo.erp.spfea.products;

import java.util.*;

public class ProductFactory {
    private static Map<String, double[]> productDataMap = new HashMap<>();

    public double[] getProductData(double[] productData)  {
        if (productData == null){
            return null;
        }

        List<Double> dataList = new ArrayList<>();
        for (double data : productData) {
            dataList.add(data);
        }

        Collections.sort(dataList);
        String dataHashValue = String.valueOf(dataList.hashCode());

        if (!productDataMap.containsKey(dataHashValue)) {
            productDataMap.put(dataHashValue, productData);
            return productData;
        } else {
            return productDataMap.get(dataHashValue);
        }
    }

}
