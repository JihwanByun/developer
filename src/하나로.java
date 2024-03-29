import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

// edge 클래스만 이용
// island 클래스 없이 재작성 => 0, 1, 2, ..., N-1을 번호로 해서 노드 구별

// int[] x, int[] y
// x[0]: 0번 노드의 x 좌표
// y[0]: 0번 노드의 y 좌표

// int[] p : 부모 노드의 인덱스를 기억하는 배열


public class 하나로 {
    static int N;
    static int[] p ;
    static double min_cost;
    static double E;

    static int[] x, y; // x, y 좌표값의 배열
    static boolean[] visited;

    public static class edge implements Comparable<edge>{


        int A; // 시작점 노드 번호
        int B; // 끝점 노드 번호

        double cost;

        edge(int A, int B){
            this.A = A;
            this.B = B;
            this.cost = Math.pow(Math.abs(x[A]-x[B]),2)+ Math.pow(Math.abs(y[A]-y[B]),2);
        }

        @Override
        public int compareTo(edge o) { //오름차순
            return (int) (this.cost - o.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));

        int TestCase = Integer.parseInt(br.readLine());

        for(int Tc =1; Tc<= TestCase;Tc++){
            N = Integer.parseInt(br.readLine());
            x = new int[N];
            y = new int[N];
            min_cost =0;

            StringTokenizer st =new StringTokenizer(br.readLine());
            for(int i = 0 ; i<N; i++){
                x[i] = Integer.parseInt(st.nextToken());
            }
            StringTokenizer st1 = new StringTokenizer(br.readLine());
            for(int i = 0 ; i<N ; i++){
                y[i] = Integer.parseInt(st1.nextToken());
            } //맵 입력 완!

            List<edge> edge_list=  new ArrayList<>();

            for(int i = 0 ; i <N; i++){
                for(int j = i+1 ;j<N; j++ )
                    edge_list.add(new edge(i, j));
            }

            Collections.sort(edge_list);
            //간선리스트 오름차순 정렬 완료


            p = new int[N+1];

            for(int i =0; i<N;i++) {
                makeset(i);
            }
            
            for(int i = 0 ; i< N*(N-1)/2 ;i++){
                if(findset(edge_list.get(i).A) != findset(edge_list.get(i).B)) {
                    union(edge_list.get(i).A, edge_list.get(i).B);
                    min_cost += edge_list.get(i).cost;

                }
            }

            E = Double.parseDouble(br.readLine());

            double ans = Math.round(E * min_cost);

            System.out.println("#" + Tc + " " + (long) ans);

        }

    }

    private static void makeset(int x) {
        p[x] = x;
    }


    private static void union(int x ,int y) {
        p[findset(y)] = p[findset(x)];
    }

    private static int findset(int x) {
        if(p[x] ==x ) return x;

        p[x] = findset(p[x]);

        return p[x];
    }
}
