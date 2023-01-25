/*PLAYER 테이블에서 TEAM_ID가 'K01'인 선수*/
SELECT * FROM PLAYER
WHERE TEAM_ID = 'K01';

/*PLAYER 테이블에서 TEAM_ID가 'K01'이 아닌 선수*/
SELECT * FROM PLAYER
WHERE TEAM_ID <> 'K01';

SELECT * FROM PLAYER
WHERE TEAM_ID != 'K01';

SELECT * FROM PLAYER
WHERE TEAM_ID ^= 'K01';

/*PLAYER 테이블에서 WEIGHT가 70이상이고 80이하인 선수*/
SELECT * FROM PLAYER
WHERE WEIGHT >= 70 AND WEIGHT <= 80;

SELECT * FROM PLAYER
WHERE WEIGHT BETWEEN 70 AND 80;

/*PLAYER 테이블에서 TEAM_ID가 'K03'이고 HEIGHT가 180미만인 선수*/
SELECT * FROM PLAYER
WHERE TEAM_ID = 'K03' AND HEIGHT < 180;

/*PLAYER 테이블에서 TEAM_ID가 'K06'이고 NICKNAME이 '제리'인 선수*/
SELECT * FROM PLAYER
WHERE TEAM_ID = 'K06' AND NICKNAME = '제리';

/*PLAYER 테이블에서 HEIGHT가 170이상이고 WEIGHT가 80이상인 선수 이름*/
SELECT * FROM PLAYER
WHERE HEIGHT >= 170 AND WEIGHT >= 80;

/*STADIUM 테이블에서 SEAT_COUNT가 30000 초과이고 41000이하인 경기장*/
SELECT * FROM STADIUM
WHERE SEAT_COUNT > 30000 AND SEAT_COUNT <= 41000;

/*PLAYER 테이블에서 TEAM_ID가 'K02'이거나 'K07'이고 포지션은 'MF'인 선수*/
SELECT * FROM PLAYER
WHERE (TEAM_ID = 'K02' OR TEAM_ID = 'K07') AND "POSITION" = 'MF';

SELECT * FROM PLAYER
WHERE TEAM_ID IN('K02', 'K07') AND "POSITION" = 'MF';

/*PLAYER 테이블에서 TEAM_ID가 'K01'인 선수 이름을 내 이름으로 바꾸기*/
SELECT PLAYER_NAME FROM PLAYER
WHERE TEAM_ID = 'K01';

UPDATE PLAYER
SET PLAYER_NAME = '한동석'
WHERE TEAM_ID = 'K01';

/*PLAYER 테이블에서 POSITION이 'MF'인 선수 삭제하기*/
SELECT * FROM PLAYER
WHERE "POSITION" = 'MF';

DELETE FROM PLAYER
WHERE "POSITION" = 'MF';

/*PLAYER 테이블에서 HEIGHT가 180이상인 선수 삭제하기*/
SELECT * FROM PLAYER
WHERE HEIGHT >= 180;

DELETE FROM PLAYER
WHERE HEIGHT >= 180;

ROLLBACK;

/*PLAYER 테이블에서 NICKNAME이 NULL인 선수 검색*/
SELECT PLAYER_NAME, NVL(NICKNAME, '없음') FROM PLAYER
WHERE NICKNAME IS NULL;

/*PLAYER 테이블에서 POSITION이 NULL인 선수 검색*/
SELECT * FROM PLAYER
WHERE "POSITION" IS NULL;

/*PLAYER 테이블에서 POSITION이 NULL인 선수를 '미정'으로 변경 후 검색*/
SELECT PLAYER_NAME, NVL("POSITION", '미정') FROM PLAYER;

/*PLAYER 테이블에서 NATION이 등록되어 있으면 '등록', 아니면 '미등록'으로 검색*/
SELECT PLAYER_NAME, NVL2(NATION, '등록', '미등록') FROM PLAYER;

/*AS(ALIAS): 별칭*/
/*SELECT절에서는 컬럼명 뒤에 띄어쓰고 작성하거나, AS 뒤에 작성한다.*/
SELECT PLAYER_NAME, NVL(NICKNAME, '없음') AS NICKNAME FROM PLAYER
WHERE NICKNAME IS NULL;

SELECT PLAYER_NAME, NVL("POSITION", '미정') "포지션 여부" FROM PLAYER;

