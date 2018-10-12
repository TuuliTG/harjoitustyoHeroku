package ohjelma;

import spark.Spark;

public class Main {
    public static void main(String[] args) {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
    }
    
}
