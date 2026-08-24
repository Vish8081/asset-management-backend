// repository/MaintenanceRecordRepository.java
package com.assetmanagement.repository;

import com.assetmanagement.model.MaintenanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, Long> {
    List<MaintenanceRecord> findByAssetId(Long assetId);
    List<MaintenanceRecord> findByStatus(String status);
}