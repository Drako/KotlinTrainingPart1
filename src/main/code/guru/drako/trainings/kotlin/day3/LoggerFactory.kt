package guru.drako.trainings.kotlin.day3

import org.slf4j.Logger
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import org.slf4j.LoggerFactory as Slf4jLoggerFactory

object LoggerFactory {
  /**
   * If the given class is the class of a Companion object, the surrounding class is returned.
   * Otherwise it is the original class.
   *
   * @param clazz A normal class or the class of a Companion object.
   * @return Always a normal class.
   */
  private fun resolveClassName(clazz: Class<*>): String = clazz.name.removeSuffix("\$${clazz.simpleName}")

  /**
   * Comfort function for Kotlin taking a KClass.
   *
   * @param clazz The class for which the logger should be acquired.
   * @result The logger for the given class.
   */
  fun getLogger(clazz: KClass<*>): Logger = Slf4jLoggerFactory.getLogger(resolveClassName(clazz.java))

  /**
   * Comfort function for Java taking a Class.
   *
   * @param clazz The class for which the logger should be acquired.
   * @result The logger for the given class.
   */
  @JvmStatic
  fun getLogger(clazz: Class<*>): Logger = Slf4jLoggerFactory.getLogger(resolveClassName(clazz))

  operator fun provideDelegate(thisRef: Any, property: KProperty<*>) = object : ReadOnlyProperty<Any, Logger> {
    private val logger = getLogger(thisRef.javaClass)
    override fun getValue(thisRef: Any, property: KProperty<*>) = logger
  }
}
