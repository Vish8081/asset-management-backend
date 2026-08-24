// model/Asset.java
package com.assetmanagement.model;

import com.assetmanagement.enums.AssetStatus;
import com.assetmanagement.enums.AssetType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String assetTag;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetStatus status = AssetStatus.AVAILABLE;

    private String serialNumber;
    private String model;
    private String manufacturer;
    private LocalDate purchaseDate;
    private Double purchasePrice;
    private LocalDate warrantyExpiryDate;
    private String location;

    @ManyToOne
    @JoinColumn(name = "current_owner_id")
    private User currentOwner;

    @ManyToOne
    @JoinColumn(name = "assigned_by")
    private User assignedBy;

    private LocalDateTime assignedDate;
    private LocalDateTime returnDate;

    // Software specific fields
    private String licenseKey;
    private Integer maxUsers;
    private String version;

    private Boolean encryptionEnabled = false;
    private Boolean antivirusInstalled = false;
    private LocalDateTime lastAuditDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}