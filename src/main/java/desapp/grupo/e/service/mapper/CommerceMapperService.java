package desapp.grupo.e.service.mapper;

import desapp.grupo.e.model.dto.commerce.CommerceDTO;
import desapp.grupo.e.model.dto.commerce.CommerceHourDTO;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.model.user.CommerceHour;

import java.util.List;
import java.util.stream.Collectors;

public class CommerceMapperService {

    public Commerce convertDtoToModel(CommerceDTO commerceDTO) {
        Commerce commerce = new Commerce();
        commerce.setName(commerceDTO.getName());
        commerce.setAddress(commerceDTO.getAddress());
        commerce.setPhone(commerceDTO.getPhone());
        commerce.setLatitude(commerceDTO.getLatitude());
        commerce.setLongitude(commerceDTO.getLongitude());
        commerce.setSectors(commerceDTO.getSectors());
        commerce.setDoDelivery(commerceDTO.getDoDelivery());
        commerce.setDeliveryUp(commerceDTO.getDeliveryUp());
        List<CommerceHour> hours = commerceDTO.getHours().stream()
                .map(this::convertHourDtoToModel)
                .collect(Collectors.toList());
        commerce.setHours(hours);
        return commerce;
    }

    private CommerceHour convertHourDtoToModel(CommerceHourDTO commerceHourDTO) {
        CommerceHour commerceHour = new CommerceHour();
        commerceHour.setDay(commerceHourDTO.getDay());
        commerceHour.setOpenAt(commerceHourDTO.getOpenAt());
        commerceHour.setCloseAt(commerceHourDTO.getCloseAt());
        commerceHour.setOpenToday(commerceHourDTO.getOpenToday());
        return commerceHour;
    }
}
