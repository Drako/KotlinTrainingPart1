package guru.drako.trainings.kotlin.day3

import org.slf4j.Logger

inline fun Logger.debug(lazyMessage: () -> String) {
  if (isDebugEnabled) {
    debug(lazyMessage())
  }
}

inline fun Logger.info(lazyMessage: () -> String) {
  if (isInfoEnabled) {
    info(lazyMessage())
  }
}

inline fun Logger.trace(lazyMessage: () -> String) {
  if (isTraceEnabled) {
    trace(lazyMessage())
  }
}

inline fun Logger.error(lazyMessage: () -> String) {
  if (isErrorEnabled) {
    error(lazyMessage())
  }
}

inline fun Logger.warn(lazyMessage: () -> String) {
  if (isWarnEnabled) {
    warn(lazyMessage())
  }
}
