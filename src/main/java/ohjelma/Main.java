package ohjelma;


import dao.KysymysDao;
import dao.VastausDao;
import database.Database;
import domain.Kysymys;
import domain.Vastaus;
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
        KysymysDao kysymykset = new KysymysDao(database);
        VastausDao vastaukset = new VastausDao(database);
        
        System.out.println("Hello world");
    
    
     Spark.get("/kysymykset", (req, res) -> {
            
            HashMap map  = new HashMap<>();
            map.put("kokeiluKurssi", kysymykset.findAll());
            
            return new ModelAndView(map, "etusivu");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/kysymykset", (req, res) -> {
            
            String kurssi = req.queryParams("kurssin nimi");
            String aihe = req.queryParams("aihe");
            String kysymysteksti = req.queryParams("teksti");
            
            Kysymys uusiKysymys = new Kysymys(kurssi, aihe, kysymysteksti);
            kysymykset.saveOrUpdate(uusiKysymys);
            
            res.redirect("/kysymykset");
            return "";
        });
        
        Spark.get("/kysymys/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer kysymysId = Integer.parseInt(req.params(":id"));
            List<Vastaus> vastauksetKysymykseen = vastaukset.kysymyksenKaikkiVastaukset(kysymysId);
            
            map.put("kysymys", kysymykset.findOne(kysymysId));
            map.put("vastaukset", vastauksetKysymykseen);
            
            return new ModelAndView(map, "vastausvaihtoehdot");
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/poistakysymys/:id", (req, res) -> {
            Integer kysymysId = Integer.parseInt(req.params(":id"));
            res.redirect("/kysymykset");
            return "";
        });
        
        Spark.post("/lisaakysymys/:kysymysid", (req, res) -> {
            boolean onOikein = false;
            Integer kysymysId = Integer.parseInt(req.params(":kysymysid"));
            System.out.println(kysymysId);
            String checkboxValues = req.queryParams("onOikein");
            if(checkboxValues != null) {
                onOikein = true;
            }
            String vastausteksti = req.queryParams("vastausteksti");
            System.out.println(vastausteksti);
            System.out.println(onOikein);
            Vastaus vastaus = new Vastaus(kysymysId, vastausteksti, onOikein);
            vastaukset.saveOrUpdate(vastaus);
            res.redirect("/kysymykset");
            return "";
        });
        
        Spark.post("/poistavastaus/:id", (req, res) -> {
            Integer vastausId = Integer.parseInt(req.params(":id"));
            vastaukset.delete(vastausId);
            res.redirect("/kysymykset");
            return "";
        });
    }
    
}
