package com.example.with_project.service;


import com.example.with_project.entity.Room;
import com.example.with_project.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // 객실 등록
    public Room save(Room room) {
        // room 객체에 호텔 ID, 필수 필드 값들이 설정되었는지 확인 후 저장
        return roomRepository.save(room);
    }

    // 호텔 ID에 따른 객실 목록 조회 (삭제되지 않은 객실)
    public List<Room> getRoomList(Long hotelId) {
        return roomRepository.findByHotelIdAndRemoveDateIsNull(hotelId);
    }

    // 특정 객실 조회
    public Optional<Room> findByRoomId(Long id) {
        return roomRepository.findByIdAndRemoveDateIsNull(id);
    }

    // 객실 수정 (객실을 먼저 조회한 후, 필요한 값 수정 후 save 호출)
    public Room updateRoom(Room room) {
        // room 객체에 기존 id가 있고, 변경할 필드들이 set되어 있다면
        return roomRepository.save(room);
    }


    // 객실 삭제 (논리 삭제: removeDate에 현재 시각 저장)
    public void deleteRoom(Long id) {
        Optional<Room> optionalRoom = roomRepository.findByIdAndRemoveDateIsNull(id);
        if(optionalRoom.isPresent()){
            Room room = optionalRoom.get();
            room.setRemoveDate(LocalDateTime.now());
            roomRepository.save(room);
        }
    }
}
