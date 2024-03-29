import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class 프림_우선순위큐 {
    static final int INF = Integer.MAX_VALUE;

    static class Edge implements Comparable<Edge>{
        int st, ed, w;

        public Edge(int st, int ed, int w){
            this.st = st;
            this.ed = ed;
            this.w = w;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.w,o.w);
        }
    }

    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);

        int V = sc.nextInt();
        int E = sc.nextInt();

        //인접리스트
        List<Edge>[] adjList = new ArrayList[V];

        //실제 담을 수 있는 바구니 준비
        for(int i= 0 ; i<V; i++){
            adjList[i] = new ArrayList<>();
        }
        for(int i= 0 ; i<E; i++){
            int A = sc.nextInt();
            int B = sc.nextInt();
            int W = sc.nextInt();

            adjList[A].add(new Edge(A,B,W));
            adjList[B].add(new Edge(B,A,W));
        }

        //방문처리

        boolean[] visited = new boolean[V];

        PriorityQueue<Edge> pq = new PriorityQueue<>();

        visited[0] = true; // 0번 정점은 시작


        //0번 정점과 인접한 친구들을 몽땅 넣어
//        for(int i = 0 ; i< adjList[0].size(); i++){
//            pq.add(adjList[0].get(i));
//        }
//        for(Edge e : adjList[0]){
//            pq.add(e);
//        }

        pq.addAll(adjList[0]);
        int pick = 1; //현재 확보한 정점의 개수
        int ans = 0; //비용도 0

        while (pick != V){
            Edge e = pq.poll();
            if(visited[e.ed]) continue; //이미 해당정점이 방문한 점이라면..

            ans += e.w; //해당 간선이 가지고 있는 가중치를 더한다.
            visited[e.ed] = true;
            pick++;

            //반복문을 돌면서 갱신할 수 있는 거 전부 갱신
            pq.addAll(adjList[e.ed]);
        }
        System.out.println(ans);





    }
}
