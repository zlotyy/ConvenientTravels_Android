package mvc.com.enums;

public enum LuggageSize {
    MALY("Mały"),
    SREDNI("Średni"),
    DUZY("Duży");

    private String luggageSize;

    LuggageSize(String luggageSize) {
        this.luggageSize = luggageSize;
    }

    public String getLuggageSize(){
        return luggageSize;
    }
}