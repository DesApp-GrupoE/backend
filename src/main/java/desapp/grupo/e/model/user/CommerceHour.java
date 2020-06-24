package desapp.grupo.e.model.user;

import javax.persistence.*;

@Entity
public class CommerceHour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq_id_commerce_hour")
    private Long id;
    @Column(nullable = false)
    private String day;
    @Column
    private String openAt;
    @Column
    private String closeAt;
    @Column(nullable = false)
    private Boolean openToday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
