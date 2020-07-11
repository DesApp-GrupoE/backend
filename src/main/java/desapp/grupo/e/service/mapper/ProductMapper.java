package desapp.grupo.e.service.mapper;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import desapp.grupo.e.model.dto.product.ProductDTO;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.service.exceptions.BadRequestException;
import desapp.grupo.e.service.log.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductMapper {

    public Product mapDtoToModel(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setPrice(productDTO.getPrice());
        product.setImg(productDTO.getImg());
        product.setStock(productDTO.getStock());
        return product;
    }

    public List<Product> mapCsvToModel(MultipartFile file) {
        if(file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }
        List<ProductDTO> productDTOS = mapCsvToDto(file);
        return productDTOS.stream().map(this::mapDtoToModel).collect(Collectors.toList());
    }

    private List<ProductDTO> mapCsvToDto(MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<ProductDTO> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(ProductDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } catch (Exception e) {
            Log.info("Unexpected error on read file: " + e.getMessage());
            throw new BadRequestException("Unexpected error on read file");
        }
    }

}
