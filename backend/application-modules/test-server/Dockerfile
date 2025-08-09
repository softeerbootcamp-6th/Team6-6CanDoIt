# ── 1) 빌드 스테이지 ────────────────────────────
FROM gradle:8.6-jdk17 AS builder
WORKDIR /workspace

# 1-A) 루트 gradle 설정 + wrapper
COPY ../../settings.gradle build.gradle gradlew ./
COPY ../../gradle gradle
RUN chmod +x gradlew
RUN echo "=== Step 1-A: Root gradle files copied ===" && ls -la

# 1-B) 모든 서브모듈의 build.gradle* 파일들을 디렉토리 구조 유지하며 복사
COPY application-modules/*/build.gradle* application-modules/
COPY common-modules/*/build.gradle* common-modules/
COPY domain-modules/*/build.gradle* domain-modules/
COPY infrastructure-modules/*/build.gradle* infrastructure-modules/
RUN echo "=== Step 1-B: Module build.gradle files copied ===" && ls -la
RUN echo "=== Application modules structure ===" && find application-modules -name "build.gradle*" -type f 2>/dev/null || echo "No application-modules found yet"

# 1-C) 의존성 캐싱 (이제 프로젝트 구조가 올바르게 설정됨)
RUN echo "=== Step 1-C: Before dependency resolution ==="
RUN ./gradlew --no-daemon :application-modules:test-server:dependencies
RUN echo "=== Step 1-C: Dependencies resolved ==="

# 1-D) 나머지 전체 소스
COPY ../.. .
RUN echo "=== Step 1-D: Full source copied ===" && ls -la
RUN echo "=== Application modules structure ===" && ls -la application-modules/

# 1-E) 부트 JAR 빌드
RUN echo "=== Step 1-E: Starting bootJar build ==="
RUN ./gradlew --no-daemon :application-modules:test-server:bootJar
RUN echo "=== Step 1-E: bootJar completed ===" && find . -name "*.jar" -type f

# ── 2) 런타임 스테이지 ──────────────────────────────────────────────
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /workspace/application-modules/test-server/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
