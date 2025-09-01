import os
import google.generativeai as genai
from github import Github

def is_image_file(filename):
    image_extensions = ('.png', '.jpg', '.jpeg', '.gif', '.bmp', '.tiff', '.webp', '.ico')
    return filename.lower().endswith(image_extensions)

def send_prompt():
    gemini_api_key = os.getenv("GEMINI_API_KEY")
    github_token = os.getenv("GITHUB_TOKEN")

    if not github_token or not gemini_api_key:
        print("Error: GEMINI_API_KEY 또는 GITHUB_TOKEN 환경 변수가 설정되지 않았습니다.")
        raise Exception("환경 변수(GEMINI_API_KEY, GITHUB_TOKEN)가 설정되지 않았습니다.")

    genai.configure(api_key=gemini_api_key)
    model = genai.GenerativeModel('gemini-1.5-pro-latest')
    g = Github(github_token)

    # GitHub Actions 컨텍스트에서 PR 정보 가져오기
    repository_name = os.getenv('GITHUB_REPOSITORY')
    pr_number = os.getenv('PR_NUMBER')

    if not pr_number:
        print("Error: PR_NUMBER 환경 변수가 설정되지 않았습니다. PR 이벤트에서만 실행 가능합니다.")
        return

    repo = g.get_repo(repository_name)
    pull_request = repo.get_pull(int(pr_number))

    # PR 정보 추출
    pr_body = pull_request.body

    # 변경된 파일 목록과 diff 내용 전처리
    changed_files = []
    diff_output = ""
    for file in pull_request.get_files():
        if is_image_file(file.filename):
            print(f"Skipping image file: {file.filename}")
            continue

        changed_files.append(file.filename)
        diff_output += f"--- file: {file.filename}\n{file.patch}\n\n"

    # 이미지 파일만 있는 경우
    if not diff_output:
        print("PR에 분석할 코드가 없습니다. 이미지 파일만 변경되었습니다.")
        pull_request.create_issue_comment(f"## 🤖 Gemini 코드 리뷰 결과\n\n이 PR은 코드 변경 없이 이미지 파일만 포함하고 있어, 분석을 건너뜁니다.")
        return

    # Gemini에 전달할 프롬프트 구성
    prompt = f"""
    [역할]
    - 당신은 "코딩 테스트 자동 리뷰어(Static Analysis + Algo Coach)"입니다.
    - 목표: (a) 요구사항 충족 여부 판정, (b) 오류/리스크 발견, (c) 더 나은 해법 제시, (d) 짧은 수정 패치 제안.
    - 언어: Java 11

    [입력 페이로드]
    - 제출 코드: {changed_files}
    
    [평가 루브릭]
    1. 요구사항 정합성
       - 예외/경계: 빈 입력, 한계치(n=0,1,최대), 중복/정렬됨/역정렬, 음수/0, 특수 케이스.
       - 정확성 증거: 반례 또는 추론 근거를 짧게.
    2. 시간/공간 복잡도
       - 상한 Big-O, 최악/평균 구분 가능시 구분.
    3. 알고리즘 적합성
       - 쓰인 기법 식별(예: 투포인터/슬라이딩 윈도우/우선순위큐/유니온파인드/DP 등).
       - 더 나은 대안 가능성 및 전환 조건.
    4. 안정성/버그 리스크
       - 인덱스 범위, 오버플로, 정수/부동, 반복자 무효화, 음수 모듈러, 깊은 복사/얕은 복사, mutable default(Python) 등.
    5. 코드 품질
       - 함수 분리, 네이밍, 불변성 유지, 조기 반환, 예외/에러 처리, I/O 비용.
    6. 테스트 설계
       - 최소 합격 세트(기본/경계/스트레스/에러) + 각 케이스 의도.
    7. 개선안
       - (A) 로컬 패치: 현 코드 유지하며 핵심 문제만 수정.
       - (B) 대안 해법: 알고리즘을 교체(복잡도/단순성 비교 포함).
    
    [출력 규격(JSON, 반드시 이 스키마 준수)]
    {{
        "assumptions": ["불명확 사양에 대한 명시적 가정..."],
      "summary": "한 문장 핵심 평가",
      "verdict": "pass | needs_fix | fail",
      "score": {{
        "correctness": 0-10,
        "complexity": 0-10,
        "style": 0-10,
        "overall": 0-100
      }},
      "issues": [
        {{"title":"정확성", "evidence":"근거/해당 코드 라인/논리", "fix":"수정 방향"}},
        {{"title":"복잡도", "evidence":"병목 근거", "fix":"개선 제안"}},
        {{"title":"안정성", "evidence":"경계/예외", "fix":"보호 코드"}}
      ],
      "complexity": {{
        "identified_algo": "예: O(n log n) 정렬 + 투포인터",
        "time": "O(...)", 
        "space": "O(...)",
        "bottlenecks": ["..."]
      }},
      "tests": [
        {{"name":"기본","input":"...","expected":"...","reason":"정상 플로우"}},
        {{"name":"경계-최소","input":"...","expected":"...","reason":"n=0/1"}},
        {{"name":"중복/특수","input":"...","expected":"...","reason":"코너 케이스"}},
        {{"name":"중복/특수","input":"...","expected":"...","reason":"코너 케이스"}},
        {{"name":"중복/특수","input":"...","expected":"...","reason":"코너 케이스"}},
        {{"name":"비정상","input":"...","expected":"...","reason":"에러 방어(필요시)"}},
        {{"name":"경계-최대","input":"...","expected":"...","reason":"최악 시간"}},
      ],
      "improvements": {{
        "patch_notes": "라인 단위 변경 요약(불필요한 전체 재작성 금지)",
        "alt_approach": "대안 알고리즘 요지 + 의사코드",
        "when_to_use_alt": "입력 특성/한도에 따른 선택 기준",
        "readability_tips": ["네이밍", "함수 분리", "I/O 최적화"]
      }},
      "plagiarism_risk": "low | medium | high (선택, 스타일/구현 패턴 기준 추정)"
    }}
    
    [작성 규칙]
    - 내부 추론 과정/사고사슬 노출 금지. 결론/근거만.
    - 코드 실행/파일 접근/네트워크 호출 금지. 정적 분석 기반.
    - 가능하면 코드 라인/블록을 인용하되 20줄 이내로 국소 인용.
    - 불명확 사양은 가정 후 명시하고, 가정 변경 시 결과가 어떻게 달라지는지 한 줄로 언급.
    - 답변은 한국어. 수식/복잡도 표기는 표준 Big-O.
    
    [간단 의사코드 템플릿(대안 해법 예시 시)]
    - 핵심 단계만 5~10줄 내로, 언어 중립적 표현.
    """

    try:
        response = model.generate_content(prompt)
        gemini_analysis = response.text

        # 생성된 분석 결과를 PR에 댓글로 작성
        comment_body = f"## 🤖 Gemini 코드 리뷰 결과\n\n{gemini_analysis}"
        pull_request.create_issue_comment(comment_body)
        print("Gemini 코드 리뷰가 PR에 성공적으로 게시되었습니다.")

    except Exception as e:
        comment_body = f"Gemini 코드 리뷰 중 오류가 발생했습니다: {e}"
        pull_request.create_issue_comment(comment_body)
        print(comment_body)


if __name__ == "__main__":
    send_prompt()