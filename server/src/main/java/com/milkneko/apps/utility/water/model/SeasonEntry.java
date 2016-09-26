package com.milkneko.apps.utility.water.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import com.milkneko.apps.utility.water.model.MeasureStamp;

@Entity
public class SeasonEntry implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8137536980942692825L;
	@EmbeddedId
    private SeasonEntryKey id;

    @OneToMany(mappedBy = "seasonEntry", fetch = FetchType.LAZY)
    private Collection<SeasonalConnectionDebt> seasonalConnectionDebts;

	@OneToMany(mappedBy = "seasonEntry", fetch = FetchType.LAZY)
	private Collection<MeasureStamp> measureStamp;

    public SeasonEntry(){
    }

    public SeasonEntry(int year, int month) {
        this.id = new SeasonEntryKey(year, month);
    }

    public SeasonEntryKey getId() {
        return id;
    }

    public int getYear() {
        return this.id.getYear();
    }

    public int getMonth() {
        return this.id.getMonth();
    }

    public Collection<SeasonalConnectionDebt> getSeasonalConnectionDebts() {
        return seasonalConnectionDebts;
    }

    public void setSeasonalConnectionDebts(Collection<SeasonalConnectionDebt> seasonalConnectionDebts) {
        this.seasonalConnectionDebts = seasonalConnectionDebts;
    }

	public Collection<MeasureStamp> getMeasureStamp() {
	    return measureStamp;
	}

	public void setMeasureStamp(Collection<MeasureStamp> param) {
	    this.measureStamp = param;
	}
}