package Baekjoon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;/*
N x M 직사각형, 총 K개 CCTV 5종류
1: 한 방향
2: 반대 두 방향
3: 직각 두 방향
4: 세 방향
5: 네 방향 모두

CCTV는 벽 통과 못함, CCTV 감시 X  -> 사각지대
CCTV끼리는 통과 가능
CCTV 회전 가능, 항상 90도, 감시 방향 가로 or 세로

6: 벽, CCTV가 못 뚫어

입력
1<= N , M <= 8
CCTV cnt <= 8

출력
ans = 결국 사각지대의 최소 크기 구하기

사고과정
배치 위치는 못 바꾸네
회전으로 판단 회전 가능한 경우는 1,2,3,4
5는 회전 필요 X

최대한 많은 곳을 check 해야함 ( 중복 없이 )
Check 유무 판단 7로 하자

돌고 나서 0으로 바꿀 것인지,
아니면 있는 거에 새로 초기화해서 씌워서 할 지


난 좌표만 받으면 돼!
과오 1)
조건식 if(office[r][c] != 0 && office[r][c] != 6 && office[r][c] != 7 //암것도 없는, 벽, 체크
논리구조 정확히 세워야한다..&& ||

과오 2)
지우는 게 이상함을 감지..남의 CCTV까지 지워버리는 사태가 발생했다..
-->CCTV 클래스를 만들어서 각 CCTV가 갖는 visited 를 제작하기

과오 3)
클래스 객체를 하나씩 만들었는데 왜 cctv의 scope를 공유하는 거지???
-> 참조값....주소를 공유시켜놓고 왜 같은 주소로 찾아간다고 뭐라하는거야
따로 주소를 만들고 싶으면 new 객체로 새로 만들어 줘야지 멍청아

과오 4)
IndexOutofBounds.. CCTV가 없을 때도 고려해야되네 ^^ 진짜 개열받네

과오 5)
95%에 틀렸습니다? 뭔데 ...뭐냐고 뭐냐고 뭐냐고!!!!!!!!!!!!!!!!!!!!!!
if(check > maxCheck) { //max check 첨에 갱신..
                maxCheck = check;
                blindSpotCnt = 0;
 */


public class 감시_회고록 {

    private static class cctv{
        int r;
        int c;

        boolean[][] scope;
        int type;
        cctv(int type ,int r, int c, boolean[][] scope){
            this.type = type;
            this.r = r;
            this.c = c;
            this.scope = scope;

        }

    }
    static int N,M,blindSpot_minCnt,blindSpotCnt,check,maxCheck;
    static int[][] office;


    static List<cctv> cctvlist;
    static int[] dr = {0,1,0,-1};
    static int[] dc=  {1,0,-1,0};
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        office = new int[N][M];
        blindSpot_minCnt =N*M;
        check= 0;
        maxCheck = -1;
        cctvlist = new ArrayList<>();

        for(int i = 0 ; i<N;i++){
            for(int j = 0 ; j< M; j++){
                office[i][j] = sc.nextInt();
                if(office[i][j] >= 1 && office[i][j]<=5){
                    cctvlist.add(new cctv(office[i][j],i,j, new boolean[N][M]));
                }
            }
        }//cctv가 있을 때
        if(!cctvlist.isEmpty()) {
            dfs(cctvlist.get(0), 0);

            System.out.println(blindSpot_minCnt);
            return;
        }
        //cctv가 없을 때
        for(int i= 0; i<N;i++) {
            for (int j = 0; j < M; j++) {
                if (office[i][j] == 0) blindSpotCnt++;
            }
        }
        if(blindSpotCnt < blindSpot_minCnt)
            blindSpot_minCnt = blindSpotCnt;

        System.out.println(blindSpot_minCnt);
    }
    private static void dfs(cctv cctv, int idx){
        if(idx >= cctvlist.size()){
            if(check > maxCheck) {
                maxCheck = check;
                blindSpotCnt = 0;
                for(int i= 0; i<N;i++) {
                    for (int j = 0; j < M; j++) {
                        if (office[i][j] == 0) blindSpotCnt++;
                    }
                }
                if(blindSpotCnt < blindSpot_minCnt)
                    blindSpot_minCnt = blindSpotCnt;
            }
            return;
        }
        cctv= cctvlist.get(idx);
        for(int dir = 0 ; dir<4;dir++){
            on_kindofCCTV(cctv.type,dir, cctv);
            dfs(cctv,idx+1);
            off_kindofCCTV(cctv.type,dir,cctv);
        }
    }
    private static boolean checkOthercctv(int nr, int nc, cctv cctv){
        for(int i = 0; i<cctvlist.size();i++){
            if(cctvlist.get(i) == cctv) continue; //같은 거 말고

            if(cctvlist.get(i).scope[nr][nc]) return true; //다른애들이 해놓은 곳이면 넘어가
        }
        return false; //넣어도 된다.
    }

    private static void check(int x, int dir, cctv cctv){
        int nr = cctv.r;
        int nc = cctv.c;
        while (true) {
            nr +=  dr[(dir+x)%4];
            nc +=  dc[(dir+x)%4];

            if(nr >= N || nr < 0 || nc >= M || nc < 0) break;
            if(office[nr][nc] == 6 ) break;

            if(1<= office[nr][nc] && office[nr][nc]<=5 ) continue;
            if(checkOthercctv(nr,nc,cctv)) continue;

            check++;
            office[nr][nc] = 7;
            cctv.scope[nr][nc] =true;
        }
    }
    private static void off(int x, int dir, cctv cctv){
        int nr = cctv.r;
        int nc = cctv.c;
        while (true) {
            nr += dr[(dir+x)%4];
            nc += dc[(dir+x)%4];

            if (nr >= N || nr < 0 || nc >= M || nc < 0) break;
            if (office[nr][nc] == 6) break;

            if(1<= office[nr][nc] && office[nr][nc]<=5 ) continue;

            //내가 해놓은 곳
            if(cctv.scope[nr][nc]){
            check--;
            office[nr][nc] = 0;
            cctv.scope[nr][nc] = false;
            }
        }
    }
    private static void off_kindofCCTV(int num, int dir, cctv cctv){ //방향에 간 곳이 들어있다
        if( num == 1){
            for(int x = 0; x<1;x++) {
                off(x,dir,cctv);
            }
        }
        else if (num == 2){
            for(int x = 0 ; x <=2 ;x+=2 ) {
                off(x,dir,cctv);
            }
        }
        else if ( num == 3){
            for(int x = 0 ; x <2 ;x++) {
                off(x,dir,cctv);
            }
        }
        else if(num == 4){
            for(int x = 0 ; x <3 ;x++ ) {
                off(x,dir,cctv);
            }
        }
    }
    private static void on_kindofCCTV(int num, int dir, cctv cctv){
        if( num == 1){
            for(int x = 0; x<1;x++) {
                check(x,dir,cctv);
            }
        }
        else if (num == 2){
            for(int x = 0 ; x <=2 ;x+=2 ) {
                check(x,dir,cctv);
            }
        }
        else if ( num == 3){
            for(int x = 0 ; x <2 ;x++) {
                check(x,dir,cctv);
            }
        }
        else if(num == 4){
            for(int x = 0 ; x <3 ;x++ ) {
                check(x,dir,cctv);
            }
        }
        else if(num == 5) {
            for (int x = 0; x < 4; x++) {
                check(x,dir,cctv);
            }
        }
    }
}