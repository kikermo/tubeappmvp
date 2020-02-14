package org.kikermo.tube.rx

import io.reactivex.Scheduler

interface RxSchedulers {

    fun main(): Scheduler

    fun io(): Scheduler

    fun computation(): Scheduler

    fun trampoline(): Scheduler
}
