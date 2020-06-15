package com.example.misteryshopper.models;

import java.time.LocalDate;

public class HiringModel {

    EmployerModel who;
    String where;
    LocalDate when;
    double fee;
    boolean accepted;
    boolean done;

    public HiringModel() {
    }

    public EmployerModel getWho() {
        return who;
    }

    public void setWho(EmployerModel who) {
        this.who = who;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public LocalDate getWhen() {
        return when;
    }

    public void setWhen(LocalDate when) {
        this.when = when;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "HiringModel{" +
                "who=" + who +
                ", where='" + where + '\'' +
                ", when=" + when +
                ", fee=" + fee +
                ", accepted=" + accepted +
                ", done=" + done +
                '}';
    }
}
