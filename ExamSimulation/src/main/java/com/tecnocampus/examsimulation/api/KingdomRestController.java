package com.tecnocampus.examsimulation.api;

import com.tecnocampus.examsimulation.application.KingdomService;
import com.tecnocampus.examsimulation.domain.Kingdom;
import com.tecnocampus.examsimulation.utilities.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kingdoms")
public class KingdomRestController {

    private final KingdomService kingdomService;

    public KingdomRestController(KingdomService kingdomService) {
        this.kingdomService = kingdomService;
    }

    // 1. Create Kingdom
    @PostMapping
    public ResponseEntity<Kingdom> createKingdom(@RequestBody Kingdom kingdom) {
        Kingdom created = kingdomService.createKingdom(kingdom);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // 2. Start Daily Production
    @PostMapping("/{id}")
    public ResponseEntity<Kingdom> startDailyProduction(@PathVariable String id) throws NotFoundException {
        Kingdom updated = kingdomService.startDailyProduction(id);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        return ResponseEntity.ok(updated);
    }

    // 3. Invest in Food or Citizens
    @PostMapping("/{id}/invest")
    public ResponseEntity<Kingdom> invest(
            @PathVariable String id,
            @RequestParam String type,
            @RequestBody GoldRequest request) throws NotFoundException {
        Kingdom updated = kingdomService.invest(id, type, request.gold());
        return ResponseEntity.ok(updated);
    }

    // 4. Get Kingdom Status
    @GetMapping("/{id}")
    public ResponseEntity<Kingdom> getKingdomStatus(@PathVariable String id) throws NotFoundException {
        return ResponseEntity.ok(kingdomService.getKingdomStatus(id));
    }

    // 5. Get Richest Kingdom
    @GetMapping("/richest")
    public ResponseEntity<Kingdom> getRichestKingdom() throws NotFoundException {
        return ResponseEntity.ok(kingdomService.getRichestKingdom());
    }

    // 6. Attack another Kingdom
    @PostMapping("/{id}/attack/{target_id}")
    public ResponseEntity<Kingdom> attack(@PathVariable("id") String attackerId,
                                       @PathVariable("target_id") String defenderId) throws NotFoundException {
        Kingdom attacker = kingdomService.attack(attackerId, defenderId);
        return ResponseEntity.ok(attacker);
    }

    // DTO para la inversión
    public record GoldRequest(int gold) {
    }
}
