import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class 캐슬디펜스 {
    static int N, M, D, maxKill, idx, kill;
    static int[][] lattice;

    static int[][] kill_zombie;

    static int[] dr = {0, -1, 0};
    static int[] dc = {-1, 0, 1};

    public static class zombie {
        int r, c, d;

        zombie(int r, int c, int d) {
            this.r = r;
            this.c = c;
            this.d = d;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt(); //행
        M = sc.nextInt(); //열
        D = sc.nextInt(); //궁수 공격거리제한
        idx = 0;
        maxKill = 0;
        kill_zombie = new int[3][2];
        lattice = new int[N + 1][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++)
                lattice[i][j] = sc.nextInt();
        }
        //3명의 궁수배치 lattice[N][] N행에 궁수 3명 배치
        //모든적이 사라지면 겜 끝
        archerturn(0, 0);

        System.out.println(maxKill);

    }

    private static boolean zombieturn(int[][] lattice) {
        int zombiecnt = 0;
        for (int i = N-1; i >=0; i--) {
            for (int j = 0; j < M; j++) {
                if (lattice[i][j] == 1) {
                    lattice[i][j] = 0;
                    if (i + 1 < N) {
                        lattice[i + 1][j] = 1;
                        zombiecnt++;
                    }
                }
            }
        }

        if (zombiecnt == 0) return true; //게임 끝
        else return false; //게임 아직 안끝남
    }


    private static void archerturn(int column, int sidx) {
        if (sidx == 3) { //3명 배치 끝 공격 시작
            kill = 0;

            int[][] can_lattice = new int[N + 1][M];
            for (int i = 0; i <= N; i++) {
                for (int j = 0; j < M; j++)
                    can_lattice[i][j] = lattice[i][j];
            }

            while (true) { //궁수턴 시작!
                idx = 0;
                int[][] kill_zombie = new int[3][2];

                for (int j = 0; j < M; j++) {
                    if (can_lattice[N][j] == 2) { //타깃 찾기 idx 받아와야해!
                        bfs(N - 1, j, can_lattice,kill_zombie);

                    }
                } //공격 대상 선정

                for (int i = 0; i < 3; i++) {
                    if(D < N && kill_zombie[i][0] == 0 && kill_zombie[i][1] ==0) continue; //한번에 잡는거면 어떡하지..
                    if (can_lattice[kill_zombie[i][0]][kill_zombie[i][1]] == 0) continue; //이미 잡은 저녁

                    can_lattice[kill_zombie[i][0]][kill_zombie[i][1]] = 0;
                    kill++;//좀비 킬
                }
                if (zombieturn(can_lattice)) {

                    if (maxKill < kill) maxKill = kill;
                    break; //겜끝

                }  //게임이 안끝났어
            }
            return;
        }

        if (column == M) return;

        lattice[N][column] = 2;
        archerturn(column + 1, sidx + 1);
        lattice[N][column] = 0;
        archerturn(column + 1, sidx);

    }

    public static void bfs(int r, int c, int[][] can_lattice,int[][] kill_zombie) { //서울 6반 이치왕 나이스

        int d = 1;

        Queue<zombie> q = new LinkedList<>();
        q.add(new zombie(r, c, d));

        if (can_lattice[q.peek().r][q.peek().c] == 1) {
            kill_zombie[idx][0] = q.peek().r;
            kill_zombie[idx][1] = q.peek().c;
            idx++;


            return;
        } //처음만 검사

        while (!q.isEmpty() && idx != 3) {
            boolean[][] visited = new boolean[N][M]; //아처 한 명당 초기화하는 거
            zombie can_zomibe = q.poll();

            if (can_zomibe.d >= D) continue;
            visited[can_zomibe.r][can_zomibe.c] = true;

            for (int dir = 0; dir < 3; dir++) { //애초에 들어가는게 d가 2
                int nr = can_zomibe.r + dr[dir];
                int nc = can_zomibe.c + dc[dir];

                if (nr < 0 || nc < 0 || nc >= M) continue;
                if (visited[nr][nc]) continue;
                if (can_lattice[nr][nc] == 1) {
                    kill_zombie[idx][0] = nr;
                    kill_zombie[idx][1] = nc;
                    idx++;
                    return;
                }

                visited[nr][nc] = true;
                q.add(new zombie(nr, nc, can_zomibe.d + 1));
            }
        }
    }
}