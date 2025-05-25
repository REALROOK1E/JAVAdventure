package com.xiaoha;

import java.util.Scanner;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Headandtail {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BigDecimal log10Of2 = new BigDecimal("0.30102999566398119521373889472449"); 

        while (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            BigDecimal exponent = log10Of2.multiply(new BigDecimal(-n));
            
            BigDecimal flex = exponent.setScale(0, RoundingMode.FLOOR);

            BigDecimal fpar = exponent.subtract(flex);

            if (fpar.compareTo(BigDecimal.ZERO) < 0) {
                fpar = fpar.add(BigDecimal.ONE);
            }
            BigDecimal ten = new BigDecimal(10);
            BigDecimal mantissa = new BigDecimal(Math.pow(ten.doubleValue(), fpar.doubleValue()));

            
            MathContext mc = new MathContext(4, RoundingMode.HALF_EVEN);
            BigDecimal formattedMantissa = mantissa.round(mc);

            if (formattedMantissa.compareTo(ten) >= 0) {
                formattedMantissa = formattedMantissa.divide(ten);
                flex = flex.add(BigDecimal.ONE);
            }
            String exponentStr = flex.abs().toPlainString();
            String mantissaString = formattedMantissa.toPlainString();
            String output = String.format("2^-%d = %s.%.3se-%s",
                    n,
                    mantissaString.charAt(0),
                    mantissaString.substring(2),
                    exponentStr);

            System.out.println(output);
        }

    }
}