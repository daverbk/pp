package online.rabko

import java.io.File
import java.nio.charset.Charset
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ofPattern
import java.util.Locale

private const val PERIOD_COLUMN = "Период выписки:"
private const val CURRENCY_COLUMN = "Валюта счета:"
private const val SUM_COLUMN = "Обороты по счету"
private const val BLOCKED_COLUMN = "Сумма блокировки"
private const val CATEGORY_COLUMN = "Категория операции"
private const val TABLE_START = "Дата транзакции"

fun main(args: Array<String>) {
    val result = readData(args)
    println(result)
}

fun readData(files: Array<String>): Map<String, Double> {
    var sumColumnIndex = 0
    var categoryColumnIndex = 0
    val result = mutableMapOf<String, Double>()

    files.forEach {
        val file = File(it)
        file.useLines(Charset.forName("windows-1251")) { lines ->
            lines.forEach { line ->
                val columns = line.split(";")
                val firstEntry = columns[0].trim()
                when (firstEntry) {
                    PERIOD_COLUMN -> println(columns[1])
                    CURRENCY_COLUMN -> println(columns[1])
                    TABLE_START -> {
                        for (i in columns.indices) {
                            when (columns[i]) {
                                SUM_COLUMN -> sumColumnIndex = i
                                BLOCKED_COLUMN -> sumColumnIndex = i
                                CATEGORY_COLUMN -> categoryColumnIndex = i
                            }
                        }
                    }
                }
                if (runCatching { LocalDate.parse(firstEntry, ofPattern("dd.MM.yyyy HH:mm:ss")) }.isSuccess) {
                    val sum = NumberFormat.getInstance(Locale.GERMAN).parse(columns[sumColumnIndex]).toDouble()
                    if (sum < 0) {
                        result[columns[categoryColumnIndex]] = result.getOrDefault(
                            columns[categoryColumnIndex],
                            0.0
                        ) + sum
                    }
                }
            }
        }
    }
    return result
}