/*선수 이름과 생일 조회*/
SELECT PLAYER_NAME "선수 이름", BIRTH_DATE "선수 생일" FROM PLAYER;

/*PLAYER 테이블에서 BACK_NO를 "등 번호"로, NICKNAME을 "선수 별명"으로 변경하여 검색*/
SELECT PLAYER_NAME, BACK_NO "등 번호", NICKNAME AS "선수 별명" FROM PLAYER;

/*위 결과에서 NICKNAME이 NULL일 경우 "없음"으로 변경*/
SELECT PLAYER_NAME, BACK_NO "등 번호", NVL(NICKNAME, '없음') AS "선수 별명" FROM PLAYER;

/*CONCATENATION: 연결, ||*/

/*PLAYER_NAME의 별명은 NICKNAME이다.*/
SELECT PLAYER_NAME || '의 별명은 ' || NICKNAME || '이다.' AS 자기소개 FROM PLAYER;

/*PLAYER_NAME의 영어이름은 E_PLAYER_NAME이다.*/
SELECT PLAYER_NAME || '의 영어이름은 ' || E_PLAYER_NAME || '이다.' 자기소개 FROM PLAYER;

/*PLAYER_NAME의 포지션은 POSITION입니다.*/
SELECT PLAYER_NAME || '의 포지션은 ' || NVL("POSITION", '미정') || '입니다.' "포지션 소개" FROM PLAYER;

/*LIKE : 포함된 문자열 값을 찾고, 문자의 개수도 제한을 줄 수 있다.*/
/*
 * [컬럼명] LIKE '';
 * 
 * %: 모든 것
 * _: 글자 수
 * 
 * 예) 
 * '%A'   : A로 끝나는 모든 값
 * 'A%'   : A로 시작하는 모든 값
 * '%A%': A가 포함된 모든 값
 * 'A__': A로 시작하고 3글자인 값   
 * '_A'   : A로 끝나고 2글자인 값
 * */

/*TEAM 테이블에서 '천마'로 끝나는 팀 이름 찾기*/
SELECT * FROM TEAM
WHERE TEAM_NAME LIKE '%천마';

/*PLAYER 테이블에서 김씨 찾기*/
SELECT * FROM PLAYER
WHERE PLAYER_NAME LIKE '김%';

/*PLAYER 테이블에서 김씨 두 자 찾기*/
SELECT * FROM PLAYER
WHERE PLAYER_NAME LIKE '김_';

/*PLAYER 테이블에서 김씨와 이씨 찾기*/
SELECT * FROM PLAYER
WHERE PLAYER_NAME LIKE '김%' OR PLAYER_NAME LIKE '이%'; 

/*PLAYER 테이블에서 이씨가 아닌 사람 찾기(NOT)*/
SELECT * FROM PLAYER
WHERE PLAYER_NAME NOT LIKE '이%';

SELECT * FROM PLAYER
WHERE NOT PLAYER_NAME LIKE '이%'; 

/*집계 함수: 결과는 무조건 1개*/
/*
 * ※ NULL은 포함시키지 않는다.
 * ※ WHERE절에서 사용 불가.
 * 
 * 
 * 평균   : AVG()
 * 최대값   : MAX()
 * 최소값   : MIN()
 * 총 합   : SUM()
 * 개수   : COUNT()
 * 
 * */

SELECT AVG(HEIGHT), MAX(HEIGHT), MIN(HEIGHT), SUM(HEIGHT), COUNT(HEIGHT) FROM PLAYER;

/*PLAYER 테이블에서 HEIGHT로 총 선수 명수 검색*/
SELECT COUNT(NVL(HEIGHT, 0)) "총 인원" FROM PLAYER;

/*정렬*/
/*
 * ORDER BY 컬럼명, ... ASC   : 오름 차순
 * ORDER BY 컬럼명, ... DESC   : 내림 차순
 * 
 * */

/*키 순 정렬*/
SELECT PLAYER_NAME, HEIGHT, WEIGHT FROM PLAYER
WHERE HEIGHT IS NOT NULL
ORDER BY HEIGHT DESC, WEIGHT;

/*
 * GROUP BY: ~별 (예: 포지션 별 평균 키)
 * 
 * GROUP BY 컬럼명 HAVING 조건식
 * ※ WHERE절에 우선적으로 처리할 조건식을 작성해야 속도가 빠르다.
 * ※ HAVING절에서는 집계함수 사용 가능
 * 
 * */
