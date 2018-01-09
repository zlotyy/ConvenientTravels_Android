package mvc.com.enums;

public enum Male {
    KOBIETA("Kobieta"),
    MEZCZYZNA("Mężczyzna");

    private String male;

    Male(String male){
        this.male = male;
    }

    public String getMale(){
        return male;
    }
}
