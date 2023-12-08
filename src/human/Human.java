package human;

public class Human {
    private final CardOfHuman demir = new CardOfHuman();
    private int coinsTotal = 300;


    public int getCoinsTotal() {
        return coinsTotal;
    }

    public void setCoinsTotal(int coinsTotal) {
        this.coinsTotal = coinsTotal;
    }

    public CardOfHuman getDemir() {
        return demir;
    }

    public String getName() {
        return "Саша";
    }
}

