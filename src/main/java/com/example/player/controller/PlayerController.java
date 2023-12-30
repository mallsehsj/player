/*
 * 
 * You can use the following import statemets
 * 
 * import org.springframework.web.bind.annotation.*;
 * import java.util.*;
 * 
 */
package com.example.player.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.player.service.PlayerH2Service;
import com.example.player.model.Player;

@RestController
public class PlayerController {

    @Autowired
    public PlayerH2Service playerService;

    @GetMapping("/players")
    public ArrayList<Player> getPlayers() {
        return playerService.getPlayers();
    }

    @PostMapping("/players")
    public Player addPlayer(@RequestBody Player player) {
        return playerService.addPlayer(player);

    }

    @GetMapping("/players/{playerId}")
    public Player getbyId(@PathVariable("playerId") int playerId) {
        return playerService.getbyId(playerId);
    }

    @PutMapping("/players/{playerId}")
    public Player updatePlayer(@PathVariable("playerId") int playerId, @RequestBody Player player) {
        return playerService.updatePlayer(playerId, player);
    }

    @DeleteMapping("/players/{playerId}")
    public void deletePlayer(@PathVariable("playerId") int playerId) {
        playerService.deletePlayer(playerId);
    }

}
