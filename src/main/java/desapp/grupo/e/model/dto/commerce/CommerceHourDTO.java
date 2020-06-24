package desapp.grupo.e.model.dto.commerce;

import desapp.grupo.e.model.user.CommerceHour;

import javax.validation.constraints.NotNull;

public class CommerceHourDTO {

    @NotNull(message = "day.notNull")
    private String day;
    private String openAt;
    private String closeAt;
    @NotNull(message = "openToday.notNull")
    private Boolean openToday;

    public CommerceHourDTO() {
        // Constructor vacio para el mapping de jackson
    }

    public CommerceHourDTO(CommerceHour commerceHour) {
        this.day = commerceHour.getDay();
        this.openAt = commerceHour.getOpenAt();
        this.closeAt = commerceHour.getCloseAt();
        this.openToday = commerceHour.getOpenToday();
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getOpenAt() {
        return openAt;
    }

    public void setOpenAt(String openAt) {
        this.openAt = openAt;
    }

    public String getCloseAt() {
        return closeAt;
    }

    public void setCloseAt(String closeAt) {
        this.closeAt = closeAt;
    }

    public Boolean getOpenToday() {
        return openToday;
    }

    public void setOpenToday(Boolean openToday) {
        this.openToday = openToday;
    }
}
