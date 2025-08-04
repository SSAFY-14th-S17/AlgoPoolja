# 🧠 SSAFY Algorithm Study - AlgoPoolja

> **알고리즘 알고풀자**  
> SSAFY 알고리즘 스터디 *AlgoPoolja*에 오신 것을 환영합니다 💻🌱  
> 백준, SW Expert Academy 등 다양한 플랫폼에서 문제를 풀고,  
> 코드 리뷰와 힌트 문서를 통해 문제를 풀고 velog에 풀이 글을 작성해보자!

---

## 📌 스터디 진행 방식

- 매주 **1회 문제 출제** (6문제)
- 플랫폼: `BOJ`, `SWEA` 등 다양하게 선정
- 난이도: `Silver`, `Gold`, `D2~D4`
- 문제는 `.github/weekly-problems/weekN.yml` 에서 관리
- 각 문제 힌트는 `docs/problems/weekN/` 폴더에 `문제명_힌트.md`로 정리

---

## 🗂 주차별 문제 모음 (이슈 & 힌트 링크)

<details>
<summary><strong>📁 Week 1 - 완전탐색</strong></summary>

| 번호 | 문제 | 문제제 | 힌트 |
|------|------|------|------|
| 1 | 13038. 교환학생 | [🔗 이슈](https://github.com/SSAFYstudyAlgoPoolja/AlgoPoolja/issues/1) | [📄 힌트](./docs/problems/week1/13038_교환학생_힌트.md) |
| 2 | 5215. 햄버거 다이어트 | [🔗 이슈](https://github.com/SSAFYstudyAlgoPoolja/AlgoPoolja/issues/2) | [📄 힌트](./docs/problems/week1/5215_햄버거다이어트_힌트.md) |
| 3 | 2503. 숫자야구 | [🔗 이슈](https://github.com/SSAFYstudyAlgoPoolja/AlgoPoolja/issues/3) | [📄 힌트](./docs/problems/week1/2503_숫자야구_힌트.md) |
| 4 | 14888. 연산자 끼워넣기 | [🔗 이슈](https://github.com/SSAFYstudyAlgoPoolja/AlgoPoolja/issues/4) | [📄 힌트](./docs/problems/week1/14888_연산자_끼워넣기_힌트.md) |
| 5 | 2659. 십자카드 문제 | [🔗 이슈](https://github.com/SSAFYstudyAlgoPoolja/AlgoPoolja/issues/5) | [📄 힌트](./docs/problems/week1/2659_십자카드_문제_힌트.md) |
| 6 | 14620. 꽃길 | [🔗 이슈](https://github.com/SSAFYstudyAlgoPoolja/AlgoPoolja/issues/6) | [📄 힌트](./docs/problems/week1/14620_꽃길_힌트.md) |

</details>

---

## 📁 프로젝트 구조
```
AlgoPoolja/
├── .github/
│   ├── workflows/                 # GitHub Actions 워크플로우 (자동화)
│   └── weekly-problems/          # 주차별 문제 YAML 파일
│       └── week1.yml
├── bin/
│   └── Main.class                # 컴파일된 클래스 파일 (Java)
├── docs/
│   └── problems/
│       └── week1/                # 주차별 힌트 파일 모음
│           ├── 13038_교환학생_힌트.md
│           ├── 5215_햄버거다이어트_힌트.md
│           └── ...
├── hyunbin/                      # 팀원별 풀이/코드 저장소
├── dohee/                        # 여기선 파일 자유롭게 사용
├── jimin/
├── minseung/
├── seho/
├── youyeon/
├── .gitignore                    # Git 무시 파일 설정
├── README.md                     # 프로젝트 소개 문서
├── .project                      # STS 등에서 사용하는 설정 파일
```
