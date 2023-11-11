package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A SleepTracker.
 */
@Entity
@Table(name = "sleep_tracker")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SleepTracker implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "sequenceGenerator"
  )
  @SequenceGenerator(name = "sequenceGenerator")
  @Column(name = "id")
  private Long id;

  @Column(name = "date")
  private LocalDate date;

  @Column(name = "sleep_time")
  private LocalTime sleepTime;

  @Column(name = "wakeup_time")
  private LocalTime wakeupTime;

  @Column(name = "total_sleep_duration")
  private Double totalSleepDuration;

  @ManyToOne(fetch = FetchType.EAGER)
  private User user;


  public Long getId() {
    return this.id;
  }

  public SleepTracker id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDate() {
    return this.date;
  }

  public SleepTracker date(LocalDate date) {
    this.setDate(date);
    return this;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public LocalTime getSleepTime() {
    return this.sleepTime;
  }

  public SleepTracker sleepTime(LocalTime sleepTime) {
    this.setSleepTime(sleepTime);
    return this;
  }

  public void setSleepTime(LocalTime sleepTime) {
    this.sleepTime = sleepTime;
  }

  public LocalTime getWakeupTime() {
    return this.wakeupTime;
  }

  public SleepTracker wakeupTime(LocalTime wakeupTime) {
    this.setWakeupTime(wakeupTime);
    return this;
  }

  public void setWakeupTime(LocalTime wakeupTime) {
    this.wakeupTime = wakeupTime;
  }

  public Double getTotalSleepDuration() {
    return this.totalSleepDuration;
  }

  public SleepTracker totalSleepDuration(Double totalSleepDuration) {
    this.setTotalSleepDuration(totalSleepDuration);
    return this;
  }

  public void setTotalSleepDuration(Double totalSleepDuration) {
    this.totalSleepDuration = totalSleepDuration;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public SleepTracker user(User user) {
    this.setUser(user);
    return this;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SleepTracker)) {
      return false;
    }
    return getId() != null && getId().equals(((SleepTracker) o).getId());
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }
}
