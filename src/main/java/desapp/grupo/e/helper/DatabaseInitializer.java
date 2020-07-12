package desapp.grupo.e.helper;

import desapp.grupo.e.helper.dummy.DummyData;
import desapp.grupo.e.model.purchase.Purchase;
import desapp.grupo.e.persistence.purchase.PurchaseRepository;
import desapp.grupo.e.persistence.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseInitializer {

    @Value("${spring.profiles.active}")
    private String typeDeploy;
    @Value("${spring.jpa.database}")
    private String database;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void initDatabaseExtensions() {
        if(!"POSTGRESQL".equalsIgnoreCase(database)) {
            return; // H2 no se banca las extensiones de postgres
        }
        String sqlCreateExtensionCube = "create extension if not exists cube";
        String sqlCreateExtensionEarthDistance = "create extension if not exists earthdistance";
        jdbcTemplate.execute(sqlCreateExtensionCube);
        jdbcTemplate.execute(sqlCreateExtensionEarthDistance);
    }

    @Transactional
    public void initDatabaseWithData() {
        if(!"dev".equalsIgnoreCase(typeDeploy)) {
            return; // Finalize execution
        }
        DummyData dummyData = new DummyData();
        this.userRepository.save(dummyData.createUser1());
        this.userRepository.save(dummyData.createUser2());
        this.userRepository.save(dummyData.createUser3());

        Purchase purchase = dummyData.createPurchase1(1L, 2L);
        Purchase purchase2 = dummyData.createPurchase2(1L, 3L);
        this.purchaseRepository.save(purchase);
        this.purchaseRepository.save(purchase2);
    }

}