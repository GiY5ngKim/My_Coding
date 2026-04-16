package boj_23971_ZOAC4;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long H = Long.parseLong(st.nextToken());
        long W = Long.parseLong(st.nextToken());
        long N = Long.parseLong(st.nextToken());
        long M = Long.parseLong(st.nextToken());

        long rows = (H - 1) / (N + 1) + 1;
        long cols = (W - 1) / (M + 1) + 1;

        System.out.print(rows * cols);
    }
}