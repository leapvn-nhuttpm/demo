package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SleepTracker;
import com.mycompany.myapp.repository.SleepTrackerRepository;
import com.mycompany.myapp.service.dto.AverageSleepTracker;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.SleepTracker}.
 */
@Service
@Transactional
public class SleepTrackerService {

  private final Logger log = LoggerFactory.getLogger(SleepTrackerService.class);

  private final SleepTrackerRepository sleepTrackerRepository;

  public SleepTrackerService(SleepTrackerRepository sleepTrackerRepository) {
    this.sleepTrackerRepository = sleepTrackerRepository;
  }

  /**
   * Save a sleepTracker.
   *
   * @param sleepTracker the entity to save.
   * @return the persisted entity.
   */
  public SleepTracker save(SleepTracker sleepTracker) {
    log.debug("Request to save SleepTracker : {}", sleepTracker);
    return sleepTrackerRepository.save(sleepTracker);
  }

  /**
   * Update a sleepTracker.
   *
   * @param sleepTracker the entity to save.
   * @return the persisted entity.
   */
  public SleepTracker update(SleepTracker sleepTracker) {
    log.debug("Request to update SleepTracker : {}", sleepTracker);
    return sleepTrackerRepository.save(sleepTracker);
  }

  /**
   * Partially update a sleepTracker.
   *
   * @param sleepTracker the entity to update partially.
   * @return the persisted entity.
   */
  public Optional<SleepTracker> partialUpdate(SleepTracker sleepTracker) {
    log.debug("Request to partially update SleepTracker : {}", sleepTracker);

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

  /**
   * Get all the sleepTrackers.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<SleepTracker> findAll() {
    log.debug("Request to get all SleepTrackers");
    return sleepTrackerRepository.findAll();
  }

  /**
   * Get all the sleepTrackers with eager load of many-to-many relationships.
   *
   * @return the list of entities.
   */
  public Page<SleepTracker> findAllWithEagerRelationships(Pageable pageable) {
    return sleepTrackerRepository.findAllWithEagerRelationships(pageable);
  }

  /**
   * Get one sleepTracker by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<SleepTracker> findOne(Long id) {
    log.debug("Request to get SleepTracker : {}", id);
    return sleepTrackerRepository.findOneWithEagerRelationships(id);
  }

  /**
   * Delete the sleepTracker by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete SleepTracker : {}", id);
    sleepTrackerRepository.deleteById(id);
  }
  
  public List<SleepTracker> findAllInWeek() {
    return sleepTrackerRepository.getAllInCurrentWeek(1l);
  }
}
