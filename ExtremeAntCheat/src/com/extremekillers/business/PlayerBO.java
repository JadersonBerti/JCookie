package com.extremekillers.business;

import java.util.List;

import com.extremekillers.dao.PlayerDAO;
import com.extremekillers.entity.Player;

public class PlayerBO {

	private PlayerDAO playerDAO;
	
	public PlayerBO() {
		playerDAO = new PlayerDAO();
	}
	
	public List<Player> findAll(int serialPlayerId) {
		return playerDAO.findAllPlayerOn(serialPlayerId);
	}

}