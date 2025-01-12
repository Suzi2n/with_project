package com.example.with_project.repository;

import com.example.with_project.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity,Long>
{
   // boolean existsByEmail(String email);
  //  UserEntity findByEmail(String email);    // email로 사용자 정보 가져옴
   Optional<UserEntity> findByEmail(String email); // 이메일로 사용자 정보를 가져옴
}