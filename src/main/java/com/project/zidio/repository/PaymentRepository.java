package com.project.zidio.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.zidio.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>  {



}
