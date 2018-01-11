package mvc.com.model;


import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class DriveModel implements Serializable {

    private long driveId;

    private Calendar startDate;

    private Calendar returnDate;

    private Calendar insertDate;

    private Calendar modificationDate;

    private String searchData;

    private String cityStart;

    private String streetStart;

    private String exactPlaceStart;

    private String cityArrival;

    private String streetArrival;

    private String exactPlaceArrival;

    private boolean isFreeWay;

    private boolean isRoundTrip;

    private int cost;

    private boolean isDeleted;

    // Polaczenie 1 User do N Przejazdow
    private UserModel insertUser;

    // Polaczenie 1 Przejazd do N Rezerwacji
    private List<BookingModel> bookings;

    // Polaczenie 1 Przejazd do N Miejsc posrednich
    private List<StopOverPlaceModel> stopOverPlaces;



    public DriveModel() {
    }

    public DriveModel(long driveId, Calendar startDate, Calendar returnDate, Calendar insertDate, Calendar modificationDate, String searchData,
                      String cityStart, String streetStart, String exactPlaceStart, String cityArrival, String streetArrival, String exactPlaceArrival,
                      boolean isFreeWay, boolean isRoundTrip, int cost, boolean isDeleted, UserModel insertUser, List<BookingModel> bookings,
                      List<StopOverPlaceModel> stopOverPlaces) {
        this.driveId = driveId;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.insertDate = insertDate;
        this.modificationDate = modificationDate;
        this.searchData = searchData;
        this.cityStart = cityStart;
        this.streetStart = streetStart;
        this.exactPlaceStart = exactPlaceStart;
        this.cityArrival = cityArrival;
        this.streetArrival = streetArrival;
        this.exactPlaceArrival = exactPlaceArrival;
        this.isFreeWay = isFreeWay;
        this.isRoundTrip = isRoundTrip;
        this.cost = cost;
        this.isDeleted = isDeleted;
        this.insertUser = insertUser;
        this.bookings = bookings;
        this.stopOverPlaces = stopOverPlaces;
    }



    public long getDriveId() {
        return driveId;
    }

    public void setDriveId(long driveId) {
        this.driveId = driveId;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Calendar returnDate) {
        this.returnDate = returnDate;
    }

    public Calendar getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Calendar insertDate) {
        this.insertDate = insertDate;
    }

    public Calendar getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Calendar modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getSearchData() {
        return searchData;
    }

    public void setSearchData(String searchData) {
        this.searchData = searchData;
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

//    public String getBusStopStart() {
//        return busStopStart;
//    }
//
//    public void setBusStopStart(String busStopStart) {
//        this.busStopStart = busStopStart;
//    }


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

    public boolean isFreeWay() {
        return isFreeWay;
    }

    public void setFreeWay(boolean freeWay) {
        isFreeWay = freeWay;
    }

    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public void setRoundTrip(boolean roundTrip) {
        isRoundTrip = roundTrip;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public UserModel getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(UserModel insertUser) {
        this.insertUser = insertUser;
    }

    public List<BookingModel> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingModel> bookings) {
        this.bookings = bookings;
    }

    public List<StopOverPlaceModel> getStopOverPlaces() {
        return stopOverPlaces;
    }

    public void setStopOverPlaces(List<StopOverPlaceModel> stopOverPlaces) {
        this.stopOverPlaces = stopOverPlaces;
    }

    @Override
    public String toString() {
        return "DriveModel{" +
                "driveId=" + driveId +
                ", startDate=" + (startDate != null ? startDate.getTime() : "" ) +
                ", returnDate=" + (returnDate != null ? returnDate.getTime() : "" ) +
                ", insertDate=" + (insertDate != null ? insertDate.getTime() : "" ) +
                ", modificationDate=" + (modificationDate != null ? modificationDate.getTime() : "" ) +
                ", searchData='" + searchData + '\'' +
                ", cityStart='" + cityStart + '\'' +
                ", streetStart='" + streetStart + '\'' +
                ", exactPlaceStart='" + exactPlaceStart + '\'' +
                ", cityArrival='" + cityArrival + '\'' +
                ", streetArrival='" + streetArrival + '\'' +
                ", exactPlaceArrival='" + exactPlaceArrival + '\'' +
                ", isFreeWay=" + isFreeWay +
                ", isRoundTrip=" + isRoundTrip +
                ", cost=" + cost +
                ", isDeleted=" + isDeleted +
                ", insertUser=" + insertUser +
//                ", bookings=" + bookings +
//                ", stopOverPlaces=" + stopOverPlaces +
                '}';
    }
}
