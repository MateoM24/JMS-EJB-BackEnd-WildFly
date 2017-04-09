package pakiet;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DTOState implements Serializable {
	
	private static final long serialVersionUID = 3660740683713241761L;
	int EngineTemp;
	int TyresPressure;
	int OilPressure;
	LocalDateTime time;
	boolean driverAlert;
	boolean mechanicAlert;
	
	public DTOState(){}

	public int getEngineTemp() {
		return EngineTemp;
	}

	public DTOState(int engineTemp, int tyresPressure, int oilPressure, LocalDateTime time, boolean driverAlert,
			boolean mechanicAlert) {
		super();
		EngineTemp = engineTemp;
		TyresPressure = tyresPressure;
		OilPressure = oilPressure;
		this.time = time;
		this.driverAlert = driverAlert;
		this.mechanicAlert = mechanicAlert;
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
				+ OilPressure + ", time=" + time + ", alert driver?:"+driverAlert+", alert mechanic?:"+mechanicAlert+"]";
	}

	public boolean isDriverAlert() {
		return driverAlert;
	}

	public void setDriverAlert(boolean driverAlert) {
		this.driverAlert = driverAlert;
	}

	public boolean isMechanicAlert() {
		return mechanicAlert;
	}

	public void setMechanicAlert(boolean mechanicAlert) {
		this.mechanicAlert = mechanicAlert;
	}
	
}
