-- 유저 생성 (이미 있으면 생략)
CREATE USER aiproject WITH PASSWORD 'aiproject';

-- DB 접속 권한
GRANT CONNECT ON DATABASE ai_projectdb TO aiproject;

-- 스키마 사용 권한
GRANT USAGE ON SCHEMA public TO aiproject;

-- 기존 테이블에 대한 권한
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO aiproject;

-- 향후 새 테이블에 대한 권한도 자동 부여
ALTER DEFAULT PRIVILEGES IN SCHEMA public
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO aiproject;
