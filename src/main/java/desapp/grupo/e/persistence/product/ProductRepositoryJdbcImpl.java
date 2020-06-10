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

        String sqlParams = "";
        if(!isNullOrEmpty(productSearchDTO.getName())) {
            sqlParams += " name like :name ";
            params.put("name", "%" +productSearchDTO.getName() + "%");
        }
        if(!isNullOrEmpty(productSearchDTO.getBrand())) {
            if(!isNullOrEmpty(sqlParams)) {
                sqlParams += " and ";
            }
            sqlParams += " brand = :brand ";
            params.put("brand", productSearchDTO.getBrand());
        }
        sql += " where " + sqlParams;
        return jdbcTemplate.query(sql, params, new ProductMapper());
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.equals("");
    }
}
