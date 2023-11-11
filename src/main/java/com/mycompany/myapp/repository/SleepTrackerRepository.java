package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SleepTracker;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SleepTracker entity.
 */
@Repository
public interface SleepTrackerRepository
  extends JpaRepository<SleepTracker, Long> {
  @Query(
    "select sleepTracker from SleepTracker sleepTracker where sleepTracker.user.login = ?#{principal.username}"
  )
  List<SleepTracker> findByUserIsCurrentUser();

  default Optional<SleepTracker> findOneWithEagerRelationships(Long id) {
    return this.findOneWithToOneRelationships(id);
  }

  default List<SleepTracker> findAllWithEagerRelationships() {
    return this.findAllWithToOneRelationships();
  }

  default Page<SleepTracker> findAllWithEagerRelationships(Pageable pageable) {
    return this.findAllWithToOneRelationships(pageable);
  }

  @Query(
    value = "select sleepTracker from SleepTracker sleepTracker left join fetch sleepTracker.user",
    countQuery = "select count(sleepTracker) from SleepTracker sleepTracker"
  )
  Page<SleepTracker> findAllWithToOneRelationships(Pageable pageable);

  @Query(
    "select sleepTracker from SleepTracker sleepTracker left join fetch sleepTracker.user"
  )
  List<SleepTracker> findAllWithToOneRelationships();

  @Query(
    "select sleepTracker from SleepTracker sleepTracker left join fetch sleepTracker.user where sleepTracker.id =:id"
  )
  Optional<SleepTracker> findOneWithToOneRelationships(@Param("id") Long id);


  @Query(
    value = "SELECT *\r\n" + //
            "FROM your_table\r\n" + //
            "WHERE date >= DATEADD(DAY, -1 * DAYOFWEEK(GETDATE()), GETDATE())\r\n" + //
            "  AND date <= DATEADD(DAY, 6 - DAYOFWEEK(GETDATE()), GETDATE());\r\n" + //
            " AND user_id =:userId",
            nativeQuery = true
  )
  List<SleepTracker> getAllInCurrentWeek(@Param("userId") Long userId);
}
