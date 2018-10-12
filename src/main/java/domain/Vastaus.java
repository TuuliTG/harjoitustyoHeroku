
package domain;


public class Vastaus {
    
    private Integer Id;
    private Integer kysymysId;
    private String vastausteksti;
    private boolean onOikein;

    public Vastaus(Integer vastausId, Integer kysymysId, String vastausteksti, boolean oikein) {
        this.Id = vastausId;
        this.kysymysId = kysymysId;
        this.vastausteksti = vastausteksti;
        this.onOikein = oikein;
    }

    public Vastaus(Integer kysymysId, String vastausteksti, boolean oikein) {
        this.kysymysId = kysymysId;
        this.vastausteksti = vastausteksti;
        this.onOikein = oikein;
    }
     

    public Integer getKysymysId() {
        return kysymysId;
    }

    public Integer getId() {
        return Id;
    }

    public String getVastausteksti() {
        return vastausteksti;
    }

    public boolean onOikein() {
        return onOikein;
    }
    
    
    
    
    
}
