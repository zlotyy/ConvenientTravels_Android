package mvc.com.model;

import java.util.Calendar;

public class SessionActualModel {
    private long sessionId;

    private Calendar logInTime;

    // Polaczenie 1 User do N Sesji
    private UserModel user;




    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public Calendar getLogInTime() {
        return logInTime;
    }

    public void setLogInTime(Calendar logInTime) {
        this.logInTime = logInTime;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