/*PLAYER 테이블에서 포지션 종류 검색*/
SELECT "POSITION", COUNT("POSITION") FROM PLAYER
WHERE "POSITION" IS NOT NULL
GROUP BY "POSITION";

/*PLAYER 테이블에서 몸무게가 80이상인 선수들의 평균 키가 180이상인 포지션 검색*/
SELECT "POSITION", MIN(WEIGHT), AVG(HEIGHT) FROM PLAYER
WHERE WEIGHT >= 80
GROUP BY "POSITION"
HAVING AVG(HEIGHT) >= 180;

/*EMPLOYEES 테이블에서 JOB_ID별 평균 SALARY가 10000미만인 JOB_ID 검색, JOB_ID 알파벳순 정렬*/
SELECT JOB_ID, AVG(SALARY) FROM EMPLOYEES
GROUP BY JOB_ID
HAVING AVG(SALARY) < 10000
ORDER BY JOB_ID;

/*
 * SUB QUERY
 * 
 * FROM절: IN LINE VIEW
 * SELECT절: SCALAR SUB QUERY
 * WHERE절: SUB QUERY
 * 
 * */

   SELECT * FROM PLAYER
   WHERE TEAM_ID = 'K01' AND "POSITION" = 'GK';

/*PLAYER 테이블에서 TEAM_ID가 'K01'인 선수 중 POSITION이 'GK'인 선수*/
SELECT * FROM
(
   SELECT * FROM PLAYER
   WHERE TEAM_ID = 'K01'
)
WHERE "POSITION" = 'GK';

/*정남일 선수가 소속된 팀의 선수들 조회*/
SELECT * FROM PLAYER
WHERE TEAM_ID = (SELECT TEAM_ID FROM PLAYER WHERE PLAYER_NAME = '정남일');

/*PLAYER 테이블에서 전체 평균 키와 포지션별 평균 키 구하기*/
SELECT "POSITION", AVG(HEIGHT) AS "포지션별 평균 키", (SELECT AVG(HEIGHT) FROM PLAYER) "전체 평균 키"
FROM PLAYER
WHERE "POSITION" IS NOT NULL
GROUP BY "POSITION";

/*경기장 중 경기 일정이 20120501~20120502 사이에 있는 경기장 전체 정보 조회*/
SELECT * FROM STADIUM
WHERE STADIUM_ID IN
(
   SELECT STADIUM_ID FROM SCHEDULE
   WHERE SCHE_DATE BETWEEN '20120501' AND '20120502'
);

/*EMPLOYEES 테이블에서 평균 급여보다 낮은 사원들의 급여를 20% 인상*/
SELECT * FROM EMPLOYEES
WHERE SALARY < (SELECT AVG(SALARY) FROM EMPLOYEES);

UPDATE EMPLOYEES
SET SALARY = SALARY * 1.2
WHERE SALARY < (SELECT AVG(SALARY) FROM EMPLOYEES);

SELECT * FROM EMPLOYEES WHERE FIRST_NAME = 'Bruce';

/*PLAYER 테이블에서 NICKNAME이 NULL인 선수들을 정태민 선수의 닉네임으로 바꾸기*/
UPDATE PLAYER
SET NICKNAME = (SELECT NICKNAME FROM PLAYER WHERE PLAYER_NAME = '정태민')
WHERE NICKNAME IS NULL;

SELECT * FROM PLAYER;

/*PLAYER 테이블에서 평균 키보다 큰 선수들 삭제*/
DELETE FROM PLAYER
WHERE HEIGHT > (SELECT AVG(HEIGHT) FROM PLAYER);

SELECT 
(HEIGHT) FROM PLAYER;
SELECT MAX(HEIGHT) FROM PLAYER;

/*JOIN*/
/*
 * 여러 테이블에 흩어져 있는 정보 중
 * 사용자가 필요한 정보만 가져와서 가상의 테이블처럼 만들고 결과를 보여주는 것.
 * 정규화를 통해 조회 테이블이 너무 많이 쪼개져 있으면 작업이 불편하기 때문에
 * 입력, 수정, 삭제의 성능을 향상시키기 위해서 JOIN을 통해 합친 후 사용한다.
 * 
 * */

/*EMP 테이블 사원번호로 DEPT 테이블의 지역 검색*/
SELECT*FROM EMP;
SELECT*FROM DEPT;

SELECT ENAME, LOC
FROM DEPT D JOIN EMP E
ON D.DEPTNO = E.DEPTNO;

