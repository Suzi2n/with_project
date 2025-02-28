package com.example.with_project.repository;

import com.example.with_project.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByAddressContaining(String address);
}