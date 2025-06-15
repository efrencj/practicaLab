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
    public ResponseEntity<Void> createKingdom(@RequestBody Kingdom kingdom) {
        kingdomService.createKingdom(kingdom);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 2. Start Daily Production
    @PostMapping("/{id}")
    public ResponseEntity<Void> startDailyProduction(@PathVariable String id) throws NotFoundException {
        kingdomService.startDailyProduction(id);
        return ResponseEntity.ok().build();
    }

    // 3. Invest in Food or Citizens
    @PostMapping("/{id}/invest")
    public ResponseEntity<Void> invest(
            @PathVariable String id,
            @RequestParam String type,
            @RequestBody GoldRequest request) throws NotFoundException {
        kingdomService.invest(id, type, request.gold());
        return ResponseEntity.ok().build();
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
    public ResponseEntity<Void> attack(@PathVariable("id") String attackerId,
                                       @PathVariable("target_id") String defenderId) throws NotFoundException {
        kingdomService.attack(attackerId, defenderId);
        return ResponseEntity.ok().build();
    }

    // DTO para la inversión
    public record GoldRequest(int gold) {
    }
}
