package com.xiaoha;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoinTossProbability {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        List<Integer> inputNumbers = new ArrayList<>();
    
        while (sc.hasNextInt()) {
            int n = sc.nextInt();
            if (n >= 1 && n <= 100000) inputNumbers.add(n);

        }

    
        for (int n : inputNumbers) {
            BigDecimal result = calculateProbability(n);
            System.out.println("2^-" + n + "=" + formatResult(result));
        }

        sc.close();
    }

    private static BigDecimal calculateProbability(int n) {

        BigDecimal base = new BigDecimal("0.5");

        return base.pow(n, new MathContext(100, RoundingMode.HALF_UP));
    }

    private static String formatResult(BigDecimal result) {

        

        String ps = result.toPlainString();


        int first = -1;
        int dp = ps.indexOf('.');
        if (dp == -1) {
            dp = ps.length();
        }

        for (int i = 0; i < ps.length(); i++) {
            char c = ps.charAt(i);
            if (Character.isDigit(c) && c != '0' && i != dp) {
                first = i;
                break;
            }
        }

        if (first == -1) return "0.000e0";
        


        int ex;
        if (dp > first) ex = dp - first - 1;
        else ex = dp - first;
        

        StringBuilder significantDigits = new StringBuilder();
        int count = 0;
   
        for (int i = first; i < ps.length() && count < 4; i++) {
            char c = ps.charAt(i);
            if (Character.isDigit(c)) {
                significantDigits.append(c);
                count++;
            }
        }


        while (significantDigits.length() < 4) {
            significantDigits.append('0');
        }



        BigDecimal bd = new BigDecimal(significantDigits.toString());

        bd = bd.movePointLeft(1).setScale(3, RoundingMode.HALF_UP);


        String fm = bd.toPlainString();


        String[] fmpart = fm.split("\\.");
        char z = fmpart[0].charAt(0);
        String xxx = (fmpart.length > 1) ? fmpart[1] : "000";

        while (xxx.length() < 3) {
            xxx += "0";
        }
        if (xxx.length() > 3) xxx = xxx.substring(0, 3);

        return z + "." + xxx + "e" + ex;
    }
}