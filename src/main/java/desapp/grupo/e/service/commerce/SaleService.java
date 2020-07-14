package desapp.grupo.e.service.commerce;

import desapp.grupo.e.model.purchase.Purchase;
import desapp.grupo.e.persistence.purchase.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Transactional(readOnly = true)
    public List<Purchase> getPurchaseFromCommerceByUser(Long userId) {
        return purchaseRepository.findAllPurchasesFromCommerceByUser(userId);
    }
}
