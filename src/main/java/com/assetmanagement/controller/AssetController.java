// controller/AssetController.java
package com.assetmanagement.controller;

import com.assetmanagement.model.Asset;
import com.assetmanagement.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/assets")
@RequiredArgsConstructor
public class AssetController {
    private final AssetService assetService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ASSET_MANAGER', 'ADMIN')")
    public ResponseEntity<Asset> createAsset(@RequestBody Asset asset) {
        return ResponseEntity.ok(assetService.createAsset(asset));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asset> getAsset(@PathVariable Long id) {
        return ResponseEntity.ok(assetService.getAssetById(id));
    }

    @GetMapping
    public ResponseEntity<List<Asset>> getAllAssets() {
        return ResponseEntity.ok(assetService.getAllAssets());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Asset>> getAvailableAssets() {
        return ResponseEntity.ok(assetService.getAvailableAssets());
    }

    @GetMapping("/under-repair")
    public ResponseEntity<List<Asset>> getAssetsUnderRepair() {
        return ResponseEntity.ok(assetService.getAssetsUnderRepair());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ASSET_MANAGER', 'ADMIN')")
    public ResponseEntity<Asset> updateAsset(@PathVariable Long id, @RequestBody Asset asset) {
        return ResponseEntity.ok(assetService.updateAsset(id, asset));
    }

    @PostMapping("/{assetId}/assign/{userId}")
    @PreAuthorize("hasAnyRole('IT_SUPPORT', 'ASSET_MANAGER', 'ADMIN')")
    public ResponseEntity<Asset> assignAsset(@PathVariable Long assetId, @PathVariable Long userId) {
        return ResponseEntity.ok(assetService.assignAsset(assetId, userId, LocalDate.now().plusMonths(12).atStartOfDay()));
    }

    @PostMapping("/{assetId}/return")
    @PreAuthorize("hasAnyRole('IT_SUPPORT', 'ASSET_MANAGER', 'ADMIN')")
    public ResponseEntity<Asset> returnAsset(@PathVariable Long assetId) {
        return ResponseEntity.ok(assetService.returnAsset(assetId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return ResponseEntity.ok().build();
    }
}