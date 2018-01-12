package mvc.com.model;


import java.io.Serializable;
import java.util.List;

import mvc.com.enums.LuggageSize;
import mvc.com.enums.Male;

public class DriveDetailsModel implements Serializable {

    private long detailId;

    private int timeFlexibility;

    private int maxBypassTime;

    private LuggageSize luggageSize;

    private int passengersQuantity;

    private boolean isSmokePermitted;

    private boolean isAnimalPermitted;

    private Male prefferedMale;

    private String driverComment;

    private List<String> passengersComments;

    private DriveModel drive;





    public long getDetailId() {
        return detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    public int getTimeFlexibility() {
        return timeFlexibility;
    }

    public void setTimeFlexibility(int timeFlexibility) {
        this.timeFlexibility = timeFlexibility;
    }

    public int getMaxBypassTime() {
        return maxBypassTime;
    }

    public void setMaxBypassTime(int maxBypassTime) {
        this.maxBypassTime = maxBypassTime;
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

    public boolean isSmokePermitted() {
        return isSmokePermitted;
    }

    public void setSmokePermitted(boolean smokePermitted) {
        isSmokePermitted = smokePermitted;
    }

    public boolean isAnimalPermitted() {
        return isAnimalPermitted;
    }

    public void setAnimalPermitted(boolean animalPermitted) {
        isAnimalPermitted = animalPermitted;
    }

    public Male getPrefferedMale() {
        return prefferedMale;
    }

    public void setPrefferedMale(Male prefferedMale) {
        this.prefferedMale = prefferedMale;
    }

    public String getDriverComment() {
        return driverComment;
    }

    public void setDriverComment(String driverComment) {
        this.driverComment = driverComment;
    }

    public List<String> getPassengersComments() {
        return passengersComments;
    }

    public void setPassengersComments(List<String> passengersComments) {
        this.passengersComments = passengersComments;
    }

    public DriveModel getDrive() {
        return drive;
    }

    public void setDrive(DriveModel drive) {
        this.drive = drive;
    }

    @Override
    public String toString() {
        return "DriveDetailsModel{" +
                "detailId=" + detailId +
                ", timeFlexibility=" + timeFlexibility +
                ", maxBypassTime=" + maxBypassTime +
                ", luggageSize=" + luggageSize +
                ", passengersQuantity=" + passengersQuantity +
                ", isSmokePermitted=" + isSmokePermitted +
                ", isAnimalPermitted=" + isAnimalPermitted +
                ", prefferedMale=" + prefferedMale +
                ", driverComment='" + driverComment + '\'' +
//                ", passengersComments=" + passengersComments +
//                ", drive=" + drive +
                '}';
    }
}
