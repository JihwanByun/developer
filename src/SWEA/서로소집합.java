package SWEA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
    m 연산의 개수
    합집합: (0,a,b) a와 b가 같은 집합에 포함되어 있는 지 확인하는 연산
    (1,a,b) 두 원소가 같은 집합에 포함되어 있는 지 확인하는 연산

 */


public class 서로소집합 {
    static int[] p;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int TestCase = Integer.parseInt(br.readLine());
        for(int Tc = 1; Tc<= TestCase; Tc++){

            StringTokenizer st =new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            // 0은 합하는 연산, 1 두 원소가 같은 집합에 포함되어 있는 지 확인
            int[][] set = new int[m][3];
            for(int i = 0 ; i<m ; i++){
                StringTokenizer st1 = new StringTokenizer(br.readLine());
                for(int j= 0; j<3;j++)
                    set[i][j] = Integer.parseInt(st1.nextToken());
            }
            p = new int[n+1];

            StringBuilder sb = new StringBuilder();
            sb.append("#" + Tc + " ");

            for(int i = 1; i<=n ; i++){
                makeset(i);
            }

            for(int i = 0; i<m; i++){
                if(set[i][0] == 0){
                    // 두 대표자가 다른 경우에만!
                    if(findset(set[i][1]) != findset(set[i][2]))
                        union(set[i][1],set[i][2]);
                }
                else{
                    if(findset(set[i][1]) == findset(set[i][2])) //findset
                        sb.append(1);
                    else sb.append(0);
                }
            }

            System.out.println(sb);

        }
    }

    private static void makeset(int x) {
        p[x] = x;
        //rank는 현재 x
    }

    private static int findset(int x) {
        if(x != p[x]) //스스로가 대표자가 아니라면
            p[x] = findset(p[x]); // p[x]의 대표자의 자리를 p[x]값을 넣는다.
        return p[x];
    }
    private static void union(int x, int y){
        p[findset(y)] = findset(x);
    }
}
