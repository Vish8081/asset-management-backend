package com.assetmanagement.service;

import com.assetmanagement.model.Asset;
import com.assetmanagement.model.User;
import com.assetmanagement.enums.AssetStatus;
import com.assetmanagement.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {
    private final AssetRepository assetRepository;
    private final UserService userService;

    public Asset createAsset(Asset asset) {
        if (assetRepository.findByAssetTag(asset.getAssetTag()).isPresent()) {
            throw new RuntimeException("Asset tag already exists");
        }
        return assetRepository.save(asset);
    }

    public Asset getAssetById(Long id) {
        return assetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public List<Asset> getAvailableAssets() {
        return assetRepository.findAvailableAssets();
    }

    public List<Asset> getAssetsUnderRepair() {
        return assetRepository.findAssetsUnderRepair();
    }

    public List<Asset> getAssetsByStatus(AssetStatus status) {
        return assetRepository.findByStatus(status);
    }

    @Transactional
    public Asset assignAsset(Long assetId, Long userId, LocalDateTime returnDate) {
        Asset asset = getAssetById(assetId);
        User user = userService.getUserById(userId);

        if (asset.getStatus() != AssetStatus.AVAILABLE) {
            throw new RuntimeException("Asset is not available for assignment");
        }

        asset.setCurrentOwner(user);
        asset.setStatus(AssetStatus.ASSIGNED);
        asset.setAssignedDate(LocalDateTime.now());
        asset.setReturnDate(returnDate);

        return assetRepository.save(asset);
    }

    @Transactional
    public Asset returnAsset(Long assetId) {
        Asset asset = getAssetById(assetId);

        if (asset.getStatus() != AssetStatus.ASSIGNED) {
            throw new RuntimeException("Asset is not currently assigned");
        }

        asset.setCurrentOwner(null);
        asset.setStatus(AssetStatus.AVAILABLE);
        asset.setReturnDate(LocalDateTime.now());

        return assetRepository.save(asset);
    }

    public Asset updateAsset(Long id, Asset assetDetails) {
        Asset asset = getAssetById(id);
        asset.setName(assetDetails.getName());
        asset.setDescription(assetDetails.getDescription());
        asset.setModel(assetDetails.getModel());
        asset.setManufacturer(assetDetails.getManufacturer());
        asset.setLocation(assetDetails.getLocation());
        asset.setUpdatedAt(LocalDateTime.now());
        return assetRepository.save(asset);
    }

    public void deleteAsset(Long id) {
        Asset asset = getAssetById(id);
        if (asset.getStatus() == AssetStatus.ASSIGNED) {
            throw new RuntimeException("Cannot delete assigned asset");
        }
        assetRepository.deleteById(id);
    }

    public List<Asset> getAssetsWithWarrantyExpiring(LocalDate start, LocalDate end) {
        return assetRepository.findAssetsWithWarrantyExpiringBetween(start, end);
    }
}