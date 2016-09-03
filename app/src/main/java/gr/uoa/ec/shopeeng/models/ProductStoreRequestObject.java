package gr.uoa.ec.shopeeng.models;

public class ProductStoreRequestObject {

    private String productName;
    private String userLocation;
    private String distance;
    private String duration;
    private String unit;
    private String orderBy;
    private String transportMode;
    private String userId;

    public ProductStoreRequestObject(String productName, String userLocation, String distance, String duration, String unit, String orderBy, String transportMode, String userId) {
        this.productName = productName;
        this.userLocation = userLocation;
        this.distance = distance;
        this.duration = duration;
        this.unit = unit;
        this.orderBy = orderBy;
        this.transportMode = transportMode;
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }
}
