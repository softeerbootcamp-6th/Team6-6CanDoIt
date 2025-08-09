# application-modules/batch-server/Dockerfile
# ── 1) 빌드 스테이지 ────────────────────────────
FROM gradle:8.6-jdk17 AS builder
WORKDIR /workspace

# 1-A) Gradle 설정 복사
COPY ../../settings.gradle ../../build.gradle ../../gradlew ./
COPY ../../gradle gradle
RUN chmod +x gradlew
RUN echo "=== Step 1-A: Batch Server - Gradle wrapper setup ===" && ls -la

# 1-B) 전체 소스 복사
COPY ../../ .
RUN echo "=== Step 1-B: Batch Server - Full source copied ===" && ls -la
RUN echo "=== Application modules structure ===" && ls -la application-modules/

# 1-C) 의존성 다운로드
RUN echo "=== Step 1-C: Batch Server - Downloading dependencies ==="
RUN ./gradlew --no-daemon :application-modules:batch-server:dependencies

# 1-D) 부트 JAR 빌드
RUN echo "=== Step 1-D: Batch Server - Building bootJar ==="
RUN ./gradlew --no-daemon :application-modules:batch-server:bootJar
RUN echo "=== Step 1-D: Batch Server - Build completed ===" && find . -name "*.jar" -type f

# ── 2) 런타임 스테이지 ──────────────────────────────────────────────
FROM eclipse-temurin:17-jre
WORKDIR /app

# 애플리케이션 JAR 복사
COPY --from=builder /workspace/application-modules/batch-server/build/libs/*.jar app.jar

# 환경변수 설정
ENV SERVER_PORT=8080
ENV SPRING_PROFILES_ACTIVE=docker

# 포트 노출 (배치 서버도 관리 포트가 있을 수 있음)
EXPOSE 8080

# 배치 작업을 위한 메모리 설정
ENV JVM_OPTS="-Xmx1024m -Xms512m"

# 헬스체크 (선택사항)
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "java $JVM_OPTS -jar /app/app.jar"]
