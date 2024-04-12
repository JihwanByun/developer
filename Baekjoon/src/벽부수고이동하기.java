package Baekjoon;
/*
최단경로 이동 가능 시 벽 한 개까지 부수고 이동하여도 됨
한 칸에서 이동 가능한 칸은 상하좌우
최단 경로 구하기

0은 이동 가능, 1은 이동할 수 없는 벽

1을 한 번 만나면 0으로 바꾸고 최단 경로 찾기 다시 1 세우기

한 줄 읽기는 생각해야지..
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class 벽부수고이동하기 {
    private static class state {
        int r;
        int c;
        int dis;
        int wall;
        boolean[][] state_visited;

        state(int r, int c, int dis, int wall, boolean[][] visited) {
            this.r = r;
            this.c = c;
            this.dis = dis;
            this.wall = wall;
            this.state_visited = visited;
        }

    }

    static int[][] map;
    static int N, M, wallcnt, ans;
    static boolean[][] visited;

    static int[] dr = {0, 1, 0, -1};
    static int[] dc = {1, 0, -1, 0}; //우하좌상

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        map = new int[N][M];
        visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            String st = sc.next();
            for (int j = 0; j < M; j++) {
                map[i][j] = st.charAt(j) - '0';
            }
        }

        ans = 987654321;

        if (bfs()) {
            System.out.println(ans);
        } else
            System.out.println(-1);

    }

    private static boolean bfs() {
        Queue<state> q = new LinkedList<>();
        int wall = 0;
        visited = new boolean[N][M];

        if (map[0][0] == 1) wall++;
        q.add(new state(0, 0, 1, wall, new boolean[N][M]));
        visited[0][0] = true;

        while (!q.isEmpty()) {
            state curstate = q.poll();

            curstate.state_visited[curstate.r][curstate.c] = true;

            if (curstate.wall > 1) continue;

            if (curstate.r == N - 1 && curstate.c == M - 1) {
                if (ans > curstate.dis)
                    ans = curstate.dis;
            }
            for (int dir = 0; dir < 4; dir++) {
                int nr = curstate.r + dr[dir];
                int nc = curstate.c + dc[dir];

                if (nr < 0 || nr >= N || nc < 0 || nc >= M) continue;
                if (curstate.state_visited[nr][nc]) continue;
                if (curstate.wall == 1 && map[nr][nc] == 1) continue;

                curstate.state_visited[nr][nc] = true;
                if(map[nr][nc]==1) {
                    q.add(new state(nr, nc, curstate.dis + 1, curstate.wall + 1, new boolean [N][M]));

                }

                else q.add(new state(nr, nc, curstate.dis + 1, curstate.wall, curstate.state_visited));
            }

        }
        if (ans == 987654321) return false;
        else return true;

    }

}
