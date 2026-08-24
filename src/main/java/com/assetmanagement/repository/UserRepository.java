package com.assetmanagement.repository;

import com.assetmanagement.enums.Role;
import com.assetmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findByDepartment(String department);

    List<User> findByRole(Role role);

    Boolean existsByEmail(String email);

    // NEW: Find users by department and role (optional)
    List<User> findByDepartmentAndRole(String department, Role role);

    // NEW: Find active users only
    List<User> findByIsActiveTrue();

    // NEW: Custom query to find users with specific role
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.isActive = true")
    List<User> findActiveUsersByRole(@Param("role") Role role);
}