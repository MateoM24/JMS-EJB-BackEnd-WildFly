package pakiet;

import java.io.Serializable;
import java.time.LocalDateTime;

public class DTOState implements Serializable {
	
	private static final long serialVersionUID = 3660740683713241761L;
	double EngineTemp;
	double TyresPressure;
	double OilPressure;
	LocalDateTime time;
	
	public DTOState(){}
	
	public DTOState(double engineTemp, double tyresPressure, double oilPressure, LocalDateTime time) {
		super();
		EngineTemp = engineTemp;
		TyresPressure = tyresPressure;
		OilPressure = oilPressure;
		this.time = time;
	}

	public double getEngineTemp() {
		return EngineTemp;
	}

	public void setEngineTemp(double engineTemp) {
		EngineTemp = engineTemp;
	}

	public double getTyresPressure() {
		return TyresPressure;
	}

	public void setTyresPressure(double tyresPressure) {
		TyresPressure = tyresPressure;
	}

	public double getOilPressure() {
		return OilPressure;
	}

	public void setOilPressure(double oilPressure) {
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
				+ OilPressure + ", time=" + time + "]";
	}
	
}
