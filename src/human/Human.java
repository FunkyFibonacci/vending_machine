package human;

public class Human {
    private final String name = "Саша";
    private int cardCashTotal = 10000;
    private int coinsTotal = 300;

    public String getName() {
        return name;
    }

    public int getCardCashTotal() {
        return cardCashTotal;
    }

    public int getCoinsTotal() {
        return coinsTotal;
    }

    public void setCardCashTotal(int cardCashTotal) {
        this.cardCashTotal = cardCashTotal;
    }

    public void setCoinsTotal(int coinsTotal) {
        this.coinsTotal = coinsTotal;
    }
}

