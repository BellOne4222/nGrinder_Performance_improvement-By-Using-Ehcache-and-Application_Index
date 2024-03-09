# Performance_improvement-By-Using-Ehcache-and-Application_Index Project
캐싱과 인덱싱을 활용한 성능 개선 프로젝트


## 🖥️ 프로젝트 소개
공지사항 API를 ehcache를 활용한 캐싱, 인덱스를 만들어서 인덱싱으로 쿼리의 성능을 전과 후를 비교하며 성능 향상 및 nGrinder를 활용하여 부하테스트를 진행한 API 성능 개선 프로젝트
<br>

### ⚙️ 개발 환경
- java 11
- `JDK 1.8.0`
- **IDE** : intellij
- **Framework** : spring boot 2.7.4
- **Database** : mysql
- **CACHE** : ehcache 2.x
- **BUILD** : Maven

## 📌 주요 기능
#### 모든 페이지 ehcache 캐싱
- Local Cache인 ehcache 를 활용하여 개선 
- 캐싱처리를 통해 TPS 수치 약 14배 증가
- 평균 응답시간은 18배 단축
- 평균 TPS : { 23.6 } → { 331.1 }
- Mean Test Time : { 436.09 } ms → { 24.00 }ms
- nGrinder를 활용하여 부하테스트

#### 랜덤 페이지 ehcache 캐싱
- 1-5페이지에만 캐싱 되도록 condition 옵션을 활용
- 랜덤으로 페이지 번호 호출해서 페이지 하나씩 호출(1~10 랜덤 페이지 호출) 할 때의 테스트
- 캐시 적용하기 전(@Cacheable(value = "NoticeReadMapper.findByPage", key = "#request.requestURI + '-' + #pageNumber", condition = "#pageNumber <= 5")
- 캐시 적용 후(@Cacheable(value = "NoticeReadMapper.findByPage", key = "#request.requestURI + '-' + #pageNumber", condition = "#pageNumber <= 5")
- 평균 TPS : { 1237.2 } → { 1608.0 } 약 1.3배 증가
- Mean Test Time : { 5.44 } ms → { 4.57 } ms 약 1.2배 단축

#### 인덱싱
- 기준 인덱스를 칼럼에 카디널리티 수를 기준으로 설정
- Mysql Profiling로 수치 확인
- 수행시간, CPU 사용량등
- ngrinder를 통해서 인덱스 적용 전과 후 비교
- 평균 TPS : { 13 } → { 18 } 약 1.4배 증가
- Mean Test Time : { 742 } ms → { 553 }ms 약 1.4배 단축
