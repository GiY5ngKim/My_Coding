package boj_2444_print_stars7;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < N - i; j++) sb.append(' ');
            for (int j = 0; j < 2 * i - 1; j++) sb.append('*');
            sb.append('\n');
        }

        for (int i = N - 1; i >= 1; i--) {
            for (int j = 0; j < N - i; j++) sb.append(' ');
            for (int j = 0; j < 2 * i - 1; j++) sb.append('*');
            sb.append('\n');
        }

        System.out.print(sb);
    }
}