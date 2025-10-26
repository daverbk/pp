package online.rabko.logger.api

import online.rabko.api.LoggerApi
import online.rabko.logger.store.LogRepository
import online.rabko.model.LogRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class LogController(
    private val repository: LogRepository
) : LoggerApi {

    override fun log(logRequest: LogRequest): ResponseEntity<Unit> {
        repository.insert(logRequest.type)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
