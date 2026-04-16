package boj_2884_alarm_clock;

import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int H = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int total = H * 60 + M - 45;
        if (total < 0) total += 24 * 60;

        System.out.print(total / 60 + " " + total % 60);
    }
}