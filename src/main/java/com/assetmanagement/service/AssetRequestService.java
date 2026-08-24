package com.assetmanagement.service;

import com.assetmanagement.model.Asset;
import com.assetmanagement.model.AssetRequest;
import com.assetmanagement.model.User;
import com.assetmanagement.enums.RequestStatus;
import com.assetmanagement.repository.AssetRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetRequestService {
    private final AssetRequestRepository assetRequestRepository;
    private final UserService userService;
    private final AssetService assetService;

    // NEW METHOD - Get all requests
    public List<AssetRequest> getAllRequests() {
        return assetRequestRepository.findAll();
    }

    public AssetRequest createRequest(AssetRequest request, Long employeeId) {
        User employee = userService.getUserById(employeeId);
        request.setEmployee(employee);
        request.setStatus(RequestStatus.SUBMITTED);
        request.setSubmittedDate(LocalDateTime.now());
        return assetRequestRepository.save(request);
    }

    public AssetRequest getRequestById(Long id) {
        return assetRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
    }

    public List<AssetRequest> getRequestsByEmployee(Long employeeId) {
        return assetRequestRepository.findByEmployeeId(employeeId);
    }

    public List<AssetRequest> getRequestsByStatus(RequestStatus status) {
        return assetRequestRepository.findByStatus(status);
    }

    @Transactional
    public AssetRequest approveByManager(Long requestId, Long managerId, String comments) {
        AssetRequest request = getRequestById(requestId);
        User manager = userService.getUserById(managerId);

        if (request.getStatus() != RequestStatus.SUBMITTED) {
            throw new RuntimeException("Request can only be approved from SUBMITTED status");
        }

        request.setManager(manager);
        request.setManagerComments(comments);
        request.setStatus(RequestStatus.MANAGER_APPROVED);
        request.setManagerApprovalDate(LocalDateTime.now());

        return assetRequestRepository.save(request);
    }

    @Transactional
    public AssetRequest reviewByIT(Long requestId, Long itSupportId, String comments) {
        AssetRequest request = getRequestById(requestId);
        User itSupport = userService.getUserById(itSupportId);

        if (request.getStatus() != RequestStatus.MANAGER_APPROVED) {
            throw new RuntimeException("Request must be manager approved before IT review");
        }

        request.setItSupport(itSupport);
        request.setItComments(comments);
        request.setStatus(RequestStatus.IT_REVIEW);
        request.setItReviewDate(LocalDateTime.now());

        return assetRequestRepository.save(request);
    }

    @Transactional
    public AssetRequest approveRequest(Long requestId, Long assetId) {
        AssetRequest request = getRequestById(requestId);
        Asset asset = assetService.getAssetById(assetId);

        if (request.getStatus() != RequestStatus.IT_REVIEW) {
            throw new RuntimeException("Request must be in IT review status");
        }

        // Assign asset to employee
        Asset assignedAsset = assetService.assignAsset(assetId, request.getEmployee().getId(), LocalDateTime.now().plusMonths(12));

        request.setAssignedAsset(assignedAsset);
        request.setStatus(RequestStatus.APPROVED);
        request.setApprovedDate(LocalDateTime.now());

        return assetRequestRepository.save(request);
    }

    @Transactional
    public AssetRequest issueAsset(Long requestId) {
        AssetRequest request = getRequestById(requestId);

        if (request.getStatus() != RequestStatus.APPROVED) {
            throw new RuntimeException("Request must be approved before issuing");
        }

        request.setStatus(RequestStatus.ISSUED);
        request.setIssuedDate(LocalDateTime.now());

        return assetRequestRepository.save(request);
    }
}