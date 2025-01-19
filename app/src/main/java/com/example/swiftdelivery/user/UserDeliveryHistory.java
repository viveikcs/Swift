package com.example.swiftdelivery.user;

public class UserDeliveryHistory {

    private String deliveryID;
    private String UserID;
    private String AssignedAgentName;
    private String AssignedAgentMobile;
    private String PickupAddress;
    private String DeliveryAddress;
    private String PackageDetails;
    private String Status;

    public UserDeliveryHistory() {}

    public String getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(String deliveryID) {
        this.deliveryID = deliveryID;
    }

    public String getUserID() {
        return UserID;
    }

    public String getAssignedAgentName() {
        return AssignedAgentName;
    }

    public void setAssignedAgentName(String assignedAgentName) {
        AssignedAgentName = assignedAgentName;
    }

    public String getAssignedAgentMobile() {
        return AssignedAgentMobile;
    }

    public void setAssignedAgentMobile(String assignedAgentMobile) {
        AssignedAgentMobile = assignedAgentMobile;
    }

    public String getPickupAddress() {
        return PickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        PickupAddress = pickupAddress;
    }

    public String getDeliveryAddress() {
        return DeliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        DeliveryAddress = deliveryAddress;
    }

    public String getPackageDetails() {
        return PackageDetails;
    }

    public void setPackageDetails(String packageDetails) {
        PackageDetails = packageDetails;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public UserDeliveryHistory(String deliveryID, String userID, String assignedAgentName, String assignedAgentMobile, String pickupAddress, String deliveryAddress, String packageDetails, String status)
    {
        this.deliveryID = deliveryID;
        UserID = userID;
        AssignedAgentName = assignedAgentName;
        AssignedAgentMobile = assignedAgentMobile;
        PickupAddress = pickupAddress;
        DeliveryAddress = deliveryAddress;
        PackageDetails = packageDetails;
        Status = status;
    }

}
