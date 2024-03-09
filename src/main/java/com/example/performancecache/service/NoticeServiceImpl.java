package com.example.performancecache.service;

import com.example.performancecache.dto.Notice;
import com.example.performancecache.mapper.NoticeReadMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService{

    private NoticeReadMapper noticeReadMapper;

    public NoticeServiceImpl(NoticeReadMapper noticeReadMapper){
        this.noticeReadMapper = noticeReadMapper;
    }

    @Override // 캐시 설정
    // @Cacheable(value = "NoticeReadMapper.findAll") // 캐시 이름을 지정
    public List<Notice> getAllNotices() { // 캐시할 메서드
        return noticeReadMapper.findAll(); // 캐시할 메서드의 반환값
    }

    @Override
    // @Cacheable(value = "NoticeReadMapper.findByPage", key = "#request.requestURI + '-' + #pageNumber", condition = "#pageNumber <= 5") // 캐시 이름과 키를 지정하고, 캐시 조건을 지정
    public List<Notice> findByPage(HttpServletRequest request, int pageNumber) { // 캐시할 메서드
        int startIdx = (pageNumber - 1) * 10; // 페이지 번호에 해당하는 시작 인덱스 계산
        return noticeReadMapper.findByPage(startIdx); // 캐시할 메서드의 반환값
    }

    @Override
    public List<Notice> findNoticesByDates(LocalDateTime startDate, LocalDateTime endDate) { // 캐시할 메서드
        return noticeReadMapper.findNoticesByDates(startDate, endDate); // 캐시할 메서드의 반환값
    }
}
