package desapp.grupo.e.model.user;

public enum CommerceSector {

    OTHER("Other"),
    GROCERY_STORE("Grocery Store"),
    BUTCHERS_SHOP("Butcher's Shop"),
    DELI_COUNTER("Deli Counter"),
    BAKERY("Bakery"),
    FRUIT_STORE("Fruit Store"),
    HARDWARE_STORE("Hardware Store"),
    ICE_CREAM_SHOP("Ice Cream Shop");

    private String description;

    CommerceSector(String description) {
        this.description = description;
    }

}
