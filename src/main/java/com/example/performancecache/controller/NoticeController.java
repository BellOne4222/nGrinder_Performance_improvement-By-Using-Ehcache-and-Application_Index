package com.example.performancecache.controller;

import com.example.performancecache.dto.Notice;
import com.example.performancecache.service.NoticeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {
    private NoticeService noticeService; // NoticeService 필드

    public NoticeController(NoticeService noticeService){
        this.noticeService=noticeService;
    } // NoticeService를 주입받는 생성자

    @GetMapping("")
    public ResponseEntity<Object> findAll() {
        List<Notice> notices = noticeService.getAllNotices(); // 모든 공지사항을 가져옴
        return new ResponseEntity<>(notices, HttpStatus.OK); // 공지사항 반환
    }

    @GetMapping("/{page}") // GET 요청을 처리하는 메서드
    public ResponseEntity<Object> findByPage(HttpServletRequest request, @PathVariable("page") Integer page) { // 페이지 번호를 받아서 공지사항을 가져오는 메서드
        List<Notice> notices = noticeService.findByPage(request, page); // 페이지 번호에 해당하는 공지사항을 가져옴
        return new ResponseEntity<>(notices, HttpStatus.OK); // 공지사항 반환
    }

    @GetMapping("/dates") // GET 요청을 처리하는 메서드
    public ResponseEntity<Object> findNoticesByDates(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) { // 시작일과 종료일을 받아서 해당 기간의 공지사항을 가져오는 메서드
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // 날짜 포맷터 생성
        List<Notice> notices = noticeService.findNoticesByDates( // 시작일과 종료일에 해당하는 공지사항을 가져옴
                LocalDateTime.parse(startDate, formatter), // 시작일을 파싱
                LocalDateTime.parse(endDate, formatter) // 종료일을 파싱
        );
        return new ResponseEntity<>(notices, HttpStatus.OK); // 공지사항 반환
    }
}
