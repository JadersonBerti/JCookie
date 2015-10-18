package com.extremekillers.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import com.extremekillers.business.JogadorWarfaceBO;
import com.extremekillers.entity.Xiter;

@ManagedBean
public class BlackListController {

	private List<Xiter> blackList = new ArrayList<>();
	private JogadorWarfaceBO jogadorWarfaceBO = new JogadorWarfaceBO();
	
	@PostConstruct
	public void cosntrutor(){
		blackList = jogadorWarfaceBO.findByBlackList();
	}
	
	public List<Xiter> getBlackList() {
		return blackList;
	}
	
	public void setBlackList(List<Xiter> blackList) {
		this.blackList = blackList;
	}

}
