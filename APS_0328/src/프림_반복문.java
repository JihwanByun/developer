import java.util.Arrays;
import java.util.Scanner;

public class 프림_반복문 {

    static final int INF = Integer.MAX_VALUE;
    //987654321

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int V = sc.nextInt(); //0부터 시작
        int E = sc.nextInt();



        //인정행렬
        int[][] adjArr = new int[V][V];

        for(int i= 0 ; i<E; i++){
            int A = sc.nextInt();
            int B = sc.nextInt();
            int W = sc.nextInt();
            //무향 그래프
            adjArr[A][B] = adjArr[B][A] = W;
        }//입력끝

        //방문처리를 위해 배열 선언
        boolean[] visited = new boolean[V]; //이거 골랐어!
        int[] p = new int[V]; // 내가 어디서 왔는지 이거 문제에 따라서 필요할 수 도 있고, 아닐수도
        int[] dist = new int[V]; //key 라고 했던 가장 작은 비용을 저장하기 위한 배열
        //2차원 배열로도 가능하긴 하다

        //dist 초기화
        for(int i = 0 ; i<V; i++) {
            dist[i] = INF;
//            p[i] = -1;
        }
//        Arrays.fill(dist,INF);

        //임의의 한점을 선택해서 돌려야한다
        dist[0] = 0; //0번 정점부터 할래요
        int ans = 0 ;

        for(int i = 0; i< V; i++ ) { //V로 쓰든 V-1로 쓰든 상관이 읎다!
            int min = INF;
            int idx = -1;
            //아직 안뽑힌 정점들 중 가장 작은 값을 뽑겟다.
            for(int j =0 ; j<V ; j++){
                if(!visited[j] && dist[j] < min){
                    min = dist[j];
                    idx = j;
                }
            } //해당 for문 종료 시 idx: 가장 작은 값을 가지고 있고 방문하지 않은 정점이 선택됨.

            visited[idx] = true; //뽑았어 !
            //뽑은 친구와 인접한 정점들 중 갱신할 수 있으면 갱신
            for(int j = 0 ; j<V; j++){
                if(!visited[j] && adjArr[idx][j] != 0 && dist[j] > adjArr[idx][j]){
                    dist[j] = adjArr[idx][j];
                    p[j] = idx;
                }

            }


        } //정점을 선택하는 사이클
        for(int i = 0 ; i<V; i++){
            ans += dist[i];
        }
        System.out.println(Arrays.toString(dist));
        System.out.println(ans);


    }
}
