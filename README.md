# 서비스 소개

<img width="1917" height="946" alt="스크린샷 2025-08-26 오전 1 35 51" src="https://github.com/user-attachments/assets/b912d85a-3da9-44a5-8b0f-2e808a6e0542" />
<img width="1901" height="946" alt="스크린샷 2025-08-26 오전 1 44 25" src="https://github.com/user-attachments/assets/52cd9ed1-baa4-4a47-9588-84bc8739437e" />

### 산악형 국립공원을 오르는 등산객들을 위한 통합 기상 정보 서비스, “오르는오늘”입니다.

#### 코스별 예상 소요 시간을 고려한 시간대별 요약 날씨 정보를 제공합니다.

#### 또한, 사용자가 등록하는 날씨 및 안전 제보를 통해서도 정보를 확인할 수 있습니다.

---




# 기술 스택
| FE | BE | 배포환경 |
| --- | --- | --- |
| <img src="https://img.shields.io/badge/React-61DAFB?style=flat&logo=react&logoColor=black" /> <img src="https://img.shields.io/badge/TypeScript-3178C6?style=flat&logo=typescript&logoColor=white" /> <img src="https://img.shields.io/badge/Vite-646CFF?style=flat&logo=vite&logoColor=white" /> <img src="https://img.shields.io/badge/TanStack%20Query-FF4154?style=flat&logo=tanstackquery&logoColor=white" /> <img src="https://img.shields.io/badge/Emotion%20CSS-DB7093?style=flat&logo=emotion&logoColor=white" /> | <img src="https://img.shields.io/badge/Java-007396?style=flat&logo=openjdk&logoColor=white" /> <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=springboot&logoColor=white" /> ![Spring Batch](https://img.shields.io/badge/Spring%20Batch-6DB33F?logo=spring&logoColor=white) <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white" /> ![Redis](https://img.shields.io/badge/Redis-DC382D?logo=redis&logoColor=white) ![Testcontainers](https://img.shields.io/badge/Testcontainers-1F6FEB?logo=testcontainers&logoColor=white) ![Bucket4j](https://img.shields.io/badge/Bucket4j-0F6EDE?logo=java&logoColor=white)| <img src="https://img.shields.io/badge/Amazon_EC2-FF9900?style=flat&logo=amazonec2&logoColor=white" /> <img src="https://img.shields.io/badge/GitHub_Actions-2088FF?style=flat&logo=githubactions&logoColor=white" /> <img src="https://img.shields.io/badge/Amazon_S3-569A31?style=flat&logo=amazons3&logoColor=white" /> <img src="https://img.shields.io/badge/CloudFront-FF4F8B?style=flat&logo=amazoncloudwatch&logoColor=white" /> <img src="https://img.shields.io/badge/Route_53-8C4FFF?style=flat&logo=amazonroute53&logoColor=white" /> <img src="https://img.shields.io/badge/CodeDeploy-6DB33F?style=flat&logo=amazonaws&logoColor=white" /> |
</br>

<details>
  <summary> Frontend 기술 스택 </summary>
  
  #### React v18
  - 가장 안정적인 버전
  #### Vite
  - 빠른 개발 서버 시작 및 간편한 설정이 가능
  #### TypeScript
  - 정적 타입 시스템 도입으로 컴파일 시점 오류 검출, 코드 가독성, 유지보수성 향상
  #### Tanstack Query
  - 서버 상태 데이터 fetch 로직을 간결하게 하며 자동 캐싱, 백그라운드 업데이트, 재요청 등의 기능을 통해 코드 중복을 줄이고 사용자 경험을 개선
  #### Emotion CSS
  - CSS‑in‑JS 방식을 통해 컴포넌트 단위 및 동적 스타일링을 효과적으로 지원
  #### Github Action
  - 코드 푸시 시 React 앱을 자동 빌드하고 S3에 업로드하며, CloudFront 캐시를 무효화해 자동 배포 CI/CD를 구현
  #### Amazon S3
  - 빌드된 정적 파일(html, js, css 등)을 저장하고 호스팅하는 오리진 스토리지로 사용되어 정적 웹사이트 기반을 제공
  #### Amazon CloudFront
  - HTTPS와 캐시 무효화를 제공하는 CDN 역할을 하며, S3 오리진을 글로벌 사용자에게 빠르게 전달
  #### Amazon Route 53
  - 커스텀 도메인의 DNS 레코드를 CloudFront 배포에 연결하여 도메인 기반으로 정적 웹사이트에 접근할 수 있게함
</details>


---

# 개발 일정
- **2025-07-30** ~ **2025-07-31** : 프로젝트 환경 논의 및 구성
- **2025-08-01** ~ **2025-08-13** : 디렉토리 구조 논의(Atomic 구조) 및 최종결정
- **2025-08-14** ~                : 개발 

---

# 팀원 소개

<table align="center">
  <tr>
    <td>
      <img width="200" height="auto" alt="박종성" src="https://github.com/user-attachments/assets/47aec288-4d43-41ee-9a43-01d706551a32" />
    </td>
    <td>
      <img width="200" height="auto" alt="시보성" src="https://github.com/user-attachments/assets/4c618167-3d66-41f4-bdf1-019d771b269d" />
    </td>
    <td>
      <img width="200" height="auto" alt="김명규" src="https://github.com/user-attachments/assets/b20aa9c0-8d38-41fb-88ec-57c3c9a9dca3" />
    </td>
    <td>
      <img width="200" height="auto" alt="백승진" src="https://github.com/user-attachments/assets/f51e727b-2279-4343-ab7d-6f2478d82e6b" />
    </td>
  </tr>
  <tr>
    <th><a href="https://github.com/ParkJongSeong93">박종성</a></th>
    <th><a href="https://github.com/bobob0311">시보성</a></th>
    <th><a href="https://github.com/Haemulramen">김명규</a></th>
    <th><a href="https://github.com/naver0504">백승진</a></th>
  </tr>
  <tr>
    <td colspan="2" align="center">🖥 Frontend Developer</td>
    <td colspan="2" align="center">🌐 Backend Engineer</td>
  </tr>
</table>

# 프로젝트 구조
<img width="2689" height="1140" alt="6candoit_project_architecture" src="https://github.com/user-attachments/assets/75ce15cd-d6d3-47e0-9f32-a023951ceae8" />

# 프로젝트 ERD
<img width="100%" height="auto" alt="erd (1)" src="https://github.com/user-attachments/assets/c46c79e8-235d-4b72-b401-83d8eb085a02" />

## 프로젝트 핵심 도메인
- 예보 도메인
  - 날씨 예보 데이터와 관련된 도메인
- 등산로 도메인
  - 산, 등산 코스와 관련된 도메인
- 사용자 도메인
  - 사용자 정보와 관련된 도메인
- 제보 도메인
  - 등산 코스에 대해서 사용자가 생성, 제공하는 도메인
# [GitHub Wiki](https://github.com/softeerbootcamp-6th/Team6-6CanDoIt/wiki)
  - [그라운드 룰](https://github.com/softeerbootcamp-6th/Team6-6CanDoIt/wiki/%EA%B7%B8%EB%9D%BC%EC%9A%B4%EB%93%9C-%EB%A3%B0)
### 일일회고
<details>
    <summary>1주차</summary>

  - [2025-08-04](daily_retrospectives_2025_08_04)
  - [2025-08-05](daily_retrospectives_2025_08_05)
  - [2025-08-06](daily_retrospectives_2025_08_06)
  - [2025-08-07](daily_retrospectives_2025_08_07)
  - [2025-08-08](daily_retrospectives_2025_08_08)
</details>

<details>
    <summary>2주차</summary>

  - [2025-08-11](daily_retrospectives_2025_08_11)
  - [2025-08-12](daily_retrospectives_2025_08_12)
  - [2025-08-13](daily_retrospectives_2025_08_13)
  - [2025-08-14](daily_retrospectives_2025_08_14)
</details>

<details>
    <summary>3주차</summary>

  - [2025-08-18](daily_retrospectives_2025_08_18)
  - [2025-08-19](daily_retrospectives_2025_08_19)
  - [2025-08-20](daily_retrospectives_2025_08_20)
  - [2025-08-21](daily_retrospectives_2025_08_21)
  - [2025-08-22](daily_retrospectives_2025_08_22)
</details>

### Convention
- [백엔드 이슈 컨벤션](https://github.com/softeerbootcamp-6th/Team6-6CanDoIt/wiki/GitHub-%EC%9D%B4%EC%8A%88-%EC%9E%91%EC%84%B1-%EC%BB%A8%EB%B2%A4%EC%85%98)
- [에러 컨벤션](https://github.com/softeerbootcamp-6th/Team6-6CanDoIt/wiki/%EC%97%90%EB%9F%AC-%EC%BD%94%EB%93%9C-%EC%BB%A8%EB%B2%A4%EC%85%98)
