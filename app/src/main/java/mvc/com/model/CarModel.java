package mvc.com.model;

import java.io.Serializable;


public class CarModel implements Serializable {
    private long carId;

    private String carBrand;

    private String carModel;

    private String color;

    // Polaczenie 1 User do N Samochodow
    private UserModel user;





    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CarModel{" +
                "carId=" + carId +
                ", carBrand='" + carBrand + '\'' +
                ", carModel='" + carModel + '\'' +
                ", color='" + color + '\'' +
                ", user=" + user +
                '}';
    }
}
