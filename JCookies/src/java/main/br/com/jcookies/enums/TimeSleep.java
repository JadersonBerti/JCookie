package br.com.jcookies.enums;

public enum TimeSleep {

	VINTE_QUATRO_HORAS(24 * 60 * 60),
	TRINTA_DIAS(24 * 60 * 60),
	TIME_WHILE_LIVE_BROWSER(-1),
	TIME_FINISH_COOKIE(0);
	
	private int time;
	
	private TimeSleep(int time) {
		this.time = time;
	}
	
	public int getTime() {
		return time;
	}
}
