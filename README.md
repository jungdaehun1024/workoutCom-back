# 종합 운동 플랫폼

## 내용 
- [소개](#소개)  
- [기능](#기능)  
- [설치방법](#설치방법)  
- [사용버전](#사용버전)  
- [기술스택](#기술스택)  
- [기타](#기타)

### 소개 
이 프로젝트는 사용자의 운동량, 식단, 칼로리 등을 기록하고 분석할 수 있는 플랫폼입니다.

### 기능

- ✔️ **사용자 로그인 및 회원가입**
  - 회원 가입 (Bcrpy + RSA) (2025-04-07)
  - 로그인 (JWT + HttpOnly-Cookie) (2025-04-07)
  - 로그아웃 토큰 블랙리스트 등록 (2025-04-15)

- ✔️ **게시글의 CRUD**
  - 게시글 생성 시 첨부파일 업로드 (2024-04-28)
  - 게시글 상세 페이지에서 첨부파일 다운로드 (2025-04-28)
  - 게시글 수정 페이지에서 첨부파일 목록 수정 (2025-05-06)

- ⏳ **마이페이지**
  - 마이페이지에서 회원 정보 수정 (구현중)

- ⏳ **식단 기록**
  - 식품안전나라 API 연동하여 식단 기록 (구현중)

### 설치방법

1. 저장소 클론
    ```bash
    https://github.com/jungdaehun1024/workoutCom-back.git
    ```
2. 필요한 의존성 Build
    ```bash
    ./gradlew build  # 유닉스/리눅스/macOS 용
    gradlew.bat build # 윈도우 용 (cmd에서 실행)
    ```
3. `application.yml`
    - `src/main/resources/application.yml` 참고
4. Redis 설치  
    [Redis 설치 주소](https://ittrue.tistory.com/318#google_vignette)
5. 실행

### 사용버전
- **Java**: 17  
- **SpringBoot**: 3.3.1  
- **Spring Security**: org.springframework.boot:spring-boot-starter-security -> 3.3.1  
- **MyBatis**: org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.4  
- **Lombok**: org.projectlombok:lombok -> 1.18.32  
- **JWT**: 
    - io.jsonwebtoken:jjwt-api:0.11.5  
    - io.jsonwebtoken:jjwt-impl:0.11.5  
    - io.jsonwebtoken:jjwt-jackson:0.11.5  
- **Redis**: org.springframework.boot:spring-boot-starter-data-redis -> 3.3.1  
- **MySQL**: 5.7.29

### 기술스택
- **Backend**: Spring Boot, MyBatis, Swagger  
- **DB**: MySQL  

### 기타
- **ERD Cloud**: [ERD Cloud 링크](https://www.erdcloud.com/d/5yYvfZ5evYZvaeNHy) 
