package com.example.reservationclient.entity;

import java.io.Serializable;

public class Reservation implements Serializable {

	public Reservation() {}
	public Reservation(Long id, String reservationName) {
		this.id = id;
		this.reservationName = reservationName;
	}
	
    private Long id;
    private String reservationName;

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + this.id +
                ", reservationName='" + this.reservationName + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getReservationName() {
        return this.reservationName;
    }

    public void setReservationName(final String reservationName) {
        this.reservationName = reservationName;
    }
}