// model/AssetRequest.java
package com.assetmanagement.model;

import com.assetmanagement.enums.AssetType;
import com.assetmanagement.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "asset_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToOne
    @JoinColumn(name = "it_support_id")
    private User itSupport;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetType assetType;

    private String description;
    private String justification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.SUBMITTED;

    @ManyToOne
    @JoinColumn(name = "assigned_asset_id")
    private Asset assignedAsset;

    private LocalDateTime submittedDate;
    private LocalDateTime managerApprovalDate;
    private LocalDateTime itReviewDate;
    private LocalDateTime approvedDate;
    private LocalDateTime issuedDate;
    private String managerComments;
    private String itComments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        submittedDate = LocalDateTime.now();
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}