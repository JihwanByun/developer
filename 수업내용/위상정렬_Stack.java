package 수업내용;

import java.util.Scanner;
import java.util.Stack;

public class 위상정렬_Stack {
    static int V, E;
    static int[][] adj;
    static int[] degree;
    static boolean[] visited; //방문첵
    static Stack<Integer> stack; //할 일을 담을 스택


    public static void main(String[] args) {
        String[] cook = {"", "재료구매", "양념장만들기", "고기재우기", "고기손질", "제육볶음만들기", "식사", "뒷정리", "채소손질", "밥하기"};

        Scanner sc = new Scanner(System.in);

        V = sc.nextInt();
        E = sc.nextInt();
        adj = new int[V + 1][V + 1];
        degree = new int[V + 1];
        visited = new boolean[V + 1];
        stack = new Stack<>();

        for (int i = 0; i < E; i++) {
            int A = sc.nextInt();
            int B = sc.nextInt();

            adj[A][B] = 1; //가중치가 따로 없기떄문에 1로 표기, 유향이니 반대는 처리 X

            //진입차수를 증가
            degree[B]++;
        } //입력

        for (int i = 1; i < V + 1; i++) {
            //진입차수가 0인 정점들을 전부 다 DFS 탐색 하겠다.
            if (degree[i] == 0)
                dfs(i);
        }

        while (!stack.isEmpty()) {
            System.out.println(cook[stack.pop()]);
        }
        //해당 라인이 수행된다는 뜻은 위상정렬 끝! 해당 작업은 stack에 몽땅 들어 있음

    }

    static void dfs(int v) {
        visited[v] = true;

        for (int i = 1; i < V + 1; i++) {
            //인접하고, 방문하지 않은 점이 있다면 방문
            if (adj[v][i] == 1 && !visited[i]) {
                dfs(i);
            }
        }

        //해당 라인이 실행된다는 뜻은 ! 할 거 다 했어 갈 수 있는 거 전부 다녀왔어
        stack.add(v); // 나 이제 들어간다.

    }
}

