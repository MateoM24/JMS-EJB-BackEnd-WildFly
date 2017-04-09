package pakiet;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DTOState implements Serializable {
	
	private static final long serialVersionUID = 3660740683713241761L;
	int EngineTemp;
	int TyresPressure;
	int OilPressure;
	LocalDateTime time;
	boolean[] alerts={false,false};
	
	public DTOState(){}
	
	public DTOState(int engineTemp, int tyresPressure, int oilPressure, LocalDateTime time, boolean[] alerts) {
		super();
		EngineTemp = engineTemp;
		TyresPressure = tyresPressure;
		OilPressure = oilPressure;
		this.time = time;
		this.alerts=alerts;
	}

	public int getEngineTemp() {
		return EngineTemp;
	}

	public void setEngineTemp(int engineTemp) {
		EngineTemp = engineTemp;
	}

	public int getTyresPressure() {
		return TyresPressure;
	}

	public void setTyresPressure(int tyresPressure) {
		TyresPressure = tyresPressure;
	}

	public int getOilPressure() {
		return OilPressure;
	}

	public void setOilPressure(int oilPressure) {
		OilPressure = oilPressure;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "DTOState [EngineTemp=" + EngineTemp + ", TyresPressure=" + TyresPressure + ", OilPressure="
				+ OilPressure + ", time=" + time + ", alert driver?:"+alerts[0]+", alert mechanic?:"+alerts[1]+"]";
	}

	public boolean[] getAlerts() {
		return alerts;
	}

	public void setAlerts(boolean[] alerts) {
		this.alerts = alerts;
	}
	
}
