import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class SWEA_0326치즈도둑 {

    public static class Node{
        int r ;
        int c;
        int d;

        Node(int r, int c){
            this.r = r;
            this.c = c;
        }
    }
    static int[][] cheese;
    static boolean[][] angle_eat_cheese, visited;
    static int day, bigcheese, Maxbigcheese, N;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    static int idx;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int TestCase = Integer.parseInt(br.readLine());

        for (int Tc = 1; Tc <= TestCase; Tc++) {

            N = Integer.parseInt(br.readLine());

            cheese = new int[N][N];
            visited = new boolean[N][N];
            day = 0;
            bigcheese = 0;
            Maxbigcheese = 0;
            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    cheese[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            CheesebyDay(0);
            System.out.println("#" + Tc+ " " + Maxbigcheese);

        }
    }

    public static void CheesebyDay(int day) {
        if (day == 101) {

            return;
        }
        boolean[][] visited1 = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (cheese[i][j] <= day) visited1[i][j] = true;
            }
        }
        bigcheese =0;

        for(int r= 0 ; r<N; r++){
            for(int c=0 ; c<N; c++){
                if(visited1[r][c]) continue;
                BFS1(r, c,visited1);
            }
        }
        if (Maxbigcheese < bigcheese) {
            idx = day;
            Maxbigcheese = bigcheese;
        }
        CheesebyDay(day+1);
    }

    public static void BFS1( int r, int c, boolean[][] visited) {

        Queue<Node> q = new LinkedList<>();
        Node node = new Node(r,c);
        bigcheese++;
        q.add(node);

       while(!q.isEmpty()){
           Node nodeCheese = q.remove();
//                if(nodeCheese.d > d) continue;
                 //방문한 곳이면 잘가라
                for (int dir = 0; dir < 4; dir++) {
                    int nr = nodeCheese.r + dr[dir];
                    int nc = nodeCheese.c + dc[dir];
                    if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                    if (visited[nr][nc]) continue;

                    visited[nr][nc] = true;
                    q.add(new Node(nr,nc));
                }

       }

    }

}