/*PLAYER 테이블에서 송종국 선수가 속한 팀의 전화번호 검색하기*/
SELECT T.TEAM_ID, PLAYER_NAME, TEL
FROM TEAM T INNER JOIN PLAYER P
ON T.TEAM_ID = P.TEAM_ID AND PLAYER_NAME = '송종국';

/*JOBS 테이블에서 JOB_ID로 직원들의 JOB_TITLE, EMAIL, 성, 이름 검색*/

SELECT*FROM JOBS;

SELECT JOB_TITLE, EMAIL, FIRST_NAME, LAST_NAME
FROM JOBS J JOIN EMPLOYEES E 
ON J.JOB_ID = E.JOB_ID;

/*EMP 테이블의 SAL을 SALGRADE 테이블의 등급으로 나누기*/
SELECT E.ENAME , S.GRADE FROM EMP E JOIN SALGRADE S
ON SAL BETWEEN S.LOSAL AND HISAL;

/*EMPLOYEES 테이블에서 HIREDATE가 2003~2005년까지인 사원의 정보와 부서명 검색*/
SELECT E.*,DEPARTMENT_NAME
FROM EMPLOYEES E JOIN DEPARTMENTS D
ON E.DEPARTMENT_ID = D.DEPARTMENT_ID AND E.HIRE_DATE BETWEEN '20030101' AND '20051231';

/*AND E.HIRE_DATE BETWEEN '20030101' AND '20051231'
BETWEEN TO_DATE('2003', YYYY) AND TO_DATE('2005', 'YYYY');*/

/*JOB_TITLE 중 'Manager'라는 문자열이 포함된 직업들의 평균 연봉을 JOB_TITLE별로 검색*/
SELECT JOB_TITLE, AVG(E.SALARY)
FROM JOBS J JOIN EMPLOYEES E
ON J.JOB_ID = E.JOB_ID
WHERE J.JOB_TITLE LIKE '%Manager%'
GROUP BY J.JOB_TITLE;

/*EMP 테이블에서 ENAME에 L이 있는 사원들의 DNAME과 LOC 검색*/
SELECT DNAME, LOC
FROM EMP E JOIN DEPT D ON E.DEPTNO = D.DEPTNO
AND E.ENAME LIKE '%L%'; 


SELECT * FROM PLAYER p ;
SELECT TEAM_ID , HEIGHT  FROM PLAYER p ;


SELECT MAX(HEIGHT), TEAM_ID  FROM PLAYER p
GROUP BY TEAM_ID ;




/*축구 선수들 중에서 각 팀별로 키가 가장 큰 선수들 전체 정보 검색*/
SELECT * FROM 
(SELECT*FROM PLAYER
GROUP BY TEAM_ID
);

SELECT H.M, P.PLAYER_NAME, P.TEAM_ID
FROM PLAYER p JOIN (
SELECT MAX(HEIGHT) M, TEAM_ID  FROM PLAYER p
GROUP BY TEAM_ID) H 
ON P.TEAM_ID = H.TEAM_ID 
WHERE P.HEIGHT = H.M AND P.TEAM_ID = H.TEAM_ID;


/*선생님 코드 1, 2*/
SELECT P1.*
FROM PLAYER P1 JOIN
( SELECT TEAM_ID, MAX (HEIGHT) HEIGHT FROM PLAYER p 
	GROUP BY TEAM_ID )
	P2
	ON P1.TEAM_ID = P2.TEAM_ID AND P1.HEIGHT = P2.HEIGHT
	ORDER BY P1. TEAM_ID;

/*(A,B) IN (C,D) : A = C AND B = D*/
SELECT * FROM PLAYER
WHERE (TEAM_ID, HEIGHT) IN (SELECT TEAM_ID, MAX(HEIGHT) HEIGHT FROM PLAYER GROUP BY TEAM_ID)
ORDER BY TEAM_ID;


/*EMP 테이블에서 사원의 이름과 매니저 이름을 검색*/
SELECT ENAME, JOB FROM EMP
WHERE JOB = 'CLERK' OR JOB LIKE '%MANAGER';

SELECT*FROM EMP;

SELECT E.EMPNO , E.ENAME , E.MGR , M.EMPNO ,M.ENAME 
FROM EMP E JOIN EMP M 
ON E.MGR = M.EMPNO;


/*SQL 실행 순서
 * FROM > ON > JOIN > WHERE > GRUP BY > HAVING > SELECT > ORDER BY*/


