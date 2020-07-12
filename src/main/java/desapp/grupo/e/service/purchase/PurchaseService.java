package desapp.grupo.e.service.purchase;

import desapp.grupo.e.model.cart.CartProduct;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.model.purchase.DeliveryType;
import desapp.grupo.e.model.purchase.Purchase;
import desapp.grupo.e.model.purchase.PurchaseTurn;
import desapp.grupo.e.persistence.product.ProductRepository;
import desapp.grupo.e.persistence.purchase.PurchaseRepository;
import desapp.grupo.e.persistence.purchase.PurchaseTurnRepository;
import desapp.grupo.e.service.cart.ShoppingCartService;
import desapp.grupo.e.service.exceptions.StockException;
import desapp.grupo.e.webservice.controller.purchase.PurchaseRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private PurchaseTurnRepository purchaseTurnRepository;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Purchase> getAllPurchases(Long userId) {
        return purchaseRepository.findAllByUserId(userId);
    }

    @Transactional
    public void createPurchases(Long userId, List<PurchaseRequestDTO> purchaseRequestDTOS, String cartId) {
        List<Purchase> purchasesCart = shoppingCartService.generatePurchases(cartId);
        purchasesCart.forEach(purchase -> createAndSavePurchase(userId, purchaseRequestDTOS, purchase));
        shoppingCartService.deleteCart(cartId);
    }

    private void createAndSavePurchase(Long userId, List<PurchaseRequestDTO> purchaseRequestDTOS, Purchase purchase) {
        PurchaseRequestDTO purchaseRequestDTO = this.getPurchaseRequestDTO(purchase.getCommerceId(), purchaseRequestDTOS);
        this.completePurchaseData(purchase, userId, purchaseRequestDTO);
        this.updateTurnAddress(purchase, purchaseRequestDTO.getDeliveryType(), purchaseRequestDTO.getAddress());
        purchase.getCartProducts().forEach(this::updateStock);
        this.purchaseRepository.save(purchase);
    }

    private void updateStock(CartProduct cartProduct) {
        Product product = this.productRepository.getOne(cartProduct.getProductId());
        if(product.getStock() < cartProduct.getQuantity()) {
            throw new StockException(product);
        }
        product.setStock(product.getStock() - cartProduct.getQuantity());
        this.productRepository.save(product);
    }

    private void updateTurnAddress(Purchase purchase, DeliveryType deliveryType, String address) {
        if(DeliveryType.TO_ADDRESS.equals(deliveryType)) {
            associateFirstTurnDeliveryToUserAndPurchsae(purchase, address);
        } else {
            purchaseTurnRepository.addUserToTurn(purchase.getUserId(), purchase.getTurnId());
        }
    }

    private void associateFirstTurnDeliveryToUserAndPurchsae(Purchase purchase, String address) {
        Optional<PurchaseTurn> optTurn = purchaseTurnRepository.findFirstByIdCommerceAndDeliveryType(purchase.getCommerceId(), DeliveryType.TO_ADDRESS);
        if(optTurn.isPresent()) {
            PurchaseTurn purchaseTurn = optTurn.get();
            purchaseTurn.setDeliveryAddress(address);
            purchaseTurn.setIdUser(purchase.getUserId());
            purchase.setTurnId(purchaseTurn.getId());
            this.purchaseTurnRepository.save(purchaseTurn);
        }
    }

    private void completePurchaseData(Purchase purchase, Long userId, PurchaseRequestDTO purchaseRequest) {
        purchase.setDeliveryType(purchaseRequest.getDeliveryType());
        purchase.setTurnId(purchaseRequest.getTurnId());
        purchase.setUserId(userId);
        purchase.setDate(LocalDateTime.now());
    }

    private PurchaseRequestDTO getPurchaseRequestDTO(Long commerceId, List<PurchaseRequestDTO> purchaseRequestDTOS) {
        return purchaseRequestDTOS.stream()
                    .filter(purchaseRequestDTO -> commerceId.equals(purchaseRequestDTO.getCommerceId()))
                    .findFirst().get();
    }
}
