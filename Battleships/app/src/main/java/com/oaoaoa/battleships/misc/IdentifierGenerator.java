package com.oaoaoa.battleships.misc;


import java.util.Random;



public class IdentifierGenerator {

    public static String generate() {

        Random        random  = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 6; i++) {

            builder.append(
                (char) (random.nextInt(26) + 'A')
            );
            builder.append(
                (char) (random.nextInt(26) + 'a')
            );
            builder.append(
                random.nextInt(10)
            );
        }

        return builder.toString();
    }

}
