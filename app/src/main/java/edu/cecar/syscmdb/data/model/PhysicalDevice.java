package edu.cecar.syscmdb.data.model;

import java.util.Map;

public class PhysicalDevice extends FunctionalCI {
    private String serialnumber;
    private String locationId;
    private String locationName;
    private String status;
    private String brandId;
    private String brandName;
    private String modelId;
    private String modelName;
    private String assetNumber;
    private String purchaseDate;
    private String endOfWarranty;
    private Map<String,String>additionalProperties;

    public PhysicalDevice(int key, String class_, String name, String desc, String org_name, int org_id) {
        super(key, class_, name, desc, org_name, org_id);
    }
    public void setProperty(String property, String value){
        additionalProperties.put(property,value);
    }
    public String getProperty(String key){
        return additionalProperties.get(key);
    }
    public Map<String, String> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, String> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getAssetNumber() {
        return assetNumber;
    }

    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getEndOfWarranty() {
        return endOfWarranty;
    }

    public void setEndOfWarranty(String endOfWarranty) {
        this.endOfWarranty = endOfWarranty;
    }
}
