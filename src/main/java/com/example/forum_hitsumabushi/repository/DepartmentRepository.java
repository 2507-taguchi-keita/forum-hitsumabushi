package com.example.forum_hitsumabushi.repository;

import com.example.forum_hitsumabushi.repository.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}