package com.assetmanagement.repository;

import com.assetmanagement.model.AssetRequest;
import com.assetmanagement.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRequestRepository extends JpaRepository<AssetRequest, Long> {
    List<AssetRequest> findByEmployeeId(Long employeeId);
    List<AssetRequest> findByStatus(RequestStatus status);
    List<AssetRequest> findByEmployeeIdAndStatus(Long employeeId, RequestStatus status);

    // Note: findAll() is already provided by JpaRepository
    // No need to add it explicitly
}