package online.rabko.logger.store

import online.rabko.logger.model.Sleep
import online.rabko.logger.model.Workout
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

private const val IMPORT_SLEEP_SQL = """
    insert into sleep_log (total_sleep, date, sleep_end, sleep_start)
    values (:totalSleep, :date, :sleepStart, :sleepEnd)
    on conflict do nothing
"""

private const val IMPORT_WORKOUT_SQL = """
    insert into workout_log (id, name, start_time, end_time, duration)
    values (:id, :name, :start, :end, :duration)
    on conflict do nothing
"""

@Repository
class ImportRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {

    fun insertSleep(sleep: Sleep) {
        jdbcTemplate.update(
            IMPORT_SLEEP_SQL, mapOf(
                "totalSleep" to sleep.totalSleep,
                "date" to parseExporterDateTime(sleep.date),
                "sleepStart" to parseExporterDateTime(sleep.sleepStart),
                "sleepEnd" to parseExporterDateTime(sleep.sleepEnd)
            )
        )
    }

    fun insertWorkout(workout: Workout) {
        jdbcTemplate.update(
            IMPORT_WORKOUT_SQL, mapOf(
                "id" to workout.id,
                "name" to workout.name,
                "start" to parseExporterDateTime(workout.start),
                "end" to parseExporterDateTime(workout.end),
                "duration" to workout.duration
            )
        )
    }

    private fun parseExporterDateTime(date: String): OffsetDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z")
        return OffsetDateTime.parse(date, formatter)
    }
}