
package dao;

import database.Database;
import domain.Vastaus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VastausDao implements Dao<Vastaus, Integer> {
    
    private Database database;

    public VastausDao(Database database) {
        this.database = database;
    }
    

    @Override
    public Vastaus findOne(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE id = ?");
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new Vastaus(rs.getInt("id"), rs.getInt("kysymys_id"), rs.getString("vastausteksti"), rs.getBoolean("onOikein"));
        }
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {
        List<Vastaus> vastaukset = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                vastaukset.add(new Vastaus(rs.getInt("id"), rs.getInt("kysymys_id"), rs.getString("vastausteksti"), rs.getBoolean("onOikein")));
            }
        }
        return vastaukset;
    }

    @Override
    public Vastaus saveOrUpdate(Vastaus object) throws SQLException {
        
        /*Integer onOikein;
        System.out.println(object.getKysymysId());
        if(object.onOikein()){
            onOikein = 1;
        } else {
            onOikein = 0;
        }
        */
        Vastaus vastaus = etsiVastaus(object.getVastausteksti());
        if (vastaus != null) {
            return vastaus;
        }
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastaus (kysymys_id, vastausteksti, onOikein) VALUES (?,?,?)");
            stmt.setInt(1, object.getKysymysId());
            stmt.setString(2, object.getVastausteksti());
            stmt.setBoolean(3, object.onOikein());
            stmt.executeUpdate();
        }
        return etsiVastaus(object.getVastausteksti());
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Vastaus etsiVastaus(String vastausteksti) throws SQLException{
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE vastausteksti = ?");
            stmt.setString(1, vastausteksti);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new Vastaus(rs.getInt("id"), rs.getInt("kysymys_id"), rs.getString("vastausteksti"), rs.getBoolean("onOikein"));
        }
    }
    
    public List<Vastaus> kysymyksenKaikkiVastaukset(Integer kysymyksenId) throws SQLException{
        List<Vastaus> vastaukset = new ArrayList<>();
        try(Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE kysymys_id = ?");
            stmt.setInt(1, kysymyksenId);
            ResultSet rs = stmt.executeQuery();
           
            while(rs.next()){
                /*boolean onOikein = false;
                if(rs.getInt("onOikein")==1) {
                    onOikein = true;
                }*/
                vastaukset.add(new Vastaus(rs.getInt("id"), rs.getInt("kysymys_id"), rs.getString("vastausteksti"), rs.getBoolean("onOikein")));
            }
        }
        return vastaukset;
        
    }
    
    
}
