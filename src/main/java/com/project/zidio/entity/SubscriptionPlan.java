//package com.project.zidio.entity;


//import jakarta.persistence.*;

//@Entity
//@Table(name="sucription_plans")
//public class SubscriptionPlan {

  //  @Id
   // @GeneratedValue(strategy=GenerationType.IDENTITY)
    //private Long id;
    // private String name;
 /*   private Double price;
    private String description;
    private Integer durationInDays;


    public SubscriptionPlan() {}

    public SubscriptionPlan(Long id,String name,String description,Double price,Integer durationInDays) {
        this.id=id;
        this.name=name;
        this.description=description;
        this.price=price;
        this.durationInDays=durationInDays;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(Integer durationInDays) {
        this.durationInDays = durationInDays;
    }

}
*/

package com.project.zidio.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the platform user
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    // STUDENT or RECRUITER (store user role here to simplify checks)
    @Column(nullable = false)
    private String role;

    // BASIC, PREMIUM (or any plan name you want)
    @Column(nullable = false)
    private String planType;

    // PENDING, PAID, EXPIRED
    @Column(nullable = false)
    private String paymentStatus;

    private LocalDateTime startDate;
    private LocalDateTime endDate;


}

