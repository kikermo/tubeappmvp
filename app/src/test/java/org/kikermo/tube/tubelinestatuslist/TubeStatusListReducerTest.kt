package org.kikermo.tube.tubelinestatuslist

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.kikermo.tube.base.LoadingState
import org.kikermo.tube.domain.model.Line
import org.kikermo.tube.domain.model.LineStatus
import org.kikermo.tube.domain.usecase.gettubestatus.GetTubeStatusResponse
import java.util.*

@RunWith(JUnitPlatform::class)
class TubeStatusListReducerTest : Spek({
    val reducer by memoized { TubeStatusListScreenReducer() }
    val lineList = listOf(
        Line(
            id = "bakerloo",
            name = "Bakerloo",
            lastUpdated = Date(),
            status = LineStatus(10, "Good Service")
        ),
        Line(
            id = "northern",
            name = "Northern line",
            lastUpdated = Date(),
            status = LineStatus(5, "Severe delays")
        )
    )

    given("an initial state") {
        val initialState = TubeStateListScreenState()

        given("successful getTubeStatusResponse") {
            val event = GetTubeStatusResponse.LinesFetchedSuccess(lineList)
            on("reduce") {
                val resultState = reducer.reduce(initialState, event)

                it("reduces correctly") {
                    assertEquals(resultState.lineList, lineList)
                    assertEquals(resultState.loadingState, LoadingState.Ready)
                }
            }
        }

        given("an error getTubeStatusResponse") {
            val event = GetTubeStatusResponse.Error(Throwable())
            on("reduce") {
                val resultState = reducer.reduce(initialState, event)

                it("reduces correctly") {
                    assertEquals(resultState.loadingState, LoadingState.Error)
                }
            }
        }

        given("a loading getTubeStatusResponse") {
            val event = GetTubeStatusResponse.Loading()
            on("reduce") {
                val resultState = reducer.reduce(initialState, event)

                it("reduces correctly") {
                    assertEquals(resultState.loadingState, LoadingState.Loading)
                    assertEquals(resultState.lineList, null)
                }
            }
        }

        given("a loading getTubeStatusResponse") {
            val event = GetTubeStatusResponse.Loading(lineList)
            on("reduce") {
                val resultState = reducer.reduce(initialState, event)

                it("reduces correctly") {
                    assertEquals(resultState.loadingState, LoadingState.Loading)
                    assertEquals(resultState.lineList, lineList)
                }
            }
        }
    }
})
