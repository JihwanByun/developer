package Baekjoon;


/*
(x,y,d,g) 시작점의 x, y, d, g 세대
d : 시작 방향
우 상 좌 하 ( 0,1,2,3 )
0 <= x, y <= 100 , 0<= d <=3 , 0<= g < 10)
커브의 개수 N (1 <= N <= 20)
*격자밖으로 벗어나지 않는다 -> 경계조건 고려하지 않아도 된다.
*드래곤 커브는 서로 겹칠 수 있다 -> visited 고려 안해도 된다.

처음 한 줄은 0세대 부터,
1세대 증가할 때마다, 끝점기준 시계방향 회전한 뒤 끝점에 이어 붙이기

출력
크기가 1×1인 정사각형의 네 꼭짓점이 모두 드래곤 커브의 일부인 것
--> 문제 잘못 해석,,꼭짓점만 일부이면 된다, 모서리는 일부일 필요 X


사고과정
노드 사용하기 ( 각 점의 연결을 표현해야함 )
규칙성 찾기 --> 기준점이 중요함
기준점을 어떻게 끝에서부터, 시작해서 90도씩 회전하면서 이어붙이기

의문)
각 노드가 갖는 List, String, Array는 각자 초기화해서 추가해야한다. List

노드를 다 찾긴 했는데..사각형 간선 확인 어떻게 ?
리스트로 하려니 너무 힘들다, 각 노드가 갖는 노드들을 모두 찾았지만
이를 사각형의 형태로 다시 이으려면 별다른 작업이 별도로 필요함!



--> 다른 방법으로 풀 것.



 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class 드래곤커브 {
    private static class node{
        int y;    //[y][x]순으로 담기
        int x;
        List<node> linkedNode;
        node(int y, int x,List<node> linkedNode){
            this.y= y;
            this.x= x;
            this.linkedNode = linkedNode;
        }
        node(int y, int x){
            this.y= y;
            this.x= x;

        }

    }
    static int[][] map;
    static List<node> nodelist;
    public static void main(String[] args) throws IOException {
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        nodelist = new ArrayList<>();
        map = new int[101][101];
        for(int i = 0 ; i<N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            node node = new node(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()), new ArrayList<>());
            int d = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());
            nodelist.add(node);
            link_Node(node,d,g);
        }
        int k = nodelist.size();

        for(int n = 0 ; n<k ;n++) {
            System.out.println(nodelist.get(n).y + " " + nodelist.get(n).x);
        }
    }

    private static void link_Node(node node, int d, int g){

        decide_dir(node,d); //처음건 연결된 상태

        Stack<node> stk = new Stack<>();

        stk.add(node); //처음거 넣기
        stk.add(node.linkedNode.get(0)); //0세대 끝점 넣기
        //2개 넣고 시작!

        node nodes = node.linkedNode.get(0);
        //0세대 연결하기!

        if( g>= 1) {//1세대 가보자
            node g1_start = stk.pop();
            //마지막까지 넣어놓은 거 빼기

            node g1_end = stk.peek();
            //뒤로 가기 하는데 그 이전걸 보자

            node link_node = change_dir(g1_start, g1_end.y - g1_start.y, g1_end.x - g1_start.x);
            //시계돌린거
            nodelist.add(link_node);

            g1_start.linkedNode.add(link_node);
            link_node.linkedNode.add(link_node);
            //서로 연결시키기

            stk.add(link_node);
            //1세대 끝점 넣기
        }
        //2세대부터 가보자
        for(int i = 2 ; i<= g ;i++) {
            for(int j = 0 ; j<2*i;j++) {

                node startnode = stk.pop();
                //마지막까지 넣어놓은 거 빼기

                node curnode = stk.peek();
                //뒤로 가기 하는데 그 이전걸 보자

                node link_node = change_dir(startnode, curnode.y - startnode.y, curnode.x - startnode.x);
                //시계돌린거
                nodelist.add(link_node);

                startnode.linkedNode.add(link_node);
                link_node.linkedNode.add(link_node);
                //서로 연결시키기

                stk.add(link_node);
            }
        }

    }
    private static void decide_dir(node node,int d) { //처음 건 넣어준다
        if (d == 0) {//x가 증가하는 방향
            node.linkedNode.add(new node(node.y,node.x+1,new ArrayList<>()));
            nodelist.add(node.linkedNode.get(0));
        }
        else if(d == 1){ //y가 감소하는 방향
            node.linkedNode.add(new node(node.y-1,node.x,new ArrayList<>()));
            nodelist.add(node.linkedNode.get(0));
        }
        else if(d==2){ //x가 감소하는 방향
            node.linkedNode.add(new node(node.y,node.x-1,new ArrayList<>()));
            nodelist.add(node.linkedNode.get(0));
        }
        else if(d==3){
            node.linkedNode.add(new node(node.y+1,node.x,new ArrayList<>()));
            nodelist.add(node.linkedNode.get(0));
        }

    }
    private static node change_dir(node startNode,int dy,int dx){
        if(dy == 1 && dx == 0)
            return new node(startNode.y, startNode.x+1,new ArrayList<>());
        if(dy == 0 && dx == 1)
            return new node(startNode.y-1, startNode.x,new ArrayList<>());
        if(dy == -1 && dx == 0)
            return new node(startNode.y, startNode.x-1,new ArrayList<>());

//        if(dy == 0 && dx == -1)
        return new node(startNode.y+1, startNode.x,new ArrayList<>());
    }
}


