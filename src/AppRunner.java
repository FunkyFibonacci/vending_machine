import enums.ActionLetter;
import human.Human;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.io.IOException;
import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();

    private final CoinAcceptor coinAcceptor;
    private static final Human human = new Human();

    private static boolean isExit = false;

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });
        coinAcceptor = new CoinAcceptor(200);
    }

    public static void run() {
        System.out.println("Здарово " + human.getName() + ", че будем брать?");

        AppRunner app = new AppRunner();

        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);
        print("Монет на сумму в автомате: " + coinAcceptor.getAmount());
        System.out.println("В кошелке монет: " + human.getCoinsTotal());
        System.out.println("На карте денег: " + human.getDemir().getTotalSum() + "\n");
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);
    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            if (coinAcceptor.getAmount() >= products.get(i).getPrice()) {
                allowProducts.add(products.get(i));
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        System.out.println(" 0 - пополнить автомат с карты");
        print(" a - пополнить автомат на 20 монет");
        showActions(products);
        print(" h - Выйти");
        String action = fromConsole().trim().substring(0, 1);
        try {

            for (int i = 0; i < products.size(); i++) {
                if (action.equals("h")) {
                    isExit = true;
                    break;
                }
                if (action.equals("a")) {
                    if (human.getCoinsTotal() < 20){
                        throw new Exception("Закончились монеты!");
                    }
                    coinAcceptor.setAmount(coinAcceptor.getAmount() + 20);
                    human.setCoinsTotal(human.getCoinsTotal()-20);

                    System.out.println("Вы выбрали пополнить баланс на 20 монет");
                    break;
                }
                if (action.equals("0")){
                    System.out.println("Вы выбрали пополнить автомат с карты\nВведите пароль карты: ");

                    String password = fromConsole().trim();
                    if (password.equals(human.getDemir().getPasswordOfCard())){
                        if (human.getDemir().getTotalSum()<=0){
                            throw new Exception("На карте закончились деньги!!!");
                        }
                        System.out.println("Введите сколько монет хоите пополинть: ");
                        String cashToPay = fromConsole();
                        int payment = Integer.parseInt(cashToPay);
                        if (payment>human.getDemir().getTotalSum()){
                            throw new Exception("Введена сумма больше чем на карте!!!");
                        }
                        coinAcceptor.setAmount(coinAcceptor.getAmount()+payment);
                        human.getDemir().setTotalSum(human.getDemir().getTotalSum()-payment);
                    } else {
                        throw new Exception("Неправильный пароль!");
                    }
                    break;
                }

                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    coinAcceptor.setAmount(coinAcceptor.getAmount() - products.get(i).getPrice());
                    print("Вы купили " + products.get(i).getName());
                    System.out.println("===========================");
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            print("Недопустимая буква, или пустая строка, или не парсится. Попрбуйте еще раз.");
            chooseAction(products);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            chooseAction(products);
        }
    }

    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        return new Scanner(System.in).nextLine();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }
}
