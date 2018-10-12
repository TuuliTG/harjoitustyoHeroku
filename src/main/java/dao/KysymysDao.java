
package dao;

import database.Database;
import domain.Kysymys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class KysymysDao implements Dao<Kysymys, Integer> {
    
    private Database database;

    public KysymysDao(Database database) {
        this.database = database;
    }

    @Override
    public Kysymys findOne(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Kysymys kysymys = new Kysymys(rs.getString("kurssi"), rs.getString("aihe"), rs.getString("kysymysteksti"));
            kysymys.setId(rs.getInt("id"));
            return kysymys;
            
        }
    }

    @Override
    public List<Kysymys> findAll() throws SQLException {
        List<Kysymys> kysymykset = new ArrayList<>();
        try (Connection conn = database.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM KYSYMYS");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Kysymys kysymys = new Kysymys(rs.getString("kurssi"), rs.getString("aihe"), rs.getString("kysymysteksti"));
                kysymys.setId(rs.getInt("id"));
                kysymykset.add(kysymys);
            }
        }
        return kysymykset;
    }

    @Override
    public Kysymys saveOrUpdate(Kysymys object) throws SQLException {
        Kysymys kysymys = findByNameOfTheCource(object.getKurssi());
        if(kysymys != null) {
            return kysymys;
        }
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys (kurssi, aihe, kysymysteksti) VALUES (?, ?, ?)");
            stmt.setString(1, object.getKurssi());
            stmt.setString(2, object.getAihe());
            stmt.setString(3, object.getKysymysteksti());
            stmt.executeUpdate();
        }
        return findByNameOfTheCource(object.getKurssi());
    }

    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM Vastaus WHERE kysymys_id = ?");
            stmt2.setInt(1, key);
            stmt2.executeUpdate();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");
            stmt.setInt(1, key);
            stmt.executeUpdate();
            
        }
    }
    
    private Kysymys findByNameOfTheCource(String kurssinNimi) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM KYSYMYS WHERE kurssi = ?");
            stmt.setString(1, kurssinNimi);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            Kysymys kysymys = new Kysymys(rs.getString("kurssi"), rs.getString("aihe"), rs.getString("kysymysteksti"));
            kysymys.setId(rs.getInt("id"));
            return kysymys;
        }
        
    }
    
}
