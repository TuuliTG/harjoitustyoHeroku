package ohjelma;


import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;


public class Main {
    public static void main(String[] args) {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        System.out.println("Hello world");
    
    
    Spark.get("*", (req, res) -> {
       HashMap map = new HashMap();
       map.put("teksti", "hello world");
        return  new ModelAndView(map, "etusivu");
    }, new ThymeleafTemplateEngine());
    
    }
    
}
