package org.kikermo.tube.rx

import io.reactivex.schedulers.Schedulers

class TestRxSchedulers : RxSchedulers {
    override fun main() = Schedulers.trampoline()

    override fun io() = Schedulers.trampoline()

    override fun computation() = Schedulers.trampoline()

    override fun trampoline() = Schedulers.trampoline()
}
