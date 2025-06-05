# 🏋️ 종합 운동 플랫폼

## 내용
- [소개](#소개)
- [기능](#기능)
- [설치방법](#설치방법)
- [개발환경](#개발환경)
- [사용버전](#사용버전)
- [기술스택](#기술스택)
- [기타](#기타)

---

### 소개
이 프로젝트는 사용자의 운동량, 식단, 칼로리 등을 기록하고 분석할 수 있는 **종합 운동 플랫폼**입니다.

---

### 기능

- ✔️ **사용자 로그인 및 회원가입**
    - 회원 가입 (Bcrypt + RSA) - `2025-04-07`
    - 로그인 (JWT + HttpOnly-Cookie) - `2025-04-07`
    - 로그아웃 시 토큰 블랙리스트 등록 (Redis) - `2025-04-15`

- ⏳ **커뮤니티(게시글)**
    - 게시글 생성 시 첨부파일 업로드 - `2025-04-28`
    - 게시글 상세 페이지에서 첨부파일 다운로드 - `2025-04-28`
    - 게시글 수정 시 첨부파일 목록 수정 - `2025-05-06`
    - 게시글 목록 페이징 처리 `2025-06-02`
    - 게시글 카테고리 세분화 `구현중`

- ✔️ **마이페이지**
    - 마이페이지에서 회원정보 조회 - `2025-05-10`
    - 패스워드 변경 기능 - `2025-05-10`

- ✔️ **식단 기록**
    - 식품안전나라 API 연동하여 원하는 식품 데이터 응답 `2025-05-18`
    - 음식 조회 후 DB 저장 `2025-05-23`
    - 금주 전체 식단 조회 `2025-05-23`
    - 과거 식단 조회 `2025-06-01`
    - 식사 상세 정보 조회, 수정, 삭제 API 구현 `2025-06-06`

---

### 설치방법

1. 저장소 클론
    ```bash
    git clone https://github.com/jungdaehun1024/workoutCom-back.git
    ```

2. 의존성 빌드
    ```bash
    ./gradlew build       # Unix/macOS/Linux 용  
    gradlew.bat build     # Windows(cmd) 용
    ```

3. `application.yml` 설정
    - `src/main/resources/application.yml` 파일 참고

4. Redis 설치
    - [Redis 설치 가이드 보기(Window)](https://ittrue.tistory.com/318#google_vignette)
    - [Redis 설치 가이드 보기(Linux)](http://chanhan.tistory.com/entry/Linux-Ubuntu%EC%97%90-Redis-%EC%84%A4%EC%B9%98%ED%95%98%EA%B8%B0)

5. 실행

---

### 개발환경

- **IDE**: IntelliJ IDEA (Ultimate)
- **OS**: Windows 10
- **빌드 툴**: Gradle

---

### 사용버전

- **Java**: 17
- **Spring Boot**: 3.3.1
- **Spring Security**: `org.springframework.boot:spring-boot-starter-security:3.3.1`
- **MyBatis**: `org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.4`
- **Lombok**: `org.projectlombok:lombok:1.18.32`
- **JWT**: `io.jsonwebtoken:jjwt-api:0.11.5`
- **Redis**: `org.springframework.boot:spring-boot-starter-data-redis:3.3.1`
- **MySQL**: `5.7.29`

---

### 기술스택

#### **Backend**  
  - **프레임워크**  
    - Spring Boot  
  - **데이터 접근 계층**  
    - MyBatis
  - **보안/인증**
    - Spring Security
    - JWT
    - RSA
  - **데이터베이스**
    - MySQL
  - **세션/캐시**
    - Redis(In-memory DB)
#### **API 문서화**
  - Swagger (API 명세 자동화)

---

### 기타

- **ERD Cloud**: [ERD 보기](https://www.erdcloud.com/d/5yYvfZ5evYZvaeNHy)
