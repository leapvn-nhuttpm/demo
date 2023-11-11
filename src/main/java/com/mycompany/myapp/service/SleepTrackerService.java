package com.mycompany.myapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.domain.SleepTracker;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.SleepTrackerRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.utils.Utils;

@Service
@Transactional
public class SleepTrackerService {

  private final SleepTrackerRepository sleepTrackerRepository;

  private final UserRepository userRepository;

  public SleepTrackerService(SleepTrackerRepository sleepTrackerRepository, UserRepository userRepository) {
    this.sleepTrackerRepository = sleepTrackerRepository;
    this.userRepository = userRepository;
  }

  public SleepTracker save(SleepTracker sleepTracker) {
    return sleepTrackerRepository.save(sleepTracker);
  }

  public Optional<SleepTracker> partialUpdate(SleepTracker sleepTracker) {
    return sleepTrackerRepository
      .findById(sleepTracker.getId())
      .map(existingSleepTracker -> {
        if (sleepTracker.getDate() != null) {
          existingSleepTracker.setDate(sleepTracker.getDate());
        }
        if (sleepTracker.getSleepTime() != null) {
          existingSleepTracker.setSleepTime(sleepTracker.getSleepTime());
        }
        if (sleepTracker.getWakeupTime() != null) {
          existingSleepTracker.setWakeupTime(sleepTracker.getWakeupTime());
        }
        if (sleepTracker.getTotalSleepDuration() != null) {
          existingSleepTracker.setTotalSleepDuration(
            sleepTracker.getTotalSleepDuration()
          );
        }

        return existingSleepTracker;
      })
      .map(sleepTrackerRepository::save);
  }

  public void delete(Long id) {
    sleepTrackerRepository.deleteById(id);
  }
  
  public List<SleepTracker> findAllInWeek() {
    LocalDate startDateOfWeek = Utils.getStartDateOfWeek();
    LocalDate endDateOfWeek = Utils.getEndDateOfWeek();
    String userLogin = SecurityUtils.getCurrentUserLogin().orElse(null);
    Optional<User> user = this.userRepository.findOneByLogin(userLogin);
    return sleepTrackerRepository.getAllInCurrentWeek(user.get().getId(), startDateOfWeek.toString(), endDateOfWeek.toString());
  }
}
