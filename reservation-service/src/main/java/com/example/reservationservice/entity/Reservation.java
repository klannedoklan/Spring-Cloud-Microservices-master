package com.example.reservationservice.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="reservations")
@XmlRootElement(name = "reservation")
@XmlAccessorType(XmlAccessType.NONE)
public class Reservation implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
    private Long id;
	
	@XmlElement
    private String reservationName;
    
    public Reservation() {
		// TODO Auto-generated constructor stub
	}

    public Reservation(String reservationName) {
        this.reservationName = reservationName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReservationName() {
        return this.reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + this.id +
                ", reservationName='" + this.reservationName + '\'' +
                '}';
    }
}