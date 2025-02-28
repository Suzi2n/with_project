package com.example.with_project.controller;

import com.example.with_project.entity.Hotel;
import com.example.with_project.service.BlogService;
import com.example.with_project.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import com.example.with_project.entity.Room;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private BlogService hotelService; // 호텔(Article) 관련 서비스

    // 특정 호텔의 객실 목록 보기
    @GetMapping("/rooms")
    public String roomList(@RequestParam Long hotelId, Model model) {
        Hotel hotel = hotelService.findById(hotelId);
        List<Room> rooms = roomService.getRoomList(hotelId);
        model.addAttribute("hotel", hotel);
        model.addAttribute("rooms", rooms);
        return "room/roomList"; // 위에서 작성한 roomList.html       그냥 roomList 아님. templates 안의 room 폴더의 roomList.html
    }

    // 객실 등록 폼 보여주기
    @GetMapping("/new-room")
    public String newRoomForm(@RequestParam Long hotelId, Model model) {
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("room", new Room());
        return "room/newRoom"; // 위에서 작성한 newRoom.html
    }


    // 객실 등록 처리
    @PostMapping("/new-room")
    public String createRoom(@ModelAttribute Room room, @RequestParam Long hotelId, Model model) {
        try {
            Hotel hotel = hotelService.findById(hotelId);
            room.setHotel(hotel);  // Room 엔티티의 hotel 필드에 Article 객체 설정
            roomService.save(room);
            return "redirect:/rooms?hotelId=" + hotelId;
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "해당 호텔 정보가 존재하지 않습니다. (ID: " + hotelId + ")");
            return "error";  // 에러 전용 뷰로 이동
        }
    }


    // [수정 처리] 객실 수정 처리 (폼 제출)
//    @PostMapping("/room/edit-room")
//    public String updateRoom(@ModelAttribute Room room, @RequestParam Long hotelId, Model model) {
//        try {
//            Article hotel = articleService.findById(hotelId);
//            room.setHotel(hotel); // 연관관계 유지
//            roomService.updateRoom(room);
//            return "redirect:/room/list?hotelId=" + hotelId;
//        } catch (IllegalArgumentException e) {
//            model.addAttribute("errorMessage", "객실 수정 중 오류가 발생했습니다: " + e.getMessage());
//            return "error";
//        }
//    }

    /// 객실 수정 폼 보여주기
    @GetMapping("/room/edit-room")
    public String editRoomForm(@RequestParam Long roomId, @RequestParam Long hotelId, Model model) {
        Hotel hotel = hotelService.findById(hotelId);
        Room room = roomService.findByRoomId(roomId)
                .orElseThrow(() -> new IllegalArgumentException("객실을 찾을 수 없습니다. (ID: " + roomId + ")"));
        model.addAttribute("hotel", hotel);
        model.addAttribute("room", room);
        return "room/edit-room"; // templates/room/editRoom.html
    }


    // 객실 등록 처리
    @PostMapping("/room/edit-room")
    public String updateRoom(@ModelAttribute Room room, @RequestParam Long hotelId, Model model) {
        try {
            Hotel hotel = hotelService.findById(hotelId);
            room.setHotel(hotel);
            roomService.updateRoom(room);
            return "redirect:/rooms?hotelId=" + hotelId;
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "객실 수정 중 오류가 발생했습니다: " + e.getMessage());
            return "error";
        }
    }





//    // 객실 삭제
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
//        roomService.deleteRoom(id);
//        return ResponseEntity.ok().build();
//    }

    // 객실 삭제 처리 (폼이나 링크를 통해 삭제 요청)
    @PostMapping("/delete-room")
    public String deleteRoom(@RequestParam Long roomId, @RequestParam Long hotelId, Model model) {
        try {
            roomService.deleteRoom(roomId);
            return "redirect:/rooms?hotelId=" + hotelId;  // 삭제 후 객실 목록으로 일관된 리다이렉트
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "객실 삭제 중 오류가 발생했습니다: " + e.getMessage());
            return "error";
        }
    }
}
