package human;

public class Human {
    private final String name = "Саша";
    private CardOfHuman demir;
    private int coinsTotal = 300;

    public String getName() {
        return name;
    }
    public int getCoinsTotal() {
        return coinsTotal;
    }
    public void setCoinsTotal(int coinsTotal) {
        this.coinsTotal = coinsTotal;
    }
}

