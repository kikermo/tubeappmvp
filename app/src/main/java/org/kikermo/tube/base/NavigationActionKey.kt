package org.kikermo.tube.base

import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class NavigationActionKey(val value: KClass<out NavigationAction>)
