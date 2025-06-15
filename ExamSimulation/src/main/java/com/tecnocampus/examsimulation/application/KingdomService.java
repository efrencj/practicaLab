package com.tecnocampus.examsimulation.application;

import com.tecnocampus.examsimulation.domain.Kingdom;
import com.tecnocampus.examsimulation.persistence.KingdomRepository;
import com.tecnocampus.examsimulation.utilities.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class KingdomService {

    private final KingdomRepository kingdomRepository;

    public KingdomService(KingdomRepository kingdomRepository) {
        this.kingdomRepository = kingdomRepository;
    }

    // 1. Crear un nuevo reino
    public Kingdom createKingdom(Kingdom kingdom) {
        if (kingdom.getGold() < 0 || kingdom.getGold() > 60 ||
                kingdom.getCitizens() < 0 || kingdom.getCitizens() > 60 ||
                kingdom.getFood() < 0 || kingdom.getFood() > 60) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "Each field must be from 0 to 60");
        }

        kingdomRepository.saveKingdom(kingdom);
        return kingdom;
    }

    // 2. Producción diaria
    public Kingdom startDailyProduction(String kingdomId) throws NotFoundException {
        Kingdom kingdom = kingdomRepository.findKingdomById(kingdomId);

        int food = kingdom.getFood();
        int citizens = kingdom.getCitizens();

        if (food >= citizens) {
            // comida suficiente
            kingdom.setFood(food - citizens);
        } else {
            // no hay suficiente comida, se reduce la población
            citizens = food; // solo sobreviven los que pueden comer
            kingdom.setFood(0);
            kingdom.setCitizens(citizens);
        }

        kingdom.setGold(kingdom.getGold() + citizens * 2);

        if (citizens == 0) {
            kingdomRepository.deleteKingdom(kingdomId);
            return null;
        } else {
            kingdomRepository.updateKingdom(kingdom);
            return kingdom;
        }
    }

    // 3. Inversión en comida o ciudadanos
    public Kingdom invest(String kingdomId, String type, int goldToSpend) throws NotFoundException {
        Kingdom kingdom = kingdomRepository.findKingdomById(kingdomId);

        if (kingdom.getGold() < goldToSpend) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Not enough gold");
        }

        kingdom.setGold(kingdom.getGold() - goldToSpend);

        switch (type) {
            case "food" -> kingdom.setFood(kingdom.getFood() + goldToSpend * 2);
            case "citizens" -> kingdom.setCitizens(kingdom.getCitizens() + goldToSpend);
            default -> throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid investment type");
        }

        kingdomRepository.updateKingdom(kingdom);
        return kingdom;
    }

    // 4. Estado del reino
    public Kingdom getKingdomStatus(String kingdomId) throws NotFoundException {
        return kingdomRepository.findKingdomById(kingdomId);
    }

    // 5. Reino más rico
    public Kingdom getRichestKingdom() throws NotFoundException {
        return kingdomRepository.findRichestKingdom();
    }

    // 6. Ataque
    public Kingdom attack(String attackerId, String defenderId) throws NotFoundException {
        if (attackerId.equals(defenderId))
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You cannot attack yourself");

        Kingdom attacker = kingdomRepository.findKingdomById(attackerId);
        Kingdom defender = kingdomRepository.findKingdomById(defenderId);

        if (attacker.getCitizens() > defender.getCitizens()) {
            winAttack(attacker, defender);
        } else {
            winAttack(defender, attacker);
        }

        try {
            return kingdomRepository.findKingdomById(attackerId);
        } catch (NotFoundException e) {
            // attacker could have been deleted
            throw e;
        }
    }

    private void winAttack(Kingdom winner, Kingdom loser) {
        int stolenGold = loser.getGold();
        int stolenCitizens = loser.getCitizens() / 2;

        winner.setGold(winner.getGold() + stolenGold);
        winner.setCitizens(winner.getCitizens() + stolenCitizens);

        loser.setGold(0);
        loser.setCitizens(loser.getCitizens() - stolenCitizens);

        kingdomRepository.updateKingdom(winner);

        if (loser.getCitizens() <= 0) {
            kingdomRepository.deleteKingdom(loser.getId());
        } else {
            kingdomRepository.updateKingdom(loser);
        }
    }
}
