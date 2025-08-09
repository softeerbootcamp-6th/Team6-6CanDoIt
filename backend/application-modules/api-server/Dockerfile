# application-modules/api-server/Dockerfile
# ── 1) 빌드 스테이지 ────────────────────────────
FROM gradle:8.6-jdk17 AS builder
WORKDIR /workspace

# 1-A) Gradle 설정 복사
COPY ../../settings.gradle ../../build.gradle ../../gradlew ./
COPY ../../gradle gradle
RUN chmod +x gradlew
RUN echo "=== Step 1-A: API Server - Gradle wrapper setup ===" && ls -la

# 1-B) 전체 소스 복사
COPY ../../ .
RUN echo "=== Step 1-B: API Server - Full source copied ===" && ls -la
RUN echo "=== Application modules structure ===" && ls -la application-modules/

# 1-C) 의존성 다운로드
RUN echo "=== Step 1-C: API Server - Downloading dependencies ==="
RUN ./gradlew --no-daemon :application-modules:api-server:dependencies

# 1-D) 부트 JAR 빌드
RUN echo "=== Step 1-D: API Server - Building bootJar ==="
RUN ./gradlew --no-daemon :application-modules:api-server:bootJar
RUN echo "=== Step 1-D: API Server - Build completed ===" && find . -name "*.jar" -type f

# ── 2) 런타임 스테이지 ──────────────────────────────────────────────
FROM eclipse-temurin:17-jre
WORKDIR /app

# 애플리케이션 JAR 복사
COPY --from=builder /workspace/application-modules/api-server/build/libs/*.jar app.jar

# 환경변수 설정
ENV SERVER_PORT=8080
ENV SPRING_PROFILES_ACTIVE=docker

# 포트 노출
EXPOSE 8080

# 헬스체크 (선택사항)
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
