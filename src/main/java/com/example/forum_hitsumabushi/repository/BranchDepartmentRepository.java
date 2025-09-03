package com.example.forum_hitsumabushi.repository;

import com.example.forum_hitsumabushi.repository.entity.BranchDepartment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BranchDepartmentRepository extends JpaRepository<BranchDepartment, Integer> {
    @Query("""
        SELECT EXISTS (
            SELECT 1
            FROM BranchDepartment bd
            WHERE bd.branchId = :branchId
            AND bd.departmentId = :departmentId
        )
    """)
    boolean findBranchDepartment(@Param("branchId") int branchId, @Param("departmentId") int departmentId);
}
