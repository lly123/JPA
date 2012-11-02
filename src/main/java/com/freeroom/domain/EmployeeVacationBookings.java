package com.freeroom.domain;

import javax.persistence.*;
import java.util.Date;

@Embeddable
public class EmployeeVacationBookings {

    @Temporal(TemporalType.DATE)
    @Column(name = "STARTDATE")
    private Date startDate;

    @Column(name = "DAYS")
    private int days;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
}
