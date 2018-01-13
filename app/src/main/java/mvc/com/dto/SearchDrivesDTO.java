package mvc.com.dto;


import mvc.com.enums.LuggageSize;

public class SearchDrivesDTO {

    private String startDate;

    private String returnDate;

    private String startPlace;

    private String arrivalPlace;

    private String isRoundTrip;

    private int maxCost;

    private LuggageSize luggageSize;




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

    public int getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }

    public LuggageSize getLuggageSize() {
        return luggageSize;
    }

    public void setLuggageSize(LuggageSize luggageSize) {
        this.luggageSize = luggageSize;
    }
}
