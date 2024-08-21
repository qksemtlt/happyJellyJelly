# 반려견 유치원 관리 시스템 - Happy Jelly

![Happy Jelly Logo](path_to_logo.png)

## 목차
1. [프로젝트 소개](#프로젝트-소개)
2. [기술 스택](#기술-스택)
3. [주요 기능](#주요-기능)
4. [데이터베이스 설계](#데이터베이스-설계)
5. [설치 및 실행 방법](#설치-및-실행-방법)
6. [팀 멤버](#팀-멤버)
7. [라이선스](#라이선스)

## 프로젝트 소개
Happy Jelly는 반려견 유치원을 위한 종합 관리 시스템입니다. 이 시스템은 유치원 운영에 필요한 다양한 기능을 제공하여 효율적인 관리와 고객 서비스 향상을 목표로 합니다.

## 기술 스택
- 데이터베이스: Oracle Database
- 개발 도구: SQL Developer
- 백엔드: Java (또는 사용한 백엔드 기술)
- 프론트엔드: (사용한 프론트엔드 기술, 예: HTML, CSS, JavaScript)

## 주요 기능
- 회원 관리: 고객 및 직원 정보 관리
- 강아지 관리: 반려견 정보 및 건강 기록 관리
- 수업 관리: 수업 일정 및 참석 관리
- 결제 관리: 수강료 결제 및 정산 관리
- 보고서 생성: 일일/주간/월간 활동 보고서 생성

## 데이터베이스 설계
### ER 다이어그램
![ER Diagram](path_to_er_diagram.png)

### 주요 테이블
1. MEMBERS: 회원 정보 저장
2. DOGS: 강아지 정보 저장
3. CLASSES: 수업 정보 저장
4. ATTENDANCE: 출석 정보 저장
5. PAYMENTS: 결제 정보 저장

### 샘플 쿼리
```sql
-- 회원 정보 조회
SELECT * FROM MEMBERS WHERE member_id = 1;

-- 특정 강아지의 출석 기록 조회
SELECT * FROM ATTENDANCE 
WHERE dog_id = 1 
ORDER BY attendance_date DESC;
