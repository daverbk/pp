package online.rabko.logger

import online.rabko.api.DefaultExceptionHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(DefaultExceptionHandler::class)
class PraiseRegretLoggerApplication

fun main(args: Array<String>) {
    runApplication<PraiseRegretLoggerApplication>(*args)
}
