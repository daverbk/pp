package online.rabko.monitor

import online.rabko.monitor.api.DefaultExceptionHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(DefaultExceptionHandler::class)
class SelfMonitorApplication

fun main(args: Array<String>) {
    runApplication<SelfMonitorApplication>(*args)
}
