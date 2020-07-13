package desapp.grupo.e.service.purchase;

import desapp.grupo.e.model.purchase.Purchase;
import desapp.grupo.e.model.purchase.PurchaseTurn;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.purchase.PurchaseRepository;
import desapp.grupo.e.persistence.purchase.PurchaseTurnRepository;
import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseTurnService {

    private PurchaseTurnRepository purchaseTurnRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;

    public PurchaseTurnService(PurchaseTurnRepository purchaseTurnRepository) {
        this.purchaseTurnRepository = purchaseTurnRepository;
    }

    @Transactional
    public PurchaseTurn createPurchaseTurn(PurchaseTurn purchaseTurn) {
        return purchaseTurnRepository.save(purchaseTurn);
    }

    @Transactional
    public List<PurchaseTurn> getPurchaseTurns(Long commerceId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return purchaseTurnRepository.findAllByIdCommerceAndDateTurnBetweenOrderByDateTurn(commerceId, dateFrom, dateTo);
    }

    @Transactional(readOnly = true)
    public List<PurchaseTurn> getFreePurchaseTurns(Long commerceId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return purchaseTurnRepository.findAllByIdCommerceAndIdUserIsNullAndDateTurnBetweenOrderByDateTurn(commerceId, dateFrom, dateTo);
    }

    @Transactional(readOnly = true)
    public void sendEmailsBeforeTurnPurchases() {
        List<PurchaseTurn> purchaseTurns = purchaseTurnRepository.findNextTurnsToExpire();
        purchaseTurns.forEach(purchaseTurn -> {
            User user = userRepository.getOne(purchaseTurn.getIdUser());
            Purchase purchase = purchaseRepository.getByTurnId(purchaseTurn.getId());
            mailService.sendPurchaseTurnEmail(user.getEmail(), purchase.getId());
        });
    }
}
