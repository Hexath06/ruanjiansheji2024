package Entity;

import java.math.BigDecimal;

public class RetrievedProduct {
    private String image;
    private String productName;
    private BigDecimal productPrice;

    public RetrievedProduct(String image, String productName, BigDecimal productPrice) {
        this.image = image;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = BigDecimal.valueOf(productPrice);
    }
}

