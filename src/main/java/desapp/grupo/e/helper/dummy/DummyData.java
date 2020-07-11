package desapp.grupo.e.helper.dummy;

import desapp.grupo.e.model.builder.commerce.CommerceBuilder;
import desapp.grupo.e.model.builder.product.ProductBuilder;
import desapp.grupo.e.model.builder.purchase.PurchaseTurnBuilder;
import desapp.grupo.e.model.builder.user.UserBuilder;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.model.purchase.DeliveryType;
import desapp.grupo.e.model.purchase.PurchaseTurn;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.model.user.CommerceSector;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.service.utils.RandomString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DummyData {

    private BCryptPasswordEncoder encrypter;
    private RandomString randomString;

    public DummyData() {
        this.encrypter = new BCryptPasswordEncoder();
        this.randomString = new RandomString();
    }

    public User createUser1() {
        User user1 = UserBuilder.aUser()
                .withName("Pepe")
                .withSurname("Pepe")
                .withEmail("pepito@test.com")
                .withPassword(encrypter.encode("12345678"))
                .withSecret(randomString.nextStringOnlyCharacters(15))
                .build();

        Commerce commerce1 = CommerceBuilder.aCommerce()
                .withName("Lo de Minguito")
                .withAddress("Brandsen 300, Quilmes")
                .withLatitude(-34.725805)
                .withLongitude(-58.252009)
                .withPhone("1155443322")
                .withDoDelivery(true)
                .withDeliveryUp(5.0)
                .withSectors(Arrays.asList(CommerceSector.GROCERY_STORE))
                .build();
        commerce1.setPurchaseTurns(createTurns(1L));

        Product product1 = ProductBuilder.aProduct()
                .withName("Fideos tallarines Lucchetti 500gr").withBrand("Luchetti")
                .withStock(100).withPrice(50.0)
                .withImg("https://http2.mlstatic.com/fideo-lucchetti-tallarin-500-grs-D_NQ_NP_952769-MLA31029340446_062019-F.jpg")
                .build();

        Product product2 = ProductBuilder.aProduct()
                .withName("Coca Cola 2.25L").withBrand("Coca Cola")
                .withStock(100).withPrice(130.0)
                .withImg("https://www.licoreraexpress.com/wp-content/uploads/2018/01/Coca-Cola-Bottle-2L-1.jpg")
                .build();

        Product product3 = ProductBuilder.aProduct()
                .withName("Coca Cola Light 1.5L").withBrand("Coca Cola")
                .withStock(100).withPrice(70.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/7/7790895001451_02.jpg")
                .build();

        Product product4 = ProductBuilder.aProduct()
                .withName("Arroz largo fino Apóstoles sin TACC 1kg").withBrand("Apóstoles")
                .withStock(100).withPrice(55.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/7/7791120031656_02.jpg")
                .build();

        Product product5 = ProductBuilder.aProduct()
                .withName("Arroz Molinos Ala 1kg").withBrand("Molinos Ala")
                .withStock(100).withPrice(65.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/7/7791120031557_02.jpg")
                .build();

        Product product6 = ProductBuilder.aProduct()
                .withName("Té Green Hills 25 saquitos").withBrand("Green Hills")
                .withStock(100).withPrice(50.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/7/7/7790480008285_02.jpg")
                .build();

        Product product7 = ProductBuilder.aProduct()
                .withName("Té Taragui 25 saquitos").withBrand("Taragui")
                .withStock(100).withPrice(39.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/7/7790387010305_02_1.jpg")
                .build();


        List<Product> products = Arrays.asList(product1, product2, product3, product4, product5, product6, product7);
        commerce1.setProducts(products);
        user1.setCommerce(commerce1);
        return user1;
    }

    public User createUser2() {
        User user1 = UserBuilder.aUser()
                .withName("Susana")
                .withSurname("Rodriguez")
                .withEmail("susana@gmail.com")
                .withPassword(this.encrypter.encode("12345678"))
                .withSecret(randomString.nextStringOnlyCharacters(15))
                .build();

        Commerce commerce1 = CommerceBuilder.aCommerce()
                .withName("Susi")
                .withAddress("Av. Francia 4996, Quilmes")
                .withLatitude(-34.761164)
                .withLongitude(-58.267327)
                .withPhone("110303456")
                .withDoDelivery(true)
                .withDeliveryUp(5.0)
                .withSectors(Arrays.asList(CommerceSector.GROCERY_STORE))
                .build();
        commerce1.setPurchaseTurns(createTurns(2L));

        Product product1 = ProductBuilder.aProduct()
                .withName("Fideos tallarines Lucchetti 500gr").withBrand("Luchetti")
                .withStock(100).withPrice(60.0)
                .withImg("https://http2.mlstatic.com/fideo-lucchetti-tallarin-500-grs-D_NQ_NP_952769-MLA31029340446_062019-F.jpg")
                .build();

        Product product2 = ProductBuilder.aProduct()
                .withName("Fideos tallarines Knorr 500gr").withBrand("Knorr")
                .withStock(100).withPrice(55.0)
                .withImg("https://geant.vteximg.com.br/arquivos/ids/227890-1000-1000/575662.jpg?v=636833437802530000")
                .build();

        Product product3 = ProductBuilder.aProduct()
                .withName("Harina de Trigo Pureza 0000 1Kg").withBrand("Pureza")
                .withStock(100).withPrice(50.0)
                .withImg("https://http2.mlstatic.com/harina-de-trigo-pureza-0000-paquete-1-kg-D_NQ_NP_685051-MLA28256118213_092018-F.jpg")
                .build();

        Product product4 = ProductBuilder.aProduct()
                .withName("Harina de Trigo Favorita 000 1Kg").withBrand("Favorita")
                .withStock(100).withPrice(50.0)
                .withImg("https://http2.mlstatic.com/harina-de-trigo-favorita-000-paquete-1-kg-D_NQ_NP_681931-MLA28256145294_092018-F.jpg")
                .build();

        Product product5 = ProductBuilder.aProduct()
                .withName("Queso cremoso Puyehue 1kg").withBrand("Peyehue")
                .withStock(20).withPrice(319.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/2/5/2505587000002_02.jpg")
                .build();

        Product product6 = ProductBuilder.aProduct()
                .withName("Queso cremoso Cremon 1kg").withBrand("La serenisima")
                .withStock(20).withPrice(469.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/2/3/2305242000008_02_nuevopack.jpg")
                .build();

        Product product7 = ProductBuilder.aProduct()
                .withName("Leche entera larga vida La Serenisima 1L").withBrand("La serenisima")
                .withStock(30).withPrice(70.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/7/7790742308801_01_nuevopack.jpg")
                .build();

        Product product8 = ProductBuilder.aProduct()
                .withName("Leche descremada larga vida La Serenisima 1L").withBrand("La serenisima")
                .withStock(30).withPrice(69.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/7/7790742362100_01_nuevopack.jpg")
                .build();


        List<Product> products = Arrays.asList(product1, product2, product3, product4, product5, product6, product7, product8);
        commerce1.setProducts(products);
        user1.setCommerce(commerce1);
        return user1;
    }

    public User createUser3() {
        User user1 = UserBuilder.aUser()
                .withName("Pedro")
                .withSurname("Lopez")
                .withEmail("pedro_lopez@gmail.com")
                .withPassword(this.encrypter.encode("12345678"))
                .withSecret(randomString.nextStringOnlyCharacters(15))
                .withAuth2fa(false)
                .build();

        Commerce commerce1 = CommerceBuilder.aCommerce()
                .withName("El barba")
                .withAddress("Estanislao del campo 4579, Quilmes")
                .withLatitude(-34.765784)
                .withLongitude(-58.263849)
                .withPhone("110303456")
                .withDoDelivery(true)
                .withDeliveryUp(4.0)
                .withSectors(Arrays.asList(CommerceSector.GROCERY_STORE))
                .build();
        commerce1.setPurchaseTurns(createTurns(3L));

        Product product1 = ProductBuilder.aProduct()
                .withName("Aceite de girasol Natura 1.5L").withBrand("Natura")
                .withStock(25).withPrice(140.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/7/7790272001029_02.jpg")
                .build();

        Product product2 = ProductBuilder.aProduct()
                .withName("Aceite de girasol Cocinero 1.5L").withBrand("Cocinero")
                .withStock(30).withPrice(130.0)
                .withImg("https://geant.vteximg.com.br/arquivos/ids/227890-1000-1000/575662.jpg?v=636833437802530000")
                .build();

        Product product3 = ProductBuilder.aProduct()
                .withName("Mayonesa Hellmann's clasica 475gr").withBrand("Hellmann's")
                .withStock(15).withPrice(80.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/7/7794000960091_01.jpg")
                .build();

        Product product4 = ProductBuilder.aProduct()
                .withName("Sal fina Celusal 500gr").withBrand("Celusal")
                .withStock(20).withPrice(37.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/7/7790072001014.jpg")
                .build();

        Product product5 = ProductBuilder.aProduct()
                .withName("Gaseosa Coca Cola 2.25L").withBrand("Coca Cola")
                .withStock(50).withPrice(143.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/7/7790895000997_01.jpg")
                .build();

        Product product6 = ProductBuilder.aProduct()
                .withName("Gaseosa Schweppes sin azucar pomelo 2.25L").withBrand("Schweppes")
                .withStock(20).withPrice(88.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/7/7790895010095_01.jpg")
                .build();

        Product product7 = ProductBuilder.aProduct()
                .withName("Cerveza rubia Andes Origen 6 x 473cc").withBrand("Andes")
                .withStock(30).withPrice(375.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/7/7792798001712_01.jpg")
                .build();

        Product product8 = ProductBuilder.aProduct()
                .withName("Rollo de cocina Elegante 3 x 50 paños").withBrand("Elegante")
                .withStock(30).withPrice(50.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/7/7793344904013_02.jpg")
                .build();

        Product product9 = ProductBuilder.aProduct()
                .withName("Rollo de cocina Sussex 3 x 60 paños").withBrand("Sussex")
                .withStock(30).withPrice(80.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/7/7790250056799_01.jpg")
                .build();

        Product product10 = ProductBuilder.aProduct()
                .withName("Jabon en polvo para la ropa Ala 3kg").withBrand("Ala")
                .withStock(30).withPrice(369.0)
                .withImg("https://supermercado.carrefour.com.ar/media/catalog/product/cache/1/small_image/214x/9df78eab33525d08d6e5fb8d27136e95/7/7/7791290011731_01.jpg")
                .build();

        List<Product> products = Arrays.asList(product1, product2, product3, product4, product5, product6, product7,
                product8, product9, product10);
        commerce1.setProducts(products);
        user1.setCommerce(commerce1);
        return user1;
    }

    private List<PurchaseTurn> createTurns(Long commerceId) {
        PurchaseTurn turn1 = PurchaseTurnBuilder.aPurchaseTurn()
                .withDateTurn(LocalDateTime.now().plusDays(1).withHour(12).withMinute(0).withSecond(0).withNano(0))
                .withDeliveryType(DeliveryType.ON_COMMERCE)
                .withIdCommerce(commerceId).build();
        PurchaseTurn turn2 = PurchaseTurnBuilder.aPurchaseTurn()
                .withDateTurn(LocalDateTime.now().plusDays(1).withHour(12).withMinute(30).withSecond(0).withNano(0))
                .withDeliveryType(DeliveryType.ON_COMMERCE)
                .withIdCommerce(commerceId).build();
        PurchaseTurn turn3 = PurchaseTurnBuilder.aPurchaseTurn()
                .withDateTurn(LocalDateTime.now().plusDays(3).withHour(15).withMinute(0).withSecond(0).withNano(0))
                .withDeliveryType(DeliveryType.ON_COMMERCE)
                .withIdCommerce(commerceId).build();
        PurchaseTurn turn4 = PurchaseTurnBuilder.aPurchaseTurn()
                .withDateTurn(LocalDateTime.now().plusDays(3).withHour(15).withMinute(15).withSecond(0).withNano(0))
                .withDeliveryType(DeliveryType.ON_COMMERCE)
                .withIdCommerce(commerceId).build();
        PurchaseTurn turn5 = PurchaseTurnBuilder.aPurchaseTurn()
                .withDateTurn(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0))
                .withDeliveryType(DeliveryType.ON_COMMERCE)
                .withIdCommerce(commerceId).build();
        PurchaseTurn turn6 = PurchaseTurnBuilder.aPurchaseTurn()
                .withDateTurn(LocalDateTime.now().plusDays(3).withHour(10).withMinute(15).withSecond(0).withNano(0))
                .withDeliveryType(DeliveryType.TO_ADDRESS)
                .withIdCommerce(commerceId).build();
        PurchaseTurn turn7 = PurchaseTurnBuilder.aPurchaseTurn()
                .withDateTurn(LocalDateTime.now().plusDays(3).withHour(10).withMinute(30).withSecond(0).withNano(0))
                .withDeliveryType(DeliveryType.TO_ADDRESS)
                .withIdCommerce(commerceId).build();
        PurchaseTurn turn8 = PurchaseTurnBuilder.aPurchaseTurn()
                .withDateTurn(LocalDateTime.now().plusDays(1).withHour(12).withMinute(0).withSecond(0).withNano(0))
                .withDeliveryType(DeliveryType.TO_ADDRESS)
                .withIdCommerce(commerceId).build();
        PurchaseTurn turn9 = PurchaseTurnBuilder.aPurchaseTurn()
                .withDateTurn(LocalDateTime.now().plusDays(1).withHour(12).withMinute(15).withSecond(0).withNano(0))
                .withDeliveryType(DeliveryType.TO_ADDRESS)
                .withIdCommerce(commerceId).build();
        PurchaseTurn turn10 = PurchaseTurnBuilder.aPurchaseTurn()
                .withDateTurn(LocalDateTime.now().plusDays(3).withHour(10).withMinute(45).withSecond(0).withNano(0))
                .withDeliveryType(DeliveryType.TO_ADDRESS)
                .withIdCommerce(commerceId).build();
        return Arrays.asList(turn1, turn2, turn3, turn4, turn5, turn6, turn7, turn8, turn9, turn10);
    }

}
