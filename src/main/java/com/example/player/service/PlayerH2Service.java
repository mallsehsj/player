/*
 * You can use the following import statements
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.jdbc.core.JdbcTemplate;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * import java.util.ArrayList;
 * 
 */

// Write your code here
package com.example.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import javax.el.ELException;

import com.example.player.model.PlayerRowMapper;
import com.example.player.model.Player;
import com.example.player.repository.PlayerRepository;

@Service
public class PlayerH2Service implements PlayerRepository {

   @Autowired
   public JdbcTemplate db;

   @Override
   public ArrayList<Player> getPlayers() {
      List<Player> list = db.query("select * from team", new PlayerRowMapper());
      ArrayList<Player> allPlayers = new ArrayList<>(list);
      return allPlayers;
   }

   @Override
   public Player addPlayer(Player player) {
      db.update("insert into team(playerName, jerseyNumber, role) values(?, ?, ?)", player.getPlayerName(),
            player.getJerseyNumber(), player.getRole());
      Player savePlayer = db.queryForObject("select * from team where playerId=?", new PlayerRowMapper(),
            player.getPlayerId());
      return savePlayer;
   }

   @Override
   public Player getbyId(int playerId) {
      try {
         Player player1 = db.queryForObject("select * from team where playerId=?", new PlayerRowMapper(),
               playerId);
         return player1;

      } catch (Exception e) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      }

   }

   @Override
   public Player updatePlayer(int playerId, Player player) {

      if (getbyId(playerId) == null) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      }
      
      if (player.getJerseyNumber() != 0) {
         db.update("update team set jerseyNumber=? where playerId=?", player.getJerseyNumber(), playerId);
      }
      if (player.getPlayerName() != null) {
         db.update("update team set playerName=? where playerId=?", player.getPlayerName(), playerId);
      }
      if (player.getRole() != null) {
         db.update("update team set role=? where playerId=?", player.getRole(), playerId);
      }
      Player player2 = db.queryForObject("select * from team where playerId=?", new PlayerRowMapper(), playerId);
      return player2;
  
      }

 

   @Override
   public void deletePlayer(int playerId) {

    if (getbyId(playerId) == null) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      }else {
         db.update("delete from player where playerId=?", playerId);
         throw new ResponseStatusException(HttpStatus.NO_CONTENT);

      }
   
   }

}
