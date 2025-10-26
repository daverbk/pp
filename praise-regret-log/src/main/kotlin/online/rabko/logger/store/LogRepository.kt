package online.rabko.logger.store

import online.rabko.model.LogType
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

private const val INSERT_SQL = """
    insert into praise_regret_logs (type)
    values (:type)
"""

@Repository
class LogRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {
    fun insert(type: LogType) {
        jdbcTemplate.update(INSERT_SQL, mapOf("type" to type.name))
    }
}
