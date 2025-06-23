# 유레카 종합 프로젝트 - Yoajung admin

## 프로젝트 개요
- 요아정 관리자 시스템은 서비스 운영자가 요금제, 상품, 혜택, 사용자 리뷰 등의 데이터를 손쉽게 관리할 수 있도록 설계된 백오피스 서버입니다. 
- 사용자의 요금제 이용 현황 및 챗봇 통계 데이터를 열람할 수 있으며, 수집된 리뷰를 요약해주는 기능도 제공합니다.

## 아키텍처
![architecture](https://github.com/user-attachments/assets/a6df5444-141e-4ceb-9e9c-698a8d48ebf0)

## 팀원 소개
| 이름  | 역할      | 주요 구현 내용                          | GitHub                                             |
|-----|---------|-----------------------------------|----------------------------------------------------|
| 이재윤 | 백엔드, AI | 요금제 혜택 상품 비즈니스 로직, 요금제 및 챗봇 배치 작업 | <a href="https://github.com/iju42829"><img src="https://avatars.githubusercontent.com/u/116072376?v=4" width="100" height="100" alt="iju42829" /></a>         |
| 박소연 | 백엔드     | 리뷰 비즈니스 로직 구현                     | <a href="https://github.com/so-yeon1"><img src="https://avatars.githubusercontent.com/u/82212460?v=4" width="100" height="100" alt="so-yeon1" /></a>        |
| 신혜원 | 백엔드     | 이미지 관리 기능                         | <a href="https://github.com/hyew0nn"><img src="https://avatars.githubusercontent.com/u/113279618?v=4" width="100" height="100" alt="hyew0nn" /></a>               |
| 홍석준 | 백엔드, AI | 인증/인가, 리뷰 요약                      | <a href="https://github.com/seokjuun"><img src="https://avatars.githubusercontent.com/u/45346977?v=4" width="100" height="100" alt="seokjuun" /></a>               |

## 주요 기능
| 기능 | 설명 |
|----|------|
| 인증 / 인가 | 관리자 로그인 및 권한 기반 접근 제어 기능 제공 |
| 요금제 | 요금제 등록, 수정, 삭제, 조회 기능 제공 |
| 혜택 및 상품 | 혜택 및 상품 관리 기능 제공<br/>S3 기반 이미지 업로드 지원 |
| 통계 | 요금제 및 챗봇 통계 데이터 수집<br/>OpenAI 기반 요약 제공 |
| 리뷰 | 배치 및 Dify를 통한 리뷰 요약 기능 제공 |

## 실행 화면

### 요금제
| 설명 | 이미지 |
|------|--------|
| 요금제 조회  | <img src="https://github.com/user-attachments/assets/01b07076-5b0c-4eed-af66-4a85af62f920" width="600"/> |
| 요금제 등록 | <img src="https://github.com/user-attachments/assets/cd6dd71a-be4f-4ae2-807e-e3797e3992c3" width="600"/> |

### 통계
| 설명 | 이미지 |
|------|--------|
| 연령별 통계  | <img src="https://github.com/user-attachments/assets/3400a5c0-12b8-4e34-8306-b7907fb2516f" width="600"/> |
| 챗봇 통계 | <img src="https://github.com/user-attachments/assets/f42de7a5-7844-4323-91e1-e07225c4a4f7" width="600"/> |

