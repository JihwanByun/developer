package SWEA;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class 작업순서 {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);

        for(int testCase = 1; testCase<=10; testCase++){



            int V = sc.nextInt(); //정점의 개수
            int E = sc.nextInt(); // 간선의 개수

            int[][] adj = new int[V+1][V+1];
            int[] degree = new int[V+1];

            for(int i = 0 ; i <E;i++){
                int a = sc.nextInt();
                int b = sc.nextInt();

                adj[a][b] = 1;
                degree[b]++;
            }

            System.out.print("#"+ testCase + " ");

            Queue<Integer> q = new LinkedList<>();
            for(int i = 1 ; i<V+1;i++) {
                if( degree[i] == 0) {
                    q.add(i);
                    degree[i]--;
                }
            }

            while (!q.isEmpty()){
                int cur = q.poll(); //q에서 꺼내와!
                System.out.print(cur +" ");

                for(int i = 1; i <V+1;i++) {
                    if (adj[cur][i] == 1) { //간선이 연결되어 있다
                        adj[cur][i] = 0; //간선 없애자
                        degree[i]--; //해당 차수 내리자
                    }
                    if (degree[i] == 0) {
                        q.add(i);
                        degree[i]--;
                    }

                }
            }
            System.out.println(" ");
        }
    }
}