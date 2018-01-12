package mvc.com.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import mvc.com.enums.Male;
import mvc.com.enums.Role;

public class UserModel implements Serializable {

    private long userId;

    private String login;

    private String password;

    private String mail;

    private String phone;

    private String name;

    private String lastname;

    private Male male;

    private Calendar birthDate;

    private String searchData;

    private Role role = Role.ROLE_USER;

    private List<Integer> userRates;

    private List<String> personalityAssessment;

    private List<Integer> drivingSkills;

    private Calendar modifyTime;

    private Calendar lastLoginTime;

    private boolean isDeleted;

    // Polaczenie 1 User do N Przejazdow
    private List<DriveModel> drives;

    // Polaczenie 1 User do N Samochodow
    private List<CarModel> cars;

    // Polaczenie 1 User do N Rezerwacji
    private List<BookingModel> bookings;




    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Male getMale() {
        return male;
    }

    public void setMale(Male male) {
        this.male = male;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public String getSearchData() {
        return searchData;
    }

    public void setSearchData(String searchData) {
        this.searchData = searchData;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Integer> getUserRates() {
        return userRates;
    }

    public void setUserRates(List<Integer> userRates) {
        this.userRates = userRates;
    }

    public List<String> getPersonalityAssessment() {
        return personalityAssessment;
    }

    public void setPersonalityAssessment(List<String> personalityAssessment) {
        this.personalityAssessment = personalityAssessment;
    }

    public List<Integer> getDrivingSkills() {
        return drivingSkills;
    }

    public void setDrivingSkills(List<Integer> drivingSkills) {
        this.drivingSkills = drivingSkills;
    }

    public Calendar getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Calendar modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Calendar getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Calendar lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public List<DriveModel> getDrives() {
        return drives;
    }

    public void setDrives(List<DriveModel> drives) {
        this.drives = drives;
    }

    public List<CarModel> getCars() {
        return cars;
    }

    public void setCars(List<CarModel> cars) {
        this.cars = cars;
    }

    public List<BookingModel> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingModel> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "UserModel{" +
//                "userId=" + userId +
                ", login='" + login + '\'' +
//                ", mail='" + mail + '\'' +
//                ", phone='" + phone + '\'' +
//                ", name='" + name + '\'' +
//                ", lastname='" + lastname + '\'' +
//                ", male=" + male +
//                ", birthDate=" + birthDate.getTime() +
//                ", role=" + role +
//                ", modifyTime=" + modifyTime.getTime() +
//                ", lastLoginTime=" + (lastLoginTime != null ? lastLoginTime.getTime() : "" ) +
//                ", isDeleted=" + isDeleted +
                '}';
    }
}
