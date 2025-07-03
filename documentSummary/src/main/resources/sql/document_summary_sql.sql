TRUNCATE document RESTART IDENTITY CASCADE;

INSERT INTO document (document_title, file_path, user_id)
VALUES
  ('자바 기초 강의', '/files/java_basics.pdf', 101),
  ('스프링 부트 입문', '/files/spring_boot_intro.pdf', 102),
  ('포스트그레스 완전 정복', '/files/postgresql_master.pdf', 103),
  ('리액트 실습', '/files/react_practice.pdf', 104),
  ('도커 사용법', '/files/docker_guide.pdf', 105),
  ('AWS 클라우드 소개', '/files/aws_cloud.pdf', 106),
  ('SQL 튜토리얼', '/files/sql_tutorial.pdf', 107),
  ('웹 보안 기초', '/files/web_security.pdf', 108),
  ('API 설계 가이드', '/files/api_design.pdf', 109),
  ('알고리즘 문제풀이', '/files/algorithm_solutions.pdf', 110);

SELECT document_id, document_title FROM document ORDER BY document_id;

INSERT INTO summary (document_id, keyword, summary_content, section_reference)
VALUES
  (1, '자바', '자바는 객체지향 언어로 널리 사용됩니다.', '1장'),
  (2, '스프링 부트', '스프링 부트는 설정이 간편한 프레임워크입니다.', '소개'),
  (3, '포스트그레스', '포스트그레스는 고성능 오픈소스 DB입니다.', '1장'),
  (4, '리액트', '리액트는 UI 구성요소를 만드는 라이브러리입니다.', '개요'),
  (5, '도커', '도커는 컨테이너 기반 가상화 기술입니다.', '기본 개념'),
  (6, 'AWS', 'AWS는 클라우드 서비스 제공업체입니다.', '서비스 소개'),
  (7, 'SQL', 'SQL은 데이터베이스 쿼리 언어입니다.', '기본 문법'),
  (8, '웹 보안', '웹 보안은 해킹 방지를 위한 기술입니다.', '보안 기초'),
  (9, 'API', 'API는 애플리케이션 간 소통 방법입니다.', '설계 원칙'),
  (10, '알고리즘', '알고리즘은 문제 해결 절차입니다.', '연습 문제');

