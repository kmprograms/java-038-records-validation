import java.math.BigDecimal;

public class App {
    /*
        Przechowamy za pomocą rekordu dane na temat produktu, które walidujemy przed
        utworzeniem instancji.
    */

    /*
        Na początek klasa do rzucania wyjątków od błędów walidacji danych produktu
    */
    static class ProductValidationException extends RuntimeException {
        public ProductValidationException(String message) {
            super(message);
        }
    }

    /*
        Przechodzimy do implementacji rekordu
    */
    record Product(String name, BigDecimal price, Integer quantity) {

        /*
            Implementujemy walidację w ramach compact constructor
        */
        public Product {
            /*
                Nazwa produktu musi składać się z samych dużych liter
            */
            if (isNameNotCorrect("[A-Z]+")) {
                throw new ProductValidationException("Name is not correct");
            }

            /*
                Cena musi mieć dodatnią wartość
            */
            if (isPriceNotCorrect()) {
                throw new ProductValidationException("Price is not correct");
            }

            /*
                Ilość musi mieć dodatnią wartość
            */
            if (isQuantityNotCorrect()) {
                throw new ProductValidationException("Quantity is not correct");
            }
        }

        /*
            Implementujemy metody pomocnicze
            Metody zwracają true, jeżeli nie zostały spełnione warunki walidacji.
            Dzięki temu później nie będę musiał ich wywoływać z operatorem negacji !
        */

        private boolean isNameNotCorrect(final String regex) {
            return name == null || !name.matches(regex);
        }

        private boolean isPriceNotCorrect() {
            return price == null || price.compareTo(BigDecimal.ZERO) <= 0;
        }

        private boolean isQuantityNotCorrect() {
            return quantity == null || quantity <= 0;
        }
    }

    public static void main(String[] args) {
        try {
            var p1 = new Product("PRODUCTA", new BigDecimal("100"), 10);
            System.out.println(p1);

            var p2 = new Product("Product A", new BigDecimal("100"), 10);
            System.out.println(p2);
        } catch (Exception e) {
            System.out.printf("Exception: %s", e.getMessage());
        }
    }
}
