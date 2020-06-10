package desapp.grupo.e.persistence.product;

import desapp.grupo.e.model.product.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ProductMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setIdCommerce(resultSet.getLong("commerce_id"));
        product.setImg(resultSet.getString("img"));
        product.setStock(resultSet.getInt("stock"));
        product.setName(resultSet.getString("name"));
        product.setBrand(resultSet.getString("brand"));
        product.setPrice(resultSet.getDouble("price"));
        return product;
    }
}
