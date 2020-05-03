package desapp.grupo.e.model.product;

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

    public String getDescription() {
        return this.description;
    }
}
