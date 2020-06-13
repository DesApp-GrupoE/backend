package desapp.grupo.e.persistence.product;

import desapp.grupo.e.model.dto.search.ProductSearchDTO;
import desapp.grupo.e.model.product.Product;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepositoryJdbcImpl {

    private static final Double MILLA_TO_KM = 1.609344;
    private NamedParameterJdbcTemplate jdbcTemplate;

    public ProductRepositoryJdbcImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    public List<Product> findProducts(ProductSearchDTO productSearchDTO) {
        String sql = "select * from product";
        Map<String, Object> params = new HashMap<>();
        if(productSearchDTO == null || productSearchDTO.isEmptyObject()) {
            return jdbcTemplate.query(sql, params, new ProductMapper());
        }

        String sqlParams = paramsFilterProducts(productSearchDTO, params);
        if(!isNullOrEmpty(sqlParams)) {
            sql += " where " + sqlParams;
        }
        return jdbcTemplate.query(sql, params, new ProductMapper());
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.equals("");
    }

    public List<Product> findProductsInRadioKm(ProductSearchDTO productSearchDTO, Double latitude, Double longitude, Integer kilometers) {
        String sql = "select * from product";
        Map<String, Object> params = new HashMap<>();
        String paramsProduct = "";
        if(productSearchDTO != null && !productSearchDTO.isEmptyObject()) {
            String paramsFilter = paramsFilterProducts(productSearchDTO, params);
            if(isNullOrEmpty(paramsFilter)) {
               paramsProduct = paramsFilter;
            } else {
                paramsProduct = paramsFilter + " and ";
            }
        }

        sql += " where " + paramsProduct;
        sql += " commerce_id in (select c.id from commerce c where " +
                "( " +
                "   point(c.longitude, c.latitude)<@>point(:longitude, :latitude)" +
                ") * :toKm < :km)" ;
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("toKm", MILLA_TO_KM);
        params.put("km", kilometers);
        return jdbcTemplate.query(sql, params, new ProductMapper());
    }

    private String paramsFilterProducts(ProductSearchDTO productSearchDTO, Map<String, Object> params) {
        String sqlParams = "";
        if(!isNullOrEmpty(productSearchDTO.getName())) {
            sqlParams += " upper(name) like upper(:name) ";
            params.put("name", "%" +productSearchDTO.getName() + "%");
        }
        if(!isNullOrEmpty(productSearchDTO.getBrand())) {
            if(!isNullOrEmpty(sqlParams)) {
                sqlParams += " and ";
            }
            sqlParams += " brand = :brand ";
            params.put("brand", productSearchDTO.getBrand());
        }
        return sqlParams;
    }
}
