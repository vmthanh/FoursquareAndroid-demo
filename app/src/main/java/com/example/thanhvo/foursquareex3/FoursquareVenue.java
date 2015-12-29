package com.example.thanhvo.foursquareex3;

/**
 * Created by ThanhVo on 12/28/2015.
 */
public class FoursquareVenue {
    private String id;
    private String name;
    private String address;
    private String crossStreet;
    private String lat;
    private String lng;
    private String distance;
    private String category;
    private String city;
    public FoursquareVenue(){
        this.id = "";
        this.name = "";
        this.address = "";
        this.crossStreet = "";
        this.lat = "";
        this.lng = "";
        this.distance = "";
        this.category = "";
    }

    public void setId(String id){
        this.id = id;

    }
    public String getId()
    {
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return this.address;
    }
    public void setCrossStreet(String crossStreet){
        this.crossStreet = crossStreet;
    }
    public String getCrossStreet(){
        return this.crossStreet;
    }
    public void setLat(String lat){
        this.lat = lat;
    }
    public String getLat(){
        return this.lat;
    }
    public void setLng(String lng){
        this.lng = lng;
    }
    public String getLng(){
        return this.lng;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }
    public void setCategory(String category)
    {
        this.category = category;
    }
    public String getCategory()
    {
        return this.category;
    }
    public void setDistance(String distance)
    {
        this.distance = distance;
    }
    public String getDistance()
    {
        return this.distance;
    }
}
