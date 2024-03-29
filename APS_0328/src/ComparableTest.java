import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class ComparableTest
{
    static class Edge implements Comparable<Edge> {
        int A, B, W;
        public Edge(int a, int b, int w) {
            A = a;
            B = b;
            W = w;
        }
        @Override
        public String toString() {
            return "Edge [A=" + A + ", B=" + B + ", W=" + W + "]";
        }

        //정렬 기준(비교기준)을 만드는 것임
        // -> Arrays.sort, Collections.sort, PriorityQueue에서 사용
        // this : 현재 선택된 객체 자기 자신
        // o : 비교대상

        // this - o => 오름차순
        // o - this => 내림차순

        // 병합정렬에서 두 수를 비교할 때
        // j-1, j 번째 두 수를 비교
        // j-1: this
        // j : o
        @Override
        public int compareTo(Edge o) {
            return o.W - this.W;
        }

    }

    public static void main(String[] args) {
//        List<Edge> list = new ArrayList<>();
//        list.add(new Edge(1, 1, 10));
//        list.add(new Edge(1, 1, 23));
//        list.add(new Edge(1, 1, 5));
//        list.add(new Edge(1, 1, 103));
//
//
//        Collections.sort(list);
//
//        System.out.println(list);

//        PriorityQueue<Edge> pq = new PriorityQueue<>(); // 최소힙
        PriorityQueue<Edge> pq = new PriorityQueue<>(Collections.reverseOrder()); // 최대힙

        pq.add(new Edge(1, 1, 14));
        pq.add(new Edge(1, 1, 6));
        pq.add(new Edge(1, 1, 22));
        pq.add(new Edge(1, 1, 141));

        System.out.println(pq.poll());

    }
}
