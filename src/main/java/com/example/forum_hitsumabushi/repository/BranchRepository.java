package com.example.forum_hitsumabushi.repository;

import com.example.forum_hitsumabushi.repository.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
}
