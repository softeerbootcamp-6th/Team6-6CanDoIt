# application-modules/batch-server/Dockerfile
# ── 1) 빌드 스테이지 ────────────────────────────
FROM gradle:8.6-jdk17 AS builder
WORKDIR /workspace

# 1-A) Gradle 설정 파일들만 먼저 복사 (캐시 최적화)
COPY ../../settings.gradle ../../build.gradle ../../gradlew ./
COPY ../../gradle gradle
RUN chmod +x gradlew
RUN echo "=== Step 1-A: Batch Server - Gradle wrapper setup ===" && ls -la

# 1-B) 모든 서브모듈의 build.gradle 파일들을 디렉토리 구조 유지하며 복사
COPY ../../application-modules/*/build.gradle* application-modules/
COPY ../../common-modules/*/build.gradle* common-modules/
COPY ../../domain-modules/*/build.gradle* domain-modules/
COPY ../../infrastructure-modules/*/build.gradle* infrastructure-modules/
RUN echo "=== Step 1-B: Batch Server - Build files copied ===" && find . -name "build.gradle*" -type f

# 1-C) 의존성 다운로드 (소스 코드 변경 시에도 캐시 유지)
RUN echo "=== Step 1-C: Batch Server - Downloading dependencies ==="
RUN ./gradlew --no-daemon :application-modules:batch-server:dependencies --refresh-dependencies
RUN echo "=== Step 1-C: Batch Server - Dependencies cached ==="

# 1-D) 이제 전체 소스 복사 (의존성 캐시 이후)
COPY ../../ .
RUN echo "=== Step 1-D: Batch Server - Full source copied ==="

# 1-E) 부트 JAR 빌드
RUN echo "=== Step 1-E: Batch Server - Building bootJar ==="
RUN ./gradlew --no-daemon :application-modules:batch-server:bootJar --no-build-cache
RUN echo "=== Step 1-E: Batch Server - Build completed ===" && find . -name "*.jar" -type f

# ── 2) 런타임 스테이지 ──────────────────────────────────────────────
FROM eclipse-temurin:17-jre
WORKDIR /app

# 애플리케이션 JAR 복사
COPY --from=builder /workspace/application-modules/batch-server/build/libs/*.jar app.jar

# 환경변수 설정 (배치 서버는 더 많은 메모리 할당)
ENV JVM_OPTS="-Xmx1024m -Xms512m -XX:+UseG1GC -XX:+UseStringDeduplication"

# 포트 노출
EXPOSE 8081

# 헬스체크 (배치 서버용 포트)
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8081/actuator/health || exit 1

# 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "java $JVM_OPTS -jar /app/app.jar"]