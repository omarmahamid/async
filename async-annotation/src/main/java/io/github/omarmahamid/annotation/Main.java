package io.github.omarmahamid.annotation;


import java.math.BigDecimal;


public class Main {


    @Profiling(fileDump = "/Users/omarmahamid/Documents/GitHub/async/async-annotation/profiling.html")
    public static void main(String[] args) {

        ProfilingProcessor.startProcess(Main.class);

        BigDecimal decimal = new BigDecimal("1.23");

        while (true){
            decimal.abs();
            decimal.add(new BigDecimal("1.23"));
        }

    }
}
