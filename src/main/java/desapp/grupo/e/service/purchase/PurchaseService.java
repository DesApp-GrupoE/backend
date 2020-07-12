package desapp.grupo.e.service.purchase;

import desapp.grupo.e.model.purchase.Purchase;
import desapp.grupo.e.persistence.purchase.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public List<Purchase> getAllPurchases(Long userId) {
        return purchaseRepository.findAllByUserId(userId);
    }
}
