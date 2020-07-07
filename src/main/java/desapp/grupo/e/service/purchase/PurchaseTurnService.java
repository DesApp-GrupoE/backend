package desapp.grupo.e.service.purchase;

import desapp.grupo.e.model.purchase.PurchaseTurn;
import desapp.grupo.e.persistence.purchase.PurchaseTurnRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseTurnService {

    private PurchaseTurnRepository purchaseTurnRepository;

    public PurchaseTurnService(PurchaseTurnRepository purchaseTurnRepository) {
        this.purchaseTurnRepository = purchaseTurnRepository;
    }

    @Transactional
    public PurchaseTurn createPurchaseTurn(PurchaseTurn purchaseTurn) {
        return purchaseTurnRepository.save(purchaseTurn);
    }

    @Transactional
    public List<PurchaseTurn> getPurchaseTurns(Long commerceId) {
        return purchaseTurnRepository.findAllByIdCommerce(commerceId);
    }
}
