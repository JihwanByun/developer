package SWEA;

public class 보호필름 {


    public void makeFilm(int row, int cnt) {
        //주입 X
        makeFilm(row+1,cnt);

        //약품을 주입하기 전에 원상복구를 해야하니 메모리를 할당해서 저장을 해두자

        //주입 A
        makeFilm(row+1,cnt+1);
        //주입 B
        makeFilm(row+1,cnt+1);
    }

    private void yak(int row, int drug){ //어떤 줄에

    }



}