/*[브론즈]*/
/*PLAYER 테이블에서 키가 NULL인 선수들은 키를 170으로 변경하여 평균 구하기(NULL 포함)*/
SELECT*FROM PLAYER;

/*170으로 변경*/
SELECT PLAYER_NAME, NVL(HEIGHT, 170) FROM PLAYER;

SELECT AVG(H) "AVERAGE HEIGHT" FROM(SELECT NVL(HEIGHT, 170) H FROM PLAYER);


/*[실버]*/
/*PLAYER 테이블에서 팀 별 최대 몸무게*/ 

SELECT*FROM PLAYER;

SELECT TEAM_ID,MAX(WEIGHT) FROM PLAYER GROUP BY TEAM_ID;

/*GROUP BY는 결과값이 1개만 나옴!! 하나로만 묶기 때문임.*/

/*[골드]*/
/*AVG 함수를 쓰지 않고 PLAYER 테이블에서 선수들의 평균 키 구하기(NULL 포함)*/

SELECT SUM(NVL(HEIGHT,0))/COUNT(NVL(HEIGHT,0)) "AVERAGE HEIGHT" FROM PLAYER;

/*[플래티넘]*/
/*DEPT 테이블의 LOC별 평균 급여를 반올림한 값과 각 LOC별 SAL 총 합을 조회, 반올림 : ROUND()*/
SELECT*FROM DEPT;

/*평균 급여 찾기*/ 
SELECT LOC, ROUND(AVG(SAL)) "평균 급여", SUM(SAL) "총 합"
FROM DEPT D JOIN EMP E ON D.DEPTNO = E.DEPTNO
GROUP BY LOC;

/*[다이아]*/
/*PLAYER 테이블에서 팀별 최대 몸무게인 선수 검색*/

SELECT P1.* FROM PLAYER P1 JOIN (SELECT TEAM_ID, MAX (WEIGHT) WEIGHT FROM PLAYER P
GROUP BY TEAM_ID) P2
ON P1.TEAM_ID = P2.TEAM_ID AND P1.WEIGHT = P2. WEIGHT
ORDER BY P1.TEAM_ID;

SELECT TEAM_ID, MAX(WEIGHT) FROM PLAYER
GROUP BY TEAM_ID; /*얘가 조인이 될 테이블로 들어가야 함 */

SELECT P2.* FROM 
(
SELECT TEAM_ID, MAX(WEIGHT) MAX_WEIGHT FROM PLAYER
GROUP BY TEAM_ID
) P1
JOIN PLAYER P2
ON P2.TEAM_ID = P2.TEAM_ID AND P1.MAX_WEIGHT = P2.WEIGHT
ORDER BY P2.TEAM_ID; 


/*[마스터]*/
/*EMP 테이블에서 HIREDATE가 FORD의 입사년도와 같은 사원 전체 정보 조회*/
SELECT*FROM EMP;
SELECT*FROM JOB_HISTORY;

SELECT*FROM EMP E JOIN JOB_HISTORY J ON E.EMPNO = J.EMPLOYEE_ID
WHERE J.HIREDATE = E.EMPNO(E.NAME='FORD')

SELECT * FROM EMP e WHERE TO_CHAR(HIREDATE, 'YYYY') = 
(SELECT TO_CHAR(HIREDATE, 'YYYY') FROM EMP WHERE ENAME='FORD');


/*외부 조인*/
/*join 할 때 선행 또는 후행 중 하나의 테입르 정보를 모두 확인하고 시플 때 사요한다.
 * 선행테이블이 행의 개수가 더 작아야 한다! */
SELECT NVL(TEAM_NAME,'공용'), S.*
FROM TEAM T RIGHT OUTER JOIN STADIUM S ON T.TEAM_ID = S.HOMETEAM_ID;

/*DEPARTMENT 테이블에서 매니저이름 검색, 매니저 없더라도 부서명 모두 검색*/
SELECT*FROM DEPARTMENTS;

SELECT D.DEPARTMENT_NAME, NVL(E.LAST_NAME, 'NO') || ' ' || NVL(E.FIRST_NAME, 'NAME')
FROM DEPARTMENTS D LEFT OUTER JOIN EMPLOYEES E
ON D.DEPARTMENT_ID = E.DEPARTMENT_ID AND D.MANAGER_ID = E.EMPLOYEE_ID;

