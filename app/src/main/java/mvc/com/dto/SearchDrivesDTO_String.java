package mvc.com.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchDrivesDTO_String implements Parcelable {

    private String startDate;

    private String returnDate;

    private String startPlace;

    private String arrivalPlace;

    private String isRoundTrip;

    private String maxCost;

//    private String luggageSize;


    public SearchDrivesDTO_String() {
    }

    public SearchDrivesDTO_String(String startDate, String returnDate, String startPlace, String arrivalPlace, String isRoundTrip, String maxCost) {
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.startPlace = startPlace;
        this.arrivalPlace = arrivalPlace;
        this.isRoundTrip = isRoundTrip;
        this.maxCost = maxCost;
    }


    public SearchDrivesDTO_String(Parcel parcel){
        startDate = parcel.readString();
        returnDate = parcel.readString();
        startPlace = parcel.readString();
        arrivalPlace = parcel.readString();
        isRoundTrip = parcel.readString();
        maxCost = parcel.readString();
    }





    public static final Creator<SearchDrivesDTO_String> CREATOR = new Creator<SearchDrivesDTO_String>() {
        @Override
        public SearchDrivesDTO_String createFromParcel(Parcel in) {
            return new SearchDrivesDTO_String(in);
        }

        @Override
        public SearchDrivesDTO_String[] newArray(int size) {
            return new SearchDrivesDTO_String[0];
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

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getArrivalPlace() {
        return arrivalPlace;
    }

    public void setArrivalPlace(String arrivalPlace) {
        this.arrivalPlace = arrivalPlace;
    }

    public String getIsRoundTrip() {
        return isRoundTrip;
    }

    public void setIsRoundTrip(String isRoundTrip) {
        this.isRoundTrip = isRoundTrip;
    }

    public String getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(String maxCost) {
        this.maxCost = maxCost;
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startDate);
        dest.writeString(returnDate);
        dest.writeString(startPlace);
        dest.writeString(arrivalPlace);
        dest.writeString(isRoundTrip);
        dest.writeString(maxCost);
    }

}
