package com.assetmanagement.controller;

import com.assetmanagement.model.AssetRequest;
import com.assetmanagement.service.AssetRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class AssetRequestController {
    private final AssetRequestService assetRequestService;

    // GET all requests - NEW METHOD
    @GetMapping
    public ResponseEntity<List<AssetRequest>> getAllRequests() {
        return ResponseEntity.ok(assetRequestService.getAllRequests());
    }

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<AssetRequest> createRequest(@RequestBody AssetRequest request,
                                                      @RequestParam Long employeeId) {
        return ResponseEntity.ok(assetRequestService.createRequest(request, employeeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetRequest> getRequest(@PathVariable Long id) {
        return ResponseEntity.ok(assetRequestService.getRequestById(id));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AssetRequest>> getRequestsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(assetRequestService.getRequestsByEmployee(employeeId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AssetRequest>> getRequestsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(assetRequestService.getRequestsByStatus(
                com.assetmanagement.enums.RequestStatus.valueOf(status)
        ));
    }

    @PutMapping("/{requestId}/manager-approve")
    @PreAuthorize("hasAnyRole('ASSET_MANAGER', 'ADMIN')")
    public ResponseEntity<AssetRequest> approveByManager(@PathVariable Long requestId,
                                                         @RequestParam Long managerId,
                                                         @RequestParam String comments) {
        return ResponseEntity.ok(assetRequestService.approveByManager(requestId, managerId, comments));
    }

    @PutMapping("/{requestId}/it-review")
    @PreAuthorize("hasAnyRole('IT_SUPPORT', 'ADMIN')")
    public ResponseEntity<AssetRequest> reviewByIT(@PathVariable Long requestId,
                                                   @RequestParam Long itSupportId,
                                                   @RequestParam String comments) {
        return ResponseEntity.ok(assetRequestService.reviewByIT(requestId, itSupportId, comments));
    }

    @PutMapping("/{requestId}/approve")
    @PreAuthorize("hasAnyRole('IT_SUPPORT', 'ASSET_MANAGER', 'ADMIN')")
    public ResponseEntity<AssetRequest> approveRequest(@PathVariable Long requestId,
                                                       @RequestParam Long assetId) {
        return ResponseEntity.ok(assetRequestService.approveRequest(requestId, assetId));
    }

    @PutMapping("/{requestId}/issue")
    @PreAuthorize("hasAnyRole('IT_SUPPORT', 'ASSET_MANAGER', 'ADMIN')")
    public ResponseEntity<AssetRequest> issueAsset(@PathVariable Long requestId) {
        return ResponseEntity.ok(assetRequestService.issueAsset(requestId));
    }
}