package com.github.drxaos.jvmvm.tests.interpretable.communication;

public class Cpu {

    public static void start(Bus bus) {

        while (true) {
            String input = bus.input;
            if (input != null) {
                bus.input = null;

                String res = "";
                try {
                    res = "123";
                } finally {
                    res = "";
                }

                char[] chars = input.toCharArray();
                for (int i = chars.length - 1; i >= 0; i--) {
                    res += chars[i];
                }

                bus.output = res;
            }
        }

    }

}
