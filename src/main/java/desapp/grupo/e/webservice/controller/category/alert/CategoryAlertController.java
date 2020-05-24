package desapp.grupo.e.webservice.controller.category.alert;

import desapp.grupo.e.model.dto.category.alert.CategoryAlertDTO;
import desapp.grupo.e.model.product.Category;
import desapp.grupo.e.model.product.CategoryAlert;
import desapp.grupo.e.service.category.alert.CategoryAlertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CategoryAlertController {

    private static final String USER_ID = "user_id";
    private static final String CAT_ALERT_ID = "cat_alert_id";
    private static final String URL_BASE = "/user/{" + USER_ID + "}/category-alert";
    private static final String URL_DELETE_CAT_ALERT = URL_BASE + "/{" + CAT_ALERT_ID+ "}";

    private CategoryAlertService categoryAlertService;

    public CategoryAlertController(CategoryAlertService categoryAlertService) {
        this.categoryAlertService = categoryAlertService;
    }

    @PostMapping(URL_BASE)
    public ResponseEntity<CategoryAlertDTO> createCategoryAlert(@PathVariable(USER_ID) Long userId,
                                                                @Valid @RequestBody CategoryAlertDTO categoryAlertDTO) {
        CategoryAlert categoryAlert = convertDtoToModel(categoryAlertDTO);
        CategoryAlert newCategoryAlert = categoryAlertService.save(userId, categoryAlert);
        categoryAlertDTO.setId(newCategoryAlert.getId());
        return new ResponseEntity<>(categoryAlertDTO, HttpStatus.CREATED);
    }

    @GetMapping(URL_BASE)
    public ResponseEntity<List<CategoryAlert>> getCategoryAlertsFromUser(@PathVariable(USER_ID) Long userId) {
        List<CategoryAlert> categoryAlerts = categoryAlertService.getCategoryAlertsByUserId(userId);
        return ResponseEntity.ok(categoryAlerts);
    }

    @DeleteMapping(URL_DELETE_CAT_ALERT)
    public ResponseEntity deleteCategoryAlert(@PathVariable(USER_ID) Long userId, @PathVariable(CAT_ALERT_ID) Long catAlertId) {
        categoryAlertService.removeById(userId, catAlertId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private CategoryAlert convertDtoToModel(CategoryAlertDTO categoryAlertDTO) {
        CategoryAlert categoryAlert = new CategoryAlert();
        categoryAlert.setCategory(Category.findByName(categoryAlertDTO.getCategory()));
        categoryAlert.setPercentage(categoryAlertDTO.getPercentage());
        return categoryAlert;
    }
}
