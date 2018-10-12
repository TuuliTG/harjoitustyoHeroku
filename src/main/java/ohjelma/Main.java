package ohjelma;


import database.Database;
import java.sql.Connection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;


public class Main {
    public static void main(String[] args) throws Exception{
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database database = new Database();
        Connection conn = database.getConnection();
        
        
        System.out.println("Hello world");
    
    
    Spark.get("*", (req, res) -> {
       HashMap map = new HashMap();
       map.put("teksti", "hello world");
        return  new ModelAndView(map, "etusivu");
    }, new ThymeleafTemplateEngine());
    
    }
    
}
