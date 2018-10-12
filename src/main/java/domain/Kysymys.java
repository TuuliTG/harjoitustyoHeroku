
package domain;


public class Kysymys {
    
    private Integer id;
    private String kurssi;
    private String aihe;
    private String kysymysteksti;

    
    public Kysymys(String kurssi, String aihe, String kysymysteksti) {
        
        this.kurssi = kurssi;
        this.aihe = aihe;
        this.kysymysteksti = kysymysteksti;
    }

    public String getAihe() {
        return aihe;
    }

    public Integer getId() {
        return id;
    }

    public String getKurssi() {
        return kurssi;
    }

    public String getKysymysteksti() {
        return kysymysteksti;
    }

    
    public String kurssiOtsikko() {
        return "Kurssi: " + this.getKurssi();
    }
    
    public String kurssiAihe() {
        return "Aihe: " + this.getAihe();
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
}
