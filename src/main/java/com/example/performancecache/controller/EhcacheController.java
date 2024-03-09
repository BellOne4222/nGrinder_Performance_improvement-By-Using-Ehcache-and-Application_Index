package com.example.performancecache.controller;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController // REST API를 처리하는 컨트롤러
@RequestMapping("/api") // 요청 URL을 매핑
public class EhcacheController { // EhcacheController 클래스
    private CacheManager cacheManager; // 캐시 매니저

    public EhcacheController(CacheManager cacheManager)
    {
        this.cacheManager = cacheManager;
    } // 캐시 매니저를 주입받는 생성자

    @GetMapping("/ehcache") // GET 요청을 처리하는 메서드
    public Object findAll(){ // 모든 캐시를 조회하는 메서드
        List<Map<String, List<String>>> result = cacheManager.getCacheNames().stream() // 캐시 매니저에 등록된 모든 캐시 이름을 스트림으로 변환
            .map(cacheName -> { // 캐시 이름을 매핑
                EhCacheCache cache = (EhCacheCache) cacheManager.getCache(cacheName); // 캐시 매니저에서 캐시 이름에 해당하는 캐시를 가져옴
                Ehcache ehcache = cache.getNativeCache(); // 캐시에서 네이티브 캐시를 가져옴
                Map<String, List<String>> entry = new HashMap<>(); // 캐시 이름과 캐시 키를 저장할 맵 생성

                ehcache.getKeys().forEach(key -> { // 캐시의 모든 키에 대해 반복
                    Element element = ehcache.get(key); // 키에 해당하는 엘리먼트를 가져옴
                    if (element != null) { // 엘리먼트가 null이 아니면
                        entry.computeIfAbsent(cacheName, k -> new ArrayList<>()).add(element.toString()); // 캐시 이름과 캐시 키를 맵에 저장
                    }
                });

                return entry; // 맵 반환
            })
            .collect(Collectors.toList()); // 스트림을 리스트로 변환

        return result; // 결과 반환
    }
}

