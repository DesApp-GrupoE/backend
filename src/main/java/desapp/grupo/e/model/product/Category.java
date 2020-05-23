package desapp.grupo.e.model.product;

import java.util.Arrays;

public enum Category {

    CARNES("Carnes"),
    BEBIDAS("Bebidas"),
    FRUTAS_VERDURAS("Frutas y Verduras"),
    LACTEOS("Lacteos"),
    QUESOS_FIAMBRES("Quesos y Fiambres"),
    ALMACEN("Almacen"),
    LIMPIEZA("Limpieza"),
    MASCOTAS("Mascotas");

    private String description;

    Category(String description) {
        this.description = description;
    }

    public static Category findByName(String category) {
        return Arrays.stream(values())
                .filter(cat -> cat.name().equals(category))
                .findFirst()
                .orElse(null);
    }

    public String getDescription() {
        return this.description;
    }
}
