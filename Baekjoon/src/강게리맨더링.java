package Baekjoon;


import java.util.*;

/*
양방향 간선, 지역구별 인구가 존재,최소의 선거구 차이, 못 나눌 경우 -1
한 선거구는 반드시 하나로 연결되어 었어야 함
모두 연결이라는 말은 없음, 둘 혹은 셋 이상의 연결 그룹이 나올 수 있다는 것 인지


1. 각 노드에 연결된 간선 저장
2. 숫자가 순서대로 연속되어 있으니 카운팅 배열 사용하여 인구수 저장
3. 어떻게 각 선거구를 나눌 것인가, (조건 각 선거구의 전체 합이 총 인구수가 되야 한다)
4. 완전탐색 활용하여, 모든 경우의 수에 대한 각 그룹별 노드의 연결성 유무 확인하기 - > 부분 집합 + BFS





메서드에서 계산한 값을 저장하지 않는다
bfs 방문체크는 마지막에 해야함, 들어가고 나오는 거에서 체크하면 마지막에 없을 때 체크 못하잖아


 */
public class 강게리맨더링 {
    static int N, All_p, min_Cnt;
    static int[] cnt, population;
    static List<Integer>[] adj;

    static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        cnt = new int[N + 1]; // 카운트 배열
        visited = new boolean[N + 1];
        All_p = 0;
        min_Cnt = 987654321;
        population = new int[2];

        for (int i = 1; i <= N; i++) {
            cnt[i] = sc.nextInt();
            All_p += cnt[i]; //전체 인구 정보 받아오기
        }
        //인구 정보 받아와
        adj = new ArrayList[N + 1]; // 각 노드들의 간선 채우기
        for (int i = 1; i <= N; i++) {
            adj[i] = new ArrayList<>();
            int num = sc.nextInt(); // num수 만큼 간선 받기
            for (int j = 0; j < num; j++) {
                adj[i].add(sc.nextInt());
            }
        }
        search_combi(1);

        if (min_Cnt == 987654321) { //최소값이 갱신되지 않으면
            System.out.println(-1); //불가능
            return;
        }
        System.out.println(min_Cnt); //정답 출력

    }
    private static void search_combi(int idx){
        if (idx >= N + 1) {
            population[0] = 0; //a와 b의 인구수 0으로 초기회
            population[1] = 0;
            if (divide(visited)) { // 부분집합 조합으로 나눌 수 있는 지 확인하기
                int ans = Math.abs(population[0] - population[1]); //인구수 차이 확인하기
                if (min_Cnt > ans)
                    min_Cnt = ans;
            }
            return;
        }
        visited[idx] = true;
        search_combi(idx + 1); //다음 idx 확인하기

        visited[idx] = false;
        search_combi(idx + 1);
        //부분집합 찾기
    }

    private static boolean divide(boolean[] visited) {
        List<Integer> A = new ArrayList<>();
        List<Integer> B = new ArrayList<>();
        for (int i = 1; i <= N; i++) {
            if (visited[i]) A.add(i);
            else B.add(i);
        }
        //A랑 B 나눴다
        if (A.isEmpty() || B.isEmpty()) return false; //빈 조합은 퇴출
        boolean[] copy = Arrays.copyOf(visited, N + 1);
        // visited 배열 copy 하기 중간에 훼손할 수 있으니

        bfs(A, 0, copy); // A 연결된 노드들 인구 구하기

        for (int i = 1; i <= N; i++) {
            if (copy[i]) copy[i] = false;
            else copy[i] = true;
        }
        // copy 배열 참, 거짓 뒤집기

        bfs(B, 1, copy);

        if (population[0] + population[1] == All_p) return true;

        return false;
    }
    private static void bfs(List<Integer> list, int idx, boolean[] copy) {
        boolean[] visited_Q = new boolean[N + 1];

        Queue<Integer> q = new LinkedList<>();
        q.add(list.get(0));
        visited_Q[list.get(0)]= true; //처음 가는 곳 방문 체크
        population[idx] += cnt[list.get(0)]; // A나 B에 인구수 넣기

        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int k : adj[cur]) {
                if (copy[k] && !visited_Q[k]) { //같은 그룹으로 나눈 조직 && 아직 방문하지 않은 곳?
                    q.add(k);
                    visited_Q[k] = true; //방문체크
                    population[idx] += cnt[k]; // 인구 수 반영
                }
            }
        }
    }
}





