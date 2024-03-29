package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

public class 보급로 {


    public static class Node implements Comparable<Node>{
        int r,c,w;

        public Node(int r,int c, int w) {
            this.r = r;
            this.c = c;
            this.w = w;
        }

        @Override
        public int compareTo(Node o) {
            return this.w - o.w;
        }
    }


    static int[][] road, ans_road;
    static boolean[][] visited;
    static int[] dr = {0,1,0,-1};
    static int[] dc = {1,0,-1,0};
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));

        int Testcase = Integer.parseInt(br.readLine());

        for(int Tc =1; Tc<= Testcase; Tc++){
            N = Integer.parseInt(br.readLine());

            road = new int[N][N];
            visited = new boolean[N][N];
            ans_road = new int[N][N];

            for(int i= 0; i<N;i++){
                String st = br.readLine();
                for(int j = 0 ;j<N;j++){
                    road[i][j] = st.charAt(j) - '0';
                }
            }
            for(int i = 0 ; i<N; i++){
                for(int j = 0; j<N; j++){
                    ans_road[i][j] =9876541;
                }
            }
            ans_road[0][0] = 0;


            dikstra(0,0);

            // (0,0)에서 시작, (N-1,N-1)이 끝나는 지점.

            System.out.println("#" + Tc + " "+ ans_road[N-1][N-1]);
//            for(int i = 0; i<8 ;i++)
//            System.out.println(Arrays.toString(ans_road[i]));


        }
    }
    private static void dikstra(int r, int c){

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(0,0,0));
        while (!pq.isEmpty()){
            Node cur = pq.poll(); //w가 가장 작은애부터 나온다.

            if(visited[cur.r][cur.c]) continue;
            visited[cur.r][cur.c] = true;

            for(int dir = 0; dir<4;dir++) {
                int nr = cur.r + dr[dir];
                int nc = cur.c + dc[dir];
                if(nr >= N || nr <0 || nc >= N || nc<0 ) continue;

                if(ans_road[nr][nc] > ans_road[cur.r][cur.c]+road[nr][nc])
                    ans_road[nr][nc] = ans_road[cur.r][cur.c]+road[nr][nc];

                pq.add(new Node(nr,nc,ans_road[nr][nc]));
            }
        }
    }
}