package online.rabko.monitor.api

import online.rabko.monitor.model.LogRequest
import online.rabko.monitor.store.EmotionLogRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class EmotionLogController(
    private val repository: EmotionLogRepository
) : EmotionLogApi {

    override fun log(logRequest: LogRequest): ResponseEntity<Unit> {
        repository.insert(logRequest.type)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
