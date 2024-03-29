import java.util.*;

public class 다익스트라_반복문 {

    static class Node{
        int v, w;

        public Node(int v, int w) {
            this.v = v;
            this.w = w;
        }
    }
    static final int INF = 987654321;
    static int V,E;
    static List<Node>[] adjList; //인접리스트
    static int[] dist;


    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        V = sc.nextInt();
        E = sc.nextInt();

        adjList = new ArrayList[V];
        for(int i = 0 ; i<V; i++){
            adjList[i] = new ArrayList<>();
        } //초기화 완료


        dist = new int[V];
        Arrays.fill(dist,INF);

        for(int i = 0; i<E; i++){
            //시작정점 도착정점 가중치 순으로 입력이 된다.
            adjList[sc.nextInt()].add(new Node(sc.nextInt(),sc.nextInt()));
        }
        dijsktra(0);

        System.out.println(Arrays.toString(dist));

    }

    private static void dijsktra(int start) {
        boolean[] visited = new boolean[V]; //방문처리
        dist[start] = 0; //시작노드까지의 거리는 0으로 초기화

        //모든 정점을 다 돌 때까지 해보려고 한다. (문제를 보고 도착 정점이 주어졌을 때 거기 방문하면 멈춰!)
        for(int i= 0 ; i<V-1;i++){  //V든 V-1이든 크게 상관은 없다
            int min = INF;
            int idx= -1;
            for(int j= 0 ;j<V;j++){
                if(!visited[j] && min>dist[j]){
                    min = dist[j] ;
                    idx = j;
                }
            }//idx : 방문하지 않았으면서, 시작정점으로부터 해당 idx 정점까지의 거리가 최소인 친구가 잡힘

            // idx -1 ->  시작정점으로부터 갈 수 있는 친구들은 다 방문했어!
            if(idx == -1) break;
            visited[idx] = true; // 선언

            for( Node node : adjList[idx]){
                if(!visited[node.v] && dist[node.v] > dist[idx] + node.w) {
                    dist[node.v] = dist[idx] + node.w;
                }

            }

        }

    }
}
