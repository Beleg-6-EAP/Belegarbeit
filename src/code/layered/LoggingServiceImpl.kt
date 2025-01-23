package demo.microkernel.loggingservice.internal

import demo.microkernel.loggingservice.service.LoggingService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LoggingServiceImpl: LoggingService {

    val logger: Logger = LoggerFactory.getLogger(LoggingServiceImpl::class.java)

    override fun logMessage(serviceName: String, message: String) {
        logger.info("[$serviceName] - $message")
    }
}