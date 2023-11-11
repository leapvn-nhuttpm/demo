package com.mycompany.myapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.myapp.domain.SleepTracker;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.SleepTrackerRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.SleepTrackerService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.SleepTracker}.
 */
@RestController
@RequestMapping("/api/sleep-trackers")
public class SleepTrackerResource {

  private final Logger log = LoggerFactory.getLogger(
    SleepTrackerResource.class
  );

  private static final String ENTITY_NAME = "sleepTracker";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;
    private final UserRepository userRepository;

  private final SleepTrackerService sleepTrackerService;

  private final SleepTrackerRepository sleepTrackerRepository;

  public SleepTrackerResource(
    SleepTrackerService sleepTrackerService,
    SleepTrackerRepository sleepTrackerRepository,
        UserRepository userRepository
  ) {
    this.sleepTrackerService = sleepTrackerService;
    this.sleepTrackerRepository = sleepTrackerRepository;
        this.userRepository = userRepository;
  }

  /**
   * {@code POST  /sleep-trackers} : Create a new sleepTracker.
   *
   * @param sleepTracker the sleepTracker to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sleepTracker, or with status {@code 400 (Bad Request)} if the sleepTracker has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("")
  public ResponseEntity<SleepTracker> createSleepTracker(
    @RequestBody SleepTracker sleepTracker
  ) throws URISyntaxException {
    log.debug("REST request to save SleepTracker : {}", sleepTracker);
    if (sleepTracker.getId() != null) {
      throw new BadRequestAlertException(
        "A new sleepTracker cannot already have an ID",
        ENTITY_NAME,
        "idexists"
      );
    }
    User user = SecurityUtils
            .getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin).orElse(null);
    sleepTracker.setUser(user);
    SleepTracker result = sleepTrackerService.save(sleepTracker);
    return ResponseEntity
      .created(new URI("/api/sleep-trackers/" + result.getId()))
      .headers(
        HeaderUtil.createEntityCreationAlert(
          applicationName,
          false,
          ENTITY_NAME,
          result.getId().toString()
        )
      )
      .body(result);
  }

  /**
   * {@code PATCH  /sleep-trackers/:id} : Partial updates given fields of an existing sleepTracker, field will ignore if it is null
   *
   * @param id the id of the sleepTracker to save.
   * @param sleepTracker the sleepTracker to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sleepTracker,
   * or with status {@code 400 (Bad Request)} if the sleepTracker is not valid,
   * or with status {@code 404 (Not Found)} if the sleepTracker is not found,
   * or with status {@code 500 (Internal Server Error)} if the sleepTracker couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(
    value = "/{id}",
    consumes = { "application/json", "application/merge-patch+json" }
  )
  public ResponseEntity<SleepTracker> partialUpdateSleepTracker(
    @PathVariable(value = "id", required = false) final Long id,
    @RequestBody SleepTracker sleepTracker
  ) throws URISyntaxException {
    log.debug(
      "REST request to partial update SleepTracker partially : {}, {}",
      id,
      sleepTracker
    );
    if (sleepTracker.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, sleepTracker.getId())) {
      throw new BadRequestAlertException(
        "Invalid ID",
        ENTITY_NAME,
        "idinvalid"
      );
    }

    if (!sleepTrackerRepository.existsById(id)) {
      throw new BadRequestAlertException(
        "Entity not found",
        ENTITY_NAME,
        "idnotfound"
      );
    }
    String username = sleepTrackerRepository.findById(id).map(SleepTracker::getUser).map(User::getLogin).orElse(null);
    if (username != null && !SecurityUtils.getCurrentUserLogin().equals(username)) {
        return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();
    }

    Optional<SleepTracker> result = sleepTrackerService.partialUpdate(
      sleepTracker
    );

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(
        applicationName,
        false,
        ENTITY_NAME,
        sleepTracker.getId().toString()
      )
    );
  }

  /**
   * {@code GET  /sleep-trackers} : get all the sleepTrackers.
   *
   * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sleepTrackers in body.
   */
  @GetMapping("")
  public List<SleepTracker> getAllSleepTrackers() {
    return sleepTrackerRepository.findByUserIsCurrentUser();
  }

  /**
   * {@code DELETE  /sleep-trackers/:id} : delete the "id" sleepTracker.
   *
   * @param id the id of the sleepTracker to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSleepTracker(@PathVariable Long id) {
    String username = sleepTrackerRepository.findById(id).map(SleepTracker::getUser).map(User::getLogin).orElse(null);
    if (username != null && !SecurityUtils.getCurrentUserLogin().equals(username)) {
        return ResponseEntity.status(HttpStatusCode.valueOf(403)).build();
    }

    sleepTrackerService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(
        HeaderUtil.createEntityDeletionAlert(
          applicationName,
          false,
          ENTITY_NAME,
          id.toString()
        )
      )
      .build();
  }
  @GetMapping("/average")
  public List<SleepTracker> getAverageSleepTrackerInWeek() {
    return sleepTrackerService.findAllInWeek();
  }
}
