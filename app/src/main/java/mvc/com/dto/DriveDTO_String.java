package mvc.com.dto;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by zloty on 2018-01-09.
 */

public class DriveDTO_String implements Parcelable {

    private String startDate;

    private String returnDate;

    private String cityStart;

    private String streetStart;

    private String exactPlaceStart;

    private String cityArrival;

    private String streetArrival;

    private String exactPlaceArrival;

    private String isRoundTrip;

    private String cost;

    private String luggageSize;

    private String passengersQuantity;

    private String isSmokePermitted;

    private String driverComment;

    private String stopOverPlaces;




    public DriveDTO_String(){

    }


    public DriveDTO_String(String startDate, String returnDate, String cityStart, String streetStart, String exactPlaceStart,
                           String cityArrival, String streetArrival, String exactPlaceArrival, String isRoundTrip, String cost, String luggageSize,
                           String passengersQuantity, String isSmokePermitted, String driverComment, String stopOverPlaces) {
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.cityStart = cityStart;
        this.streetStart = streetStart;
        this.exactPlaceStart = exactPlaceStart;
        this.cityArrival = cityArrival;
        this.streetArrival = streetArrival;
        this.exactPlaceArrival = exactPlaceArrival;
        this.isRoundTrip = isRoundTrip;
        this.cost = cost;
        this.luggageSize = luggageSize;
        this.passengersQuantity = passengersQuantity;
        this.isSmokePermitted = isSmokePermitted;
        this.driverComment = driverComment;
        this.stopOverPlaces = stopOverPlaces;
    }






    public DriveDTO_String(Parcel parcel){
        startDate = parcel.readString();
        returnDate = parcel.readString();
        cityStart = parcel.readString();
        streetStart = parcel.readString();
        exactPlaceStart = parcel.readString();
        cityArrival = parcel.readString();
        streetArrival = parcel.readString();
        exactPlaceArrival = parcel.readString();
        isRoundTrip = parcel.readString();
        cost = parcel.readString();
        luggageSize = parcel.readString();
        passengersQuantity = parcel.readString();
        isSmokePermitted = parcel.readString();
        driverComment = parcel.readString();
        stopOverPlaces = parcel.readString();
    }




    public static final Creator<DriveDTO_String> CREATOR = new Creator<DriveDTO_String>() {
        @Override
        public DriveDTO_String createFromParcel(Parcel in) {
            return new DriveDTO_String(in);
        }

        @Override
        public DriveDTO_String[] newArray(int size) {
            return new DriveDTO_String[0];
        }
    };





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

    public String getExactPlaceArrival() {
        return exactPlaceArrival;
    }

    public void setExactPlaceArrival(String exactPlaceArrival) {
        this.exactPlaceArrival = exactPlaceArrival;
    }

    public String getIsRoundTrip() {
        return isRoundTrip;
    }

    public void setIsRoundTrip(String isRoundTrip) {
        this.isRoundTrip = isRoundTrip;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getLuggageSize() {
        return luggageSize;
    }

    public void setLuggageSize(String luggageSize) {
        this.luggageSize = luggageSize;
    }

    public String getPassengersQuantity() {
        return passengersQuantity;
    }

    public void setPassengersQuantity(String passengersQuantity) {
        this.passengersQuantity = passengersQuantity;
    }

    public String getIsSmokePermitted() {
        return isSmokePermitted;
    }

    public void setIsSmokePermitted(String isSmokePermitted) {
        this.isSmokePermitted = isSmokePermitted;
    }

    public String getDriverComment() {
        return driverComment;
    }

    public void setDriverComment(String driverComment) {
        this.driverComment = driverComment;
    }

    public String getStopOverPlaces() {
        return stopOverPlaces;
    }

    public void setStopOverPlaces(String stopOverPlaces) {
        this.stopOverPlaces = stopOverPlaces;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startDate);
        dest.writeString(returnDate);
        dest.writeString(cityStart);
        dest.writeString(streetStart);
        dest.writeString(exactPlaceStart);
        dest.writeString(cityArrival);
        dest.writeString(streetArrival);
        dest.writeString(exactPlaceArrival);
        dest.writeString(isRoundTrip);
        dest.writeString(cost);
        dest.writeString(luggageSize);
        dest.writeString(passengersQuantity);
        dest.writeString(isSmokePermitted);
        dest.writeString(driverComment);
        dest.writeString(stopOverPlaces);
    }



}
