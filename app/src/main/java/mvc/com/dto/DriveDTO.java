package mvc.com.dto;

import java.util.List;

import mvc.com.enums.LuggageSize;
import mvc.com.model.StopOverPlaceModel;

public class DriveDTO {

    private String startDate;

    private String returnDate;

    private String cityStart;

    private String streetStart;

    private String exactPlaceStart;

    private String cityArrival;

    private String streetArrival;

    private String exactPlaceArrival;

    private String isRoundTrip;

    private int cost;

    private LuggageSize luggageSize;

    private int passengersQuantity;

    private String isSmokePermitted;

    private String driverComment;

    private List<StopOverPlaceModel> stopOverPlaces;




    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getCityStart() {
        return cityStart;
    }

    public void setCityStart(String cityStart) {
        this.cityStart = cityStart;
    }

    public String getStreetStart() {
        return streetStart;
    }

    public void setStreetStart(String streetStart) {
        this.streetStart = streetStart;
    }

    public String getExactPlaceStart() {
        return exactPlaceStart;
    }

    public void setExactPlaceStart(String exactPlaceStart) {
        this.exactPlaceStart = exactPlaceStart;
    }

    public String getExactPlaceArrival() {
        return exactPlaceArrival;
    }

    public void setExactPlaceArrival(String exactPlaceArrival) {
        this.exactPlaceArrival = exactPlaceArrival;
    }

    public String getCityArrival() {
        return cityArrival;
    }

    public void setCityArrival(String cityArrival) {
        this.cityArrival = cityArrival;
    }

    public String getStreetArrival() {
        return streetArrival;
    }

    public void setStreetArrival(String streetArrival) {
        this.streetArrival = streetArrival;
    }

    public String getIsRoundTrip() {
        return isRoundTrip;
    }

    public void setIsRoundTrip(String isRoundTrip) {
        this.isRoundTrip = isRoundTrip;
    }

    public String getIsSmokePermitted() {
        return isSmokePermitted;
    }

    public void setIsSmokePermitted(String isSmokePermitted) {
        this.isSmokePermitted = isSmokePermitted;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public LuggageSize getLuggageSize() {
        return luggageSize;
    }

    public void setLuggageSize(LuggageSize luggageSize) {
        this.luggageSize = luggageSize;
    }

    public int getPassengersQuantity() {
        return passengersQuantity;
    }

    public void setPassengersQuantity(int passengersQuantity) {
        this.passengersQuantity = passengersQuantity;
    }

    public String getDriverComment() {
        return driverComment;
    }

    public void setDriverComment(String driverComment) {
        this.driverComment = driverComment;
    }

    public List<StopOverPlaceModel> getStopOverPlaces() {
        return stopOverPlaces;
    }

    public void setStopOverPlaces(List<StopOverPlaceModel> stopOverPlaces) {
        this.stopOverPlaces = stopOverPlaces;
    }

    @Override
    public String toString() {
        return "DriveDTO{" +
                "startDate='" + startDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", cityStart='" + cityStart + '\'' +
                ", streetStart='" + streetStart + '\'' +
                ", exactPlaceStart='" + exactPlaceStart + '\'' +
                ", cityArrival='" + cityArrival + '\'' +
                ", streetArrival='" + streetArrival + '\'' +
                ", exactPlaceArrival='" + exactPlaceArrival + '\'' +
                ", isRoundTrip='" + isRoundTrip + '\'' +
                ", cost=" + cost +
                ", luggageSize=" + luggageSize +
                ", passengersQuantity=" + passengersQuantity +
                ", isSmokePermitted='" + isSmokePermitted + '\'' +
                ", driverComment='" + driverComment + '\'' +
                ", stopOverPlaces=" + stopOverPlaces +
                '}';
    }
}
