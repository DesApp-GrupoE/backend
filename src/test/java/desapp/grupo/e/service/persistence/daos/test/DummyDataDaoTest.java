package desapp.grupo.e.service.persistence.daos.test;

import desapp.grupo.e.model.builder.test.DummyDataBuilder;
import desapp.grupo.e.model.test.DummyData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import desapp.grupo.e.service.persistence.JPAHibernateTest;
import desapp.grupo.e.service.persistence.exception.DataErrorException;

import java.util.List;
import java.util.Optional;

public class DummyDataDaoTest extends JPAHibernateTest {

    private DummyDataDao dummyDataDao;
    
    @BeforeEach
    public void setUp() {
        this.dummyDataDao = new DummyDataDao(entityManager);
    }

    @AfterEach
    public void tearDown() throws DataErrorException {
        this.dummyDataDao.deleteAll();
    }


    @Test
    public void getAllCustomers() {
        List<DummyData> dummyDataList = dummyDataDao.getAll();
        Assertions.assertTrue(dummyDataList.isEmpty());
    }

    @Test
    public void saveADummyDataGenerateAnId() throws DataErrorException {
        DummyData dummyData = DummyDataBuilder.aDummyData()
                .withEmail("ariel.ramirez@test.test")
                .withPassword("secret_password")
                .build();

        Assertions.assertNull(dummyData.getId());

        this.dummyDataDao.save(dummyData);

        Assertions.assertNotNull(dummyData.getId());
    }

    @Test
    public void saveADummyDataWithoutAMandatoryFieldShouldBeThrowDataErrorException() {
        DummyData dummyData = DummyDataBuilder.aDummyData()
                .withEmail("ariel.ramirez@test.test")
                .build();

        Assertions.assertThrows(DataErrorException.class, () -> this.dummyDataDao.save(dummyData));
    }

    @Test
    public void getADummyDataById() throws DataErrorException {
        DummyData dummyData = DummyDataBuilder.aDummyData()
                .withEmail("ariel.ramirez@test.test")
                .withPassword("secret_password")
                .build();

        this.dummyDataDao.save(dummyData);

        Optional<DummyData> optDummyData = this.dummyDataDao.getById(dummyData.getId());
        Assertions.assertTrue(optDummyData.isPresent());

        DummyData dummyDataFromDB = optDummyData.get();
        Assertions.assertEquals("ariel.ramirez@test.test", dummyDataFromDB.getEmail());
        Assertions.assertEquals("secret_password", dummyDataFromDB.getPassword());
    }

    @Test
    public void updateADummyData() throws DataErrorException {
        DummyData dummyData = DummyDataBuilder.aDummyData()
                .withEmail("ariel.ramirez@test.test")
                .withPassword("secret_password")
                .build();
        this.dummyDataDao.save(dummyData);

        dummyData.setEmail("New Email");
        dummyData.setPassword("New Password");
        this.dummyDataDao.update(dummyData);

        Optional<DummyData> optDummyData = this.dummyDataDao.getById(dummyData.getId());
        Assertions.assertTrue(optDummyData.isPresent());

        DummyData dummyDataFromDB = optDummyData.get();
        Assertions.assertEquals("New Email", dummyDataFromDB.getEmail());
        Assertions.assertEquals("New Password", dummyDataFromDB.getPassword());
    }


    @Test
    public void deleteADummyDataFromDB() throws DataErrorException {
        DummyData dummyData = DummyDataBuilder.aDummyData()
                .withEmail("ariel.ramirez@test.test")
                .withPassword("secret_password")
                .build();

        this.dummyDataDao.save(dummyData);

        Optional<DummyData> optDummyData = this.dummyDataDao.getById(dummyData.getId());
        Assertions.assertTrue(optDummyData.isPresent());

        this.dummyDataDao.delete(optDummyData.get());

        Optional<DummyData> optDummyDataDeleted = this.dummyDataDao.getById(dummyData.getId());
        Assertions.assertFalse(optDummyDataDeleted.isPresent());
    }

}