/*EMPLOYEES 테이블에서 사원의 매니저 이름, 사원의 이름 조회, 매니저가 없는 사원은 본인이 매니저임을 표시*/
SELECT * FROM EMPLOYEES;

SELECT E1.FIRST_NAME "사원 이름", NVL(E2.FIRST_NAME , E1.FIRST_NAME) "매니저 이름"
FROM EMPLOYEES E1 LEFT OUTER JOIN EMPLOYEES E2
ON E1.MANAGER_ID = E2.EMPLOYEE_ID;


/*EMPLOYEES 테이블에서 사원들의 FIRST_NAME 모두 조회, 사원들 중 매니저는 JOB_ID 조회 */
SELECT FIRST_NAME, JOB_ID FROM EMPLOYEES;


SELECT JOB_ID, MANAGER_ID FROM EMPLOYEES
GROUP BY JOB_ID, MANAGER_ID;


SELECT E1.FIRST_NAME 이름, NVL(E2.JOB_ID, '일반사원') 직책 FROM EMPLOYEES E1 LEFT OUTER JOIN 
(
SELECT E.FIRST_NAME, EMPLOYEE_ID, E.JOB_ID
FROM DEPARTMENTS D LEFT OUTER JOIN EMPLOYEES E
ON D.MANAGER_ID = E.EMPLOYEE_ID
) E2 
ON E1.EMPLOYEE_ID = E2.EMPLOYEE_ID;

/*EMPLOYEES에서 각 사원별로 관리부서(매니저)와 소속부서(사원) 조회*/
SELECT E1.JOB_ID 관리부서, E2.JOB_ID 소속부서, E2.FIRST_NAME 이름
FROM
(
   SELECT JOB_ID, MANAGER_ID FROM EMPLOYEES
   GROUP BY JOB_ID, MANAGER_ID
) E1 
FULL OUTER JOIN EMPLOYEES E2
ON E1.MANAGER_ID = E2.EMPLOYEE_ID
ORDER BY 소속부서 DESC;

/*VIEW : 실제 데이타는 아닌데 보여지기만을 위한 용도, 테이블과 뷰를 비교해야함
 * CREATE VIEW [이름] AS [쿼리문]
 * 
 * 기존의 테이블을 그대로 놔둔 채 필요한 컬럼들 및 새로운 컬럼을 만든 가상 테이블
 * 실제 데이터가 저장되는 것은 아니지만 VIEW를 통해서 데이터를 관리할 수 있다.
 * 결과값 RESULT를 
 * 기존의 테이블의 쿼리문들로 만든 것이 뷰이다. 
 * 
 * - 독립성 : 다른 곳에서 접근하지 못하도록 하는 성질
 * - 편리성 : 길고 복잡한 쿼리문을 매번 작성할 필요가 없다. 
 * - 보안성 : 기존의 쿼리문이 보이지 않는다. 
 * 
 * 
 * 
 *  */

/*PLAYER 테이	블에 나이 컬럼 추가한 뷰 만들기*/
CREATE VIEW VIEW_PLAYER AS
SELECT FLOOR((SYSDATE - BIRTH_DATE) / 365) AGE, P.* FROM PLAYER P;

SELECT * FROM VIEW_PLAYER;
SELECT * FROM VIEW_PLAYER WHERE AGE < 40;


/*EMPLOYEES 테이블에서 사원 이름과 그 사원의 매니저 이름이 있는 VIEW 만들기*/

SELECT * FROM EMPLOYEES;

CREATE VIEW VIEW_EMPLOYEE_MANAGER_NAME AS
SELECT E1.FIRST_NAME || ' ' ||  E1. LAST_NAME "EMPLOYEE NAME ", E2.FIRST_NAME || ' ' || E2.LAST_NAME "MANAGER NAME"
FROM EMPLOYEES E1 JOIN EMPLOYEES E2
ON E1.MANAGER_ID = E2.EMPLOYEE_ID;

SELECT * FROM VIEW_EMPLOYEE_MANAGER_NAME;

/*PLAYER 테이블에서 TEAM_NAME 칼럼을 추가한 VIEW 만들기*/
SELECT*FROM PLAYER;
SELECT*FROM TEAM;

CREATE VIEW VIEW_TEAMNAME AS
SELECT TEAM_NAME, P.* 
FROM PLAYER P JOIN TEAM T
ON P.TEAM_ID = T.TEAM_ID;

SELECT*FROM VIEW_TEAMNAME;

