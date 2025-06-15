package com.tecnocampus.examsimulation.persistence;

import com.tecnocampus.examsimulation.domain.Kingdom;
import com.tecnocampus.examsimulation.utilities.NotFoundException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KingdomRepository {
    private final JdbcClient jdbcClient;
    public KingdomRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void saveKingdom(Kingdom kingdom) {
        String sql = "INSERT INTO kingdoms (id,food, gold, citizens, created_at) VALUES (:id,:food, :gold, :citizens, :created_at)";
        jdbcClient.sql(sql)
                .param("id", kingdom.getId())
                .param("food", kingdom.getFood())
                .param("gold", kingdom.getGold())
                .param("citizens", kingdom.getCitizens())
                .param("created_at", kingdom.getDateOfCreation())
                .update();
    }

    public Kingdom findKingdomById(String id) throws NotFoundException {
        String sql = "SELECT * FROM kingdoms WHERE id = :id";
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(Kingdom.class)
                .optional().orElseThrow(NotFoundException::new);
    }

    public void updateKingdom(Kingdom kingdom) {
        String sql = "UPDATE kingdoms SET food = :food, gold = :gold, citizens = :citizens WHERE id = :id";
        jdbcClient.sql(sql)
                .param("id", kingdom.getId())
                .param("food", kingdom.getFood())
                .param("gold", kingdom.getGold())
                .param("citizens", kingdom.getCitizens())
                .update();
    }

    public void deleteKingdom(String id) {
        String sql = "DELETE FROM kingdoms WHERE id = :id";
        jdbcClient.sql(sql)
                .param("id", id)
                .update();
    }
    public Kingdom findRichestKingdom() throws NotFoundException {
        String sql = "SELECT * FROM kingdoms ORDER BY gold DESC LIMIT 1";
        return jdbcClient.sql(sql)
                .query(Kingdom.class)
                .optional().orElseThrow(NotFoundException::new);
    }
    public List<Kingdom> findAllKingdoms() {
        String sql = "SELECT * FROM kingdoms";
        return jdbcClient.sql(sql)
                .query(Kingdom.class)
                .list();
    }


}
