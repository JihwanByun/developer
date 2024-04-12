package 수업내용;

import java.util.Arrays;
import java.util.Scanner;

public class 동적계획법_01_피보나치 {
    static int[] callFibo = new int[100];
    static int[] memo ; //계산값 저장

    static {
        memo = new int [1000];
        memo[0] = 0;
        memo[1] = 1;

        //여기서 직접 다 해놓고 부를 수 있음
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();

        memo = new int[1000];
        Arrays.fill(memo,-1);
        memo[0] = 0; //이거 필요?
        memo[1] = 1;

        System.out.println(fibo1(N));
    }


    public static int fibo1(int n ){
//        if(n==0) return 0; T(0) == 1
//        if(n==1) return 1; T(1) == 1
        if(n<=1) return n;

        return fibo1(n-1)+ fibo1(n-2);
    }

    public static int fibo2(int n ){
        if(memo[n] == -1){
            memo[n] = fibo2(n-1) + fibo2(n-2);
        }
        return memo[n];
    }


    public static int fibo3(int n){
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        for(int i = 2; i<=n;i++)
            dp[i] = dp[i-2] + dp[i-1];
        return dp[n];
    }
}

