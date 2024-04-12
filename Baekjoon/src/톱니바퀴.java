package Baekjoon;


import java.util.Arrays;
import java.util.Scanner;

/*
    톱니바퀴 총 K번 회전, 시계 or 반시계
    회전시킬 톱니바퀴, 방향 정하기

    맞닿은 극이 같으면 움직이지 않는다.
    극이 서로 다르다면 움직이는 톱니바퀴의 반대방향으로 움직인다.
    첫 입력, N극 : 0 S극 : 1   12시부터 시계방향순

    회전 횟수
    이후 회전시킨 방법, 톱니바퀴 번호, 방향

    출력, 12시 극의 값에 따라 점수 배분

    회고록
    왼쪽과 오른쪽 각각을 따로 메서드로 만들어서 재귀형식 구현,
    왼쪽 오른쪽 모두 다른 극일 경우 두 번 돌 수 있으므로 한 번 반대방향으로 돌려주기.

    1)시계방향, 반시계 방향 회전을 단순히 arr[i+1] = arr[i]로 해버린..
    그러면 계속 갱신돼서 결국 같은 숫자만 나올텐데..
    이걸 생각 못하다니..

    2)left랑 right..idx 반대로 썼는데.. 가장 기본적인 걸 틀리면 어떡해
    다음거 돌릴 지 말 지는 left, right 숫자 비교 미리하고 하는거지, 돌리고나서 비교하는 게 아니라

    3)아 톱니바퀴를 돌리기 전에 기준으로 고려하고 돌린 후는 마지막에 update 해야하는구나...

     */
public class 톱니바퀴 {


    static gear[] gears = new gear[5];
    private static class gear{
        int[] part = new int [8];
        int left;
        int right;


    }

    public static void main(String[] args) {

        Scanner sc =new Scanner(System.in);

        gear gear1 = new gear();
        gear gear2 = new gear();
        gear gear3 = new gear();
        gear gear4 = new gear();

        String st = sc.next();
        for(int i = 0; i<8;i++) {
            gear1.part[i] = st.charAt(i) - '0';
        }
        st = sc.next();
        for(int i = 0; i<8;i++) {
            gear2.part[i] = st.charAt(i) - '0';
        }
        st = sc.next();
        for(int i = 0; i<8;i++) {
            gear3.part[i] = st.charAt(i) - '0';
        }
        st = sc.next();
        for(int i = 0; i<8;i++) {
            gear4.part[i] = st.charAt(i) - '0';
        }
        int  K = sc.nextInt();

        gears[1] = gear1;
        gears[2] = gear2;
        gears[3] = gear3;
        gears[4] = gear4;

       for(int i = 1 ; i<= 4; i++){
           gears[i].left = gears[i].part[6];
           gears[i].right = gears[i].part[2];
       }
        for(int i = 0; i<K;i++){
            int num = sc.nextInt();
            int dir = sc.nextInt();

            canMove(num,dir);
            update();
        }


        //N극 0, S극 1
        int ans = 0 ;
        for(int i = 1; i<=4;i++){
            if(gears[i].part[0] == 1){
                ans += Math.pow(2,i-1);
            }
        }
        System.out.println(ans);
    }

    private static void clockwise(gear gr){
        int[] copy =  Arrays.copyOf(gr.part,8);
        int end = gr.part[7];

        for(int i = 1 ; i<= 7;i++){
            gr.part[i] = copy[i-1];
        }
        gr.part[0] = end;

    }
    private static void reverseclockwise(gear gr){
        int[] copy =  Arrays.copyOf(gr.part,8);
        int end = gr.part[0];

        for(int i = 0 ; i< 7;i++){
            gr.part[i] = copy[i+1];
        }
        gr.part[7] = end;
    }

    private static void update(){
        for(int i = 1 ; i<=4;i++) {
            gears[i].right = gears[i].part[2];
            gears[i].left = gears[i].part[6];
        }
    }
    private static void canMoveleft(int idx, int dir) { //시계 1, 반시계 -1
        if(idx <=1  ){
            if(dir == 1) { //시계였다면
                clockwise(gears[idx]);
            }
            if(dir == -1) { //반시계였다면
                reverseclockwise(gears[idx]);
            }
            return;
        }
        boolean possible = true;
        if (gears[idx - 1].right == gears[idx].left) possible = false;

        if(dir == 1) { //시계였다면
            clockwise(gears[idx]);
            if(possible)
                canMoveleft(idx-1,-1);
        }
        if(dir == -1) { //반시계였다면
            reverseclockwise(gears[idx]);
            if(possible)
                canMoveleft(idx-1,1);
        }
    }
    private static void canMoveright(int idx, int dir) { //시계 1, 반시계 -1
        if(idx >= 4 ) {
            if(dir == 1) { //시계였다면
                clockwise(gears[idx]);
            }
            if(dir == -1) { //반시계였다면
                reverseclockwise(gears[idx]);
            }
            return;
        }
        boolean possible = true;
        if(gears[idx].right == gears[idx + 1].left) possible = false;

        if(dir == 1) { //시계였다면
            clockwise(gears[idx]);
            if(possible)
                canMoveright(idx+1,-1);
        }
        if(dir == -1) { //반시계였다면
            reverseclockwise(gears[idx]);
            if(possible)
                canMoveright(idx+1,1);
        }

    }
    private static void canMove(int idx , int dir){

        if (dir == 1)
            clockwise(gears[idx]);
        if (dir == -1)
            reverseclockwise(gears[idx]);

        if(idx >1 && idx < 4) { //2랑 3일 때
            if (gears[idx + 1].left != gears[idx].right && gears[idx].left != gears[idx - 1].right) { //둘다 다를 때
                    canMoveright(idx + 1, dir*-1);
                    canMoveleft(idx - 1, dir*-1);
            }
             else if (gears[idx].left != gears[idx - 1].right && gears[idx + 1].left == gears[idx].right) {//왼쪽 방향만 기준 달라
                 canMoveleft(idx - 1, dir*-1);

            } else if (gears[idx + 1].left != gears[idx].right && gears[idx].left == gears[idx - 1].right) {//오른쪽 방향만 기준 달라
                 canMoveright(idx + 1, dir*-1);
            }
        }
        if (idx == 1) {
            if (gears[idx].right != gears[idx + 1].left) {
                canMoveright(idx + 1, dir*-1);
            }
        }
        if (idx == 4) {
            if (gears[idx].left != gears[idx - 1].right) {
                canMoveleft(idx - 1, dir*-1);
            }
        }
    }
}



