package 소프티어;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class 염기서열_커버{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); //염기서열 개수
        int M = sc.nextInt(); //그들의 길이

        char[][] DNA = new char[N][M];

        for(int i =0 ; i<N ; i++){
            for(int j = 0; j<M; j++)
                DNA[i][j] = sc.next().charAt(j);
        }

        List<Character>[] adj = new LinkedList[N];


        for(int j = 0; j< M; j++) {
            for (int i = 0; i < N; i++)
                if (DNA[i][j] != '.'){
                    adj[i].add(DNA[i][j]);
                }
        }

        for(int j = 0; j< M ; j++){
            int i=0; int k =0;

                while (adj[i].get(k) != null){
                if(DNA[i][j] == '.'){

                    DNA[i][j] = adj[i].get(k);
                    i++;
                }
            }
        }


    }
}
