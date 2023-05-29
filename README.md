# 🚇 jwp-subway-path

---

## 🤼 페어

---

| <img src="https://avatars.githubusercontent.com/u/77482065?v=4" alt="" width=150> | <img src="https://avatars.githubusercontent.com/u/106813090?v=4" alt="" width=150/> |
|:---------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------:|
|                         [디노](https://github.com/jjongwa)                          |                          [후추](https://github.com/Combi153)                          | |

## 🦖 기능 요구사항

---

### API

-GET

- `/lines` : 전체 노선과 해당 역 조회 ex) 1호선 역 정보, 2호선 역 정보, ...
- `/lines/{lineId}` : 해당 노선의 역 전체 조회 ex) 합정 -> 홍대 -> 신촌 -> 이대 ...

-POST

- `/sections/{lineId}` : 역 추가 (구간 추가)

-DELETE

- `/sections/{lineId}/stationId=?` : 역 삭제 (해당 구간 삭제 및 재배치)

**2단계 추가 API**

-GET

- `/path` : 출발역에서 도착역 사이의 최단 거리 경로 조회
    - ex) 합정 -> 홍대 -> 신촌, 17km, 1350원

### 비즈니스 규칙

- 노선에 역 등록
    - 등록되는 역의 위치는 자유롭게 지정 가능하다.
        - A-B-C 역이 등록되어 있는 노선에 D역을 등록할 경우 역과 역 사이, A역 앞과 C역 뒤에도 등록을 할 수 있다.
    - 거리 정보를 포함한다.
        - 거리 정보는 양의 정수로 제한된다.
    - 노선에 역이 하나도 등록되지 않은 상황에서 **최초 등록 시 두 역을 동시에 등록해야 한다.**
    - 하나의 역은 여러 노선에 등록될 수 있다.
    - 노선은 갈래길을 가질 수 없다.
    - 노선 가운데 역이 등록될 경우 거리 정보를 고려해야 한다.
        - 거리가 3km인 A-C 노선 사이에 B 역을 등록할 때 다음을 만족한다.
            - `A-B 거리` + `B-C 거리` = 3km
            - `A-B 거리 > 0`  && `B-C 거리 > 0`

- 노선에 역 제거
    - 노선에서 중간 역을 제거할 경우 노선의 역 순서와 거리정보가 재배치된다.
        - A-B-C-D 역이 있는 노선에서 C역이 제거되는 경우 A-B-D 순으로 재배치된다.
        - A-B가 2km, B-C가 3km, C-D가 4km인 경우 C역이 제거되면 B-D 거리가 7km가 된다.
    - 노선에 등록된 역이 2개 인 경우 하나의 역을 제거할 때 두 역이 모두 제거된다.

## 📀 데이터베이스

```sql
create table if not exists STATION
(
    id
    bigint
    auto_increment
    not
    null,
    name
    varchar
(
    255
) not null unique
    );

create table if not exists LINE
(
    id
    bigint
    auto_increment
    not
    null,
    name
    varchar
(
    255
) not null unique,
    color varchar
(
    20
) not null
    );

create table if not exists SECTION
(
    id
    bigint
    auto_increment
    not
    null,
    line_id
    bigint
    not
    null,
    from_id
    bigint
    not
    null,
    to_id
    bigint
    not
    null,
    distance
    bigint
    not
    null
);
```

**2단계 요구사항**

데이터베이스 설정을 프로덕션과 테스트를 다르게 합니다.

- 프로덕션의 데이터베이스는 로컬에 저장될 수 있도록 설정
- 테스트용 데이터베이스는 인메모리로 동작할 수 있도록 설정
