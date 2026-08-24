// repository/AssetRepository.java
package com.assetmanagement.repository;

import com.assetmanagement.model.Asset;
import com.assetmanagement.enums.AssetStatus;
import com.assetmanagement.enums.AssetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByStatus(AssetStatus status);
    List<Asset> findByType(AssetType type);
    List<Asset> findByCurrentOwnerId(Long userId);
    Optional<Asset> findByAssetTag(String assetTag);

    @Query("SELECT a FROM Asset a WHERE a.warrantyExpiryDate BETWEEN :start AND :end")
    List<Asset> findAssetsWithWarrantyExpiringBetween(LocalDate start, LocalDate end);

    @Query("SELECT a FROM Asset a WHERE a.currentOwner IS NULL AND a.status = 'AVAILABLE'")
    List<Asset> findAvailableAssets();

    @Query("SELECT a FROM Asset a WHERE a.status = 'UNDER_REPAIR'")
    List<Asset> findAssetsUnderRepair();
}