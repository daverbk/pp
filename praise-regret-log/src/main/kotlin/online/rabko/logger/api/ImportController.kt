package online.rabko.logger.api

import online.rabko.logger.model.MetricsImportRequest
import online.rabko.logger.model.WorkoutImportRequest
import online.rabko.logger.store.ImportRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ImportController(
    @Value("\${import.size.max}")
    private var maxImportSize: Int,
    private val importRepository: ImportRepository
) : ImportApi {

    override fun importSleepData(metricsImportRequest: MetricsImportRequest): ResponseEntity<Unit> {
        metricsImportRequest.data.metrics
            .flatMap { it.data }
            .validateImportSize()
            .forEach { importRepository.insertSleep(it) }

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    override fun importWorkoutData(workoutImportRequest: WorkoutImportRequest): ResponseEntity<Unit> {
        workoutImportRequest.data.workouts
            .validateImportSize()
            .forEach { importRepository.insertWorkout(it) }

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    private fun <T> List<T>.validateImportSize(): List<T> =
        this.takeIf { it.size <= maxImportSize }
            ?: throw TooManyConcurrentImports("Import size exceeds limit: $maxImportSize", )
}

class TooManyConcurrentImports(msg: String) : ApiException(msg, HttpStatus.BAD_REQUEST.value())