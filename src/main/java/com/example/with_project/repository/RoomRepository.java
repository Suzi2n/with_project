package com.example.with_project.repository;

import com.example.with_project.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    //Spring Data JPA를 사용하면, Repository 인터페이스만 작성하면 기본 CRUD 기능이 자동으로 제공됩니다.


    // 호텔 ID에 해당하고 삭제되지 않은(Room.removeDate IS NULL) 객실 목록 조회
    List<Room> findByHotelIdAndRemoveDateIsNull(Long hotelId);

    // 특정 객실 조회 (삭제되지 않은 경우)
    Optional<Room> findByIdAndRemoveDateIsNull(Long id);
}
