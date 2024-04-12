package Baekjoon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class 감시 {

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