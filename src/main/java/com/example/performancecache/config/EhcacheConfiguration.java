package com.example.performancecache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

@Configuration // 스프링 설정파일
@EnableCaching // 스프링 캐싱을 활성화
public class EhcacheConfiguration {

    @Bean // 캐시 매니저를 빈으로 등록
    @Primary // 동일한 타입의 빈이 여러개 있을 때, 이 빈을 우선적으로 사용하도록 설정
    public CacheManager cacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) { // EhCacheManagerFactoryBean을 주입받아 캐시 매니저를 생성
        return new EhCacheCacheManager(ehCacheManagerFactoryBean.getObject()); // EhCacheCacheManager를 생성하고 EhCacheManagerFactoryBean에서 생성한 캐시 매니저를 주입
    }

    @Bean // EhCacheManagerFactoryBean을 빈으로 등록
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() { // EhCacheManagerFactoryBean을 생성하는 빈
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean(); // EhCacheManagerFactoryBean 생성
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml")); // ehcache.xml 파일을 설정
        ehCacheManagerFactoryBean.setShared(true); // 여러 스프링 빈이 동일한 EhCacheManager 인스턴스를 공유할 수 있도록 설정
        return ehCacheManagerFactoryBean; // 생성한 EhCacheManagerFactoryBean 반환
    }

}
