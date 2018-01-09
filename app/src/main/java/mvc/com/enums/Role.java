package mvc.com.enums;

public enum Role {
    ROLE_USER("Uzytkownik"),
    ROLE_ADMIN("Administrator");

    private String role;

    Role(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
