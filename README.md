# blog_api
블로그 검색 및 RealTime 인기목록 API

### 지원자 정보
* 2126-000188_박일우_서버개발자

### 개발환경
* JAVA17
* Spring Boot 3.1.1
* Gradle 8.0
* (Optional) docker 20.10.17^


### MultiModule 구성도
* search_module
  * Blog 검색을 위한 모듈
  * 사용한 외부 라이브러리
    * javax.validation:validation-api(2.0.1 Fianl) -> Request Parameter의 Validation을 위해 사용
    * com.google.code.gson:gson(2.10.1) -> Blog 검색 API의 응답값을 객체에 Mapping하기 위해 사용
* popular_module
  * 인기 키워드 조회를 위한 모듈
  * 사용한 외부 라이브버리 없음
* data_module
  * 데이터를 컨트롤 하는 모듈
  * 사용한 외부 라이브러리
    * it.ozimov:embedded-redis(0.7.2) -> Embedded redis를 위해 사용

### 흐름도
![Blank diagram](https://github.com/parkilwoo/blog_api/assets/56834479/b0caa848-f2f2-4677-877c-46b7f9d3abcd)
* Blog 검색
  1. 유저가 요청을 한다.
  2. search_module에서 API를 호출하고 호출 결과를 응답한다.
  3. 비동기로 data_module에 검색 키워드의 count를 증가시킨다
* 인기 키워드 조회
  1. 유저가 요청을 한다.
  2. popular_module에서 data_module를 호출하여 인기 키워드를 조회한다.
  3. 유저에게 응답한다.
 
### 프로젝트 설명
* 모듈을 Layerd Architecture에 따른 분류가 아닌 각각의 하나의 서비스별로 구성
  * MSA를 생각하여 하나의 서비스에서 문제가 생겨도 다른 서비스는 정상구동을 할 수 있도록 모듈간 의존도를 최소화
  * 추후 사용량이 많아질경우 어떤 서비스에서 트래픽이 많이 발생하는지 분석하여 해당 서비스의 scale-up, scale-out등을 할 수 있을거라 판단하여 서비스별로 모듈 구성
* 데이터의 조회에 Redis를 사용
  * 트래픽이 많고 저장되어 있는 데이터가 많음을 염두해서 처음에는 DB를 이중화하여 CORS 패턴을 설계
    * 하지만 데이터가 많아지면서 RDB에서 순위를 구할 때 성능이 좋지 않다는것을 인지 -> 파티셔닝등을 고려했으나 딱히 분할 기준이 애매한 데이터라 판단됨
  * Redis에서 사용되는 sorted set 자료구조가 랭킹을 구할때 아주 효율적임을 알고 Redis를 사용
    * 적재하는 데이터가 간단하여 명령 수행이 굉장히 짧을것이라 예상
    * 혹시모를 Redis의 장애에 대비해 RDB에도 데이터를 적재 -> 후에 Redis에 문제가 생기면 RDB의 데이터로 백업
* 동시성 이슈
   * 싱글스레드 기반의 Redis를 사용하였므로 동시성 이슈 간단하게 해결
   * 백업을 위한 RDB에는 SELECT FOR UPDATE를 이용하여 배타락을 얻는 방식에 비관적 잠금 -> 백업용 디비라 성능보단 동시성 해결이 우선이라 판단 하였음
* 블로그 검색 API 추상화
   * 블로그 검색에 대한 부분을 최대한 추상화 하여 장애가 났을시 쉽게 API Connector를 설계

### API 명세

```GET http://localhost:8081/api/v1/blog```
* Request

| 필드명 | 타입 | 설명 | 필수 여부 | 기본값 |
| --- | --- | --- | --- | --- |
| query | String | 검색어 | O | |
| sort | String | 정렬 기준 accuracy(정확도순), recency(최신순) | X | accuracy |
| page | Integer | 페이지 번호(1~50사이) | X | 1 |
| size | Integer | 한 페이지에 보여질 문서 수(1~50사이) | X | 10 |

* Response

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| meta | Meta | 응답 관련 정보 |
| documents | Document[] | 응답 결과 |

* Meta

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| total_count | Integer | 검색된 문서 수 |
| pageable_count | integer | total_count 중 노출 가능 문서 수 |
| is_end | Boolean | 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음 |

* document

| 필드명       | 타입   | 설명                      |
|-----------| ------ | ------------------------- |
| title     | string | 블로그 글 제목                 |
| contents  | string | 블로그 글 요약                 |
| url       | string | 블로그 글 URL                  |
| blogname  | string | 블로그의 이름               |
| thumbnail | string | 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음        |
| datetime  | Datetime | 블로그 글 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]            |


```GET http://localhost:8082/api/v1/popular```
* Response

| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| popularSearch | PopularSearch[] | 응답결과 |

* PopularSearch


| 필드명 | 타입 | 설명 |
| --- | --- | --- |
| keyword | String | 검색어 |
| score | Integer | 검색횟수 |


### 다운로드 링크 및 실행방법

다운로드 링크: https://github.com/parkilwoo/20230705_2126-000188/tree/main/download

🔥 **매우중요**

구성도에서도 말했다싶이 서버가 두개입니다. 꼭 링크에 있는 popular_module-0.0.1-SNAPSHOT.jar, search_module-0.0.1-SNAPSHOT.jar 두 파일 모두 받은 후
``` java -jar search_module-0.0.1-SNAPSHOT.jar ```
``` jave -jar popular_module-0.0.1-SNAPSHOT.jar ```
두개 다 실행해주세요!(어떤 파일을 먼저 실행하든 상관은 없습니다.)
