package Baekjoon;


import java.util.Arrays;
import java.util.Scanner;

/*
최악 코스와 최고 코스의 피로도 차이 출력
오르막길 횟수 최소, 오르막길 횟수 최대
최악코스, 최대코스

0은 오르막길, 1은 내리막길
 */
public class 학교탐방하기 {
    static int N,M;
    static int[][] road;
    static int[] tired;

    static int sum,Max,Min;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt(); //건물의 개수
        M = sc.nextInt(); //도로의 개수
        road = new int[N+1][2];
        tired = new int[N+1];
        Arrays.fill(tired,Integer.MAX_VALUE);
        for(int i = 0 ; i<=M;i++){

        }


    }
    static void prim(){
        tired[0] = 0;
        
    }

}
