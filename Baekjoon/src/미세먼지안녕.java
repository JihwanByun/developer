package Baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 미세먼지안녕 {


    private static class cleaner{
        int r ;
        int c ;
        cleaner(int r, int c){
            this.r = r;
            this.c = c;
        }
    }
    static int[][] room;
    static int R,C,T; //각각 행, 열 ,시간
    static int[] dr = {0,1,0,-1};
    static int[] dc = {1,0,-1,0};

    static int[] updr = {0,-1,0,1};
    static int[] updc = {1,0,-1,0};

    static int[] downdr = {0,1,0,-1};
    static int[] downdc = {1,0,-1,0};

    static cleaner[] cleaners;

    public static void main(String[] args) throws IOException {
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st =new StringTokenizer(br.readLine());

        cleaners = new cleaner[2];
        int ans = 0 ;
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        room =new int[R][C];

        int k = 0;
        for(int i = 0 ; i <R; i++){
            st =new StringTokenizer(br.readLine());
            for(int j = 0 ; j<C ;j++){
                room[i][j] = Integer.parseInt(st.nextToken());
                if(room[i][j] == -1){
                    cleaners[k] = new cleaner(i,j);
                    k++;
                }
            }
        }
        for(int time = 0; time<T;time++){
            diffuse_dust();
            clean_room_up();
            clean_room_down();
        }

        for(int i= 0 ; i<R;i++)
            for(int j = 0 ; j<C;j++)
                if(room[i][j] > 0) ans += room[i][j];
        
        System.out.println(ans);
    }
    private static void diffuse_dust(){
        int[][] copy =new int[R][C];

        for(int i = 0 ; i<R;i++)
            copy[i] = room[i].clone();

        for(int i = 0 ;i<R;i++){
            for(int j = 0 ; j<C;j++){
                if(room[i][j] != 0){
                    int divide = room[i][j] / 5;
                    for(int dir = 0; dir<4;dir++){
                        int nr = i + dr[dir];
                        int nc = j + dc[dir];

                        if(nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
                        if(room[nr][nc] == -1) continue;

                        copy[i][j] -= divide;
                        copy[nr][nc] += divide;
                    }

                }
            }
        }
        for(int i = 0 ; i<R;i++)
            room[i] = copy[i].clone();
    }

    private static void clean_room_up() {
        int[][] copy = new int[R][C];

        for (int i = 0; i < R; i++)
            copy[i] = room[i].clone();

        int nr = cleaners[0].r;
        int nc = cleaners[0].c;

        copy[nr+updr[0]][nc+updc[0]] = 0;

        for (int dir = 0; dir < 4; dir++) {
            while (true) {
                nr += updr[dir];
                nc += updc[dir];
                if (nr < 0 || nr >= R || nc < 0 || nc >= C) break;

                int curR = nr + updr[dir];
                int curC = nc + updc[dir];

                if (curR < 0 || curR >= R || curC < 0 || curC >= C) {
                    if (dir < 3) {
                        copy[nr + updr[dir + 1]][nc + updc[dir + 1]] = room[nr][nc];
                        break;
                    }
                }
                if (room[curR][curC] == -1) {
                    room[nr][nc] = 0;
                    for(int i =0 ; i<R;i++)
                        room[i] = copy[i].clone();
                    return;
                }

                copy[curR][curC] = room[nr][nc];
            }
        }

    }
    private static void clean_room_down() {
        int[][] copy = new int[R][C];

        for (int i = 0; i < R; i++)
            copy[i] = room[i].clone();

        int nr = cleaners[1].r;
        int nc = cleaners[1].c;
        
        copy[nr+downdr[0]][nc+downdc[0]] = 0;
        for (int dir = 0; dir < 4; dir++) {
            while (true) {
                nr += downdr[dir];
                nc += downdc[dir];
                if (nr < 0 || nr >= R || nc < 0 || nc >= C) break;

                int curR = nr + downdr[dir];
                int curC = nc + downdc[dir];
                    if (curR < 0 || curR >= R || curC < 0 || curC >= C) {
                        if (dir < 3) {
                            copy[nr + downdr[dir + 1]][nc + downdc[dir + 1]] = room[nr][nc];
                            break;
                        }
                    }

                    if (room[curR][curC] == -1) {
                        room[nr][nc] = 0;
                        for(int i =0 ; i<R;i++)
                            room[i] = copy[i].clone();
                        return;
                    }
                    copy[curR][curC] = room[nr][nc];
                }
        }
        for(int i =0 ; i<R;i++)
            room[i] = copy[i].clone();
    }
}
