package com.Nong.treading.modal;

import com.Nong.treading.domain.WithdrawalStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Withdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private WithdrawalStatus status; // PENDING, SUCCESS

    private Long amount; // Amount to be withdrawn

    @ManyToOne
    private User user; // User who requested the withdrawal

    private LocalDateTime date = LocalDateTime.now();



}
