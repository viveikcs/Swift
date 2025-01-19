package com.example.swiftdelivery.agent;

public class AgentAvailableDelivery {
    private String deliveryID;
    private String UserName;
    private String UserMobile;
    private String PickupAddress;
    private String DeliveryAddress;
    private String PickupPhone;
    private String DeliveryPhone;
    private String PackageDetails;
    private String Status;
    private String AssignedAgent;

    public AgentAvailableDelivery() {}

    public String getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(String deliveryID) {
        this.deliveryID = deliveryID;
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

    public String getStatus() {
        return Status;
    }

    public String getAssignedAgent() {
        return AssignedAgent;
    }

    public String getPickupPhone() {
        return PickupPhone;
    }

    public void setPickupPhone(String pickupPhone) {
        PickupPhone = pickupPhone;
    }

    public String getDeliveryPhone() {
        return DeliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        DeliveryPhone = deliveryPhone;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserMobile() {
        return UserMobile;
    }

    public void setUserMobile(String userMobile) {
        UserMobile = userMobile;
    }

    public String getPackageDetails() {
        return PackageDetails;
    }

    public void setPackageDetails(String packageDetails) {
        PackageDetails = packageDetails;
    }

    public AgentAvailableDelivery(String deliveryID, String userName, String userMobile, String pickupAddress, String deliveryAddress, String pickupPhone, String deliveryPhone, String packageDetails, String status, String assignedAgent)
    {
        this.deliveryID = deliveryID;
        UserName = userName;
        UserMobile = userMobile;
        PickupAddress = pickupAddress;
        DeliveryAddress = deliveryAddress;
        PickupPhone = pickupPhone;
        DeliveryPhone = deliveryPhone;
        PackageDetails = packageDetails;
        Status = status;
        AssignedAgent = assignedAgent;
    }

    @Override
    public String toString() {
        return "Pickup From: "+PickupAddress+"\nDeliver To: "+DeliveryAddress+"\nPickup Contact: "+PickupPhone+"\nDelivery Contact: "+DeliveryPhone ;
    }
}
