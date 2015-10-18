package com.extremekillers.business;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.extremekillers.dao.ContadorTempoDAO;
import com.extremekillers.entity.ContadorTempo;

public class ContadorTempoBO {
	
	private ContadorTempoDAO contadorTempoDAO;
	
	public ContadorTempoBO() {
		contadorTempoDAO = new ContadorTempoDAO();
	}

	public Integer startContador(int serialPalyerId, Date inicio, int inicioAdminId){
		return contadorTempoDAO.insertTempoInContador(serialPalyerId,inicio,inicioAdminId);
	}
	
	public boolean desligarContador(ContadorTempo contadorTempo, Date termino, int terminoAdminId){
		String totalHoras = this.getTotalHorasByDate(contadorTempo.getInicio(), termino);
		return contadorTempoDAO.updateContadorTempo(contadorTempo.getId(),termino,terminoAdminId, totalHoras);
	}
	
	public boolean atualizarContador(int id,Date inicio, Date termino, int inicioAdminId, int terminoAdminId){
		return contadorTempoDAO.updateAllContadorTempo(id,inicio,termino,inicioAdminId,terminoAdminId);
	}
	
	public boolean deletaContador(int id){
		return contadorTempoDAO.deletaContador(id);
	}

	public ContadorTempo findById(int id){
		return contadorTempoDAO.findById(id);
	}
	
	public ContadorTempo retornaUltimoContadorAtivo(int serial_player_id){
		return contadorTempoDAO.retornaUltimoContadorAtivo(serial_player_id);
	}
	
	public List<ContadorTempo> getContadorTempos(){
		return contadorTempoDAO.getContadorTempos();
	}
	
	public String getContadorTempoRelatorio(int serialPlayerId){
		return getContadorTempoRelatorio(contadorTempoDAO.getTotalTempoGasto(serialPlayerId));
	}
	
	public String getTotalHorasByDate(Date inicio, Date termino){
		Calendar calendarInicio = Calendar.getInstance();
		calendarInicio.setTime(inicio);
		
		Calendar calendarTermino = Calendar.getInstance();
		calendarTermino.setTime(termino);
		
		Integer horasTotal = calendarTermino.get(Calendar.HOUR_OF_DAY) - calendarInicio.get(Calendar.HOUR_OF_DAY);
		Integer minutosTotal = calendarTermino.get(Calendar.MINUTE) - calendarInicio.get(Calendar.MINUTE);
		Integer segundosTotal = calendarTermino.get(Calendar.SECOND) - calendarInicio.get(Calendar.SECOND);
		horasTotal = (horasTotal > 0 ? (horasTotal - 1):horasTotal);

		return (horasTotal.toString()+":"+minutosTotal.toString()+":"+segundosTotal.toString()).replace("-", "");
	}

	public String getContadorTempoRelatorio(List<String> contadorTempos){
		Integer horas = 0;
		Integer minutos = 0;
		Integer segundos = 0;
		
		for(String tempo : contadorTempos){
			String[] hms = tempo.split(":");
			Integer h = Integer.valueOf(hms[0]);
			horas = horas + h;

			Integer m = Integer.valueOf(hms[1]);
			if(minutos + m == 60){
				horas = horas + 1;
				minutos = 0;
			}else if(minutos + m > 60){
				minutos = minutos + m - 60;
				horas = horas + 1;
			}else{
				minutos = minutos + m;
			}
			
			Integer s = Integer.valueOf(hms[2]);
			if(segundos + s == 60){
				minutos = minutos + 1;
				segundos = 0;
			}else if(segundos + s > 60){
				segundos = segundos + s - 60;
				minutos = minutos + 1;
			}else{
				segundos = segundos + s;
			}
			
			//Mesmo procedimento caso os 
			//segundos fação diferença;
			if(minutos == 60){
				horas = horas + 1;
				minutos = 0;
			}else if(minutos > 60){
				minutos = minutos + m - 60;
				horas = horas + 1;
			}
		}
		
		return "Total de horas: " +horas.toString()+":"+minutos.toString()+":"+segundos.toString();
	}
	
	public ContadorTempo retornaUtilmaConcluidaBySerialPlayerId(Integer serial_player_id) {
		return contadorTempoDAO.retornaUtilmaConcluidaBySerialPlayerId(serial_player_id);
	}

	public List<ContadorTempo> getContadorTemposBySerialPlayer(Integer serial_player_id) {
		return contadorTempoDAO.getContadorTemposBySerialPlayer(serial_player_id);
	}
	
}