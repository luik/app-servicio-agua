package com.example.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SeasonEntryKey implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2958185116990237429L;
	private int year;
    private int month;

    public SeasonEntryKey() {
    }

    public SeasonEntryKey(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}