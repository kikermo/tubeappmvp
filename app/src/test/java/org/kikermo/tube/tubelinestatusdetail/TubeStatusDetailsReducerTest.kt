package org.kikermo.tube.tubelinestatusdetail

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
import org.kikermo.tube.domain.usecase.getlinestatus.GetLineStatusResponse
import org.kikermo.tube.tubelinestatusdetails.TubeStatusDetailsScreenReducer
import org.kikermo.tube.tubelinestatusdetails.TubeStatusDetailsScreenState
import java.util.*

@RunWith(JUnitPlatform::class)
class TubeStatusDetailsReducerTest : Spek({
    val reducer by memoized { TubeStatusDetailsScreenReducer() }
    val line = Line(
        id = "bakerloo",
        name = "Bakerloo",
        lastUpdated = Date(),
        status = LineStatus(10, "Good Service")
    )

    given("an initial state") {
        val initialState = TubeStatusDetailsScreenState()

        given("successful getLineStatusResponse") {
            val event = GetLineStatusResponse.LineFetchedSuccess(line)
            on("reduce") {
                val resultState = reducer.reduce(initialState, event)

                it("reduces correctly") {
                    assertEquals(resultState.line, line)
                    assertEquals(resultState.loadingState, LoadingState.Ready)
                }
            }
        }

        given("an error getLineStatusResponse") {
            val event = GetLineStatusResponse.Error(Throwable())
            on("reduce") {
                val resultState = reducer.reduce(initialState, event)

                it("reduces correctly") {
                    assertEquals(resultState.loadingState, LoadingState.Error)
                }
            }
        }

        given("a loading getLineStatusResponse") {
            val event = GetLineStatusResponse.Loading()
            on("reduce") {
                val resultState = reducer.reduce(initialState, event)

                it("reduces correctly") {
                    assertEquals(resultState.loadingState, LoadingState.Loading)
                    assertEquals(resultState.line, null)
                }
            }
        }

        given("a loading getLineStatusResponse") {
            val event = GetLineStatusResponse.Loading(line)
            on("reduce") {
                val resultState = reducer.reduce(initialState, event)

                it("reduces correctly") {
                    assertEquals(resultState.loadingState, LoadingState.Loading)
                    assertEquals(resultState.line, line)
                }
            }
        }
    }
})
