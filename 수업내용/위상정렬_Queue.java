package 수업내용;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class 위상정렬_Queue {
    public static void main(String[] args) {

        String[] cook = {"", "재료구매", "양념장만들기", "고기재우기", "고기손질", "제육볶음만들기", "식사", "뒷정리", "채소손질", "밥하기"};

        Scanner sc = new Scanner(System.in);

        int V = sc.nextInt(); //정점의 개수
        int E = sc.nextInt(); // 간선의 개수

        int[][] adj = new int[V + 1][V + 1];
        int[] degree = new int[V + 1]; //진입차수 저장

        for (int i = 0; i < E; i++) {
            int A = sc.nextInt();
            int B = sc.nextInt();

            adj[A][B] = 1; //가중치가 따로 없기 때문에 1로 표기, 유향이니 반대는 처리 X
            //진입차수를 증가 (일종의 진입 장벽이라고 이해하면 편할 거 같다!)
            degree[B]++;

        }
        Queue<Integer> queue = new LinkedList<>();

        //큐로 위상정렬 구현 1단계
        //진입차수 0인 정점들을 넣어
        for(int i =0; i<V+1; i++){
            if(degree[i] == 0){
                queue.offer(i);   //add(),offer() 차이 예외 발생 유무
            }
        }
        //이러면 입력받은 값에서 먼저 입력받은 녀석들이 유리하겟네.
        StringBuilder sb = new StringBuilder();


        //큐로 위상정렬 구현 2단계
        //큐가 공백상태가 될 때까지 돌린다.
        while (!queue.isEmpty()){
            //2-1 하나 꺼내
            int curr = queue.poll();
            sb.append(cook[curr]+"->");
            //2-2. 연결되어 있는 간선을 제거 ( 말은 제거지만 실제로 제거하진 않음)
            for(int i = 0; i< V+1; i++){
                if(adj[curr][i] ==1){//유향에선 적어주는 순서 중요함
                    degree[i]--; //진입차수를 하나 깎는다.
                    adj[curr][i] = 0; //이건 실제로 간선을 삭제해버리는 것

                    //2-3. 진입 차수가 새롭게 0이 되었다면 큐에 넣어!
                    if(degree[i] == 0)
                        queue.offer(i);

                }
            }
        }
        System.out.println(sb);
    }
}
