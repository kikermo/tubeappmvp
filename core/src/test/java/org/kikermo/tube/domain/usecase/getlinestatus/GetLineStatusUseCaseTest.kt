package org.kikermo.tube.domain.usecase.getlinestatus

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.kotlintest.matchers.shouldEqual
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.kikermo.tube.domain.gateway.TubeGateway
import org.kikermo.tube.domain.model.Line
import org.kikermo.tube.domain.model.LineStatus
import org.kikermo.tube.persistence.TubeStore
import java.util.*

@RunWith(JUnitPlatform::class)
class GetLineStatusUseCaseTest : Spek({
    val mockTubeGateway by memoized { mock<TubeGateway>() }
    val mockTubeStore by memoized { mock<TubeStore>() }

    val getLineUseCase by memoized {
        GetLineStatusUseCase(
            tubeGateway = mockTubeGateway,
            tubeStore = mockTubeStore
        )
    }

    val request = GetLineStatusRequest("bakerloo")
    val line = Line(
        id = "bakerloo",
        name = "Bakerloo",
        lastUpdated = Date(),
        status = LineStatus(10, "Good Service")
    )
    val lineList = listOf(
        line
    )

    on("usecase execution") {
        (getLineUseCase.apply(Observable.just(request)) as Observable).test()

        it("gets the data from the gateway") {
            verify(mockTubeGateway).getTubeLinesStatus()
        }
    }
    given("that the gateway return a valid line status list") {
        beforeEachTest {
            whenever(mockTubeGateway.getTubeLinesStatus()).thenReturn(Single.just(lineList))
            whenever(mockTubeStore.updateTubeStatus(any())).thenReturn(Completable.complete())
            whenever(mockTubeStore.getLineStatus(any())).thenReturn(Maybe.empty())
        }

        on("usecase execution") {
            val test = (getLineUseCase.apply(Observable.just(request)) as Observable).test()

            it("returns a loading and a successful response") {
                test.values().shouldEqual(
                    listOf(
                        GetLineStatusResponse.Loading(null),
                        GetLineStatusResponse.LineFetchedSuccess(line)
                    )
                )
            }
            it("stores the result on local db") {
                verify(mockTubeStore).updateTubeStatus(lineList)
            }
        }
    }

    given("that the gateway returns an error, but there is cached data") {
        val networkException = Exception("network error")
        beforeEachTest {
            whenever(mockTubeGateway.getTubeLinesStatus()).thenReturn(Single.error(networkException))
            whenever(mockTubeStore.getLineStatus(any())).thenReturn(Maybe.just(line))
        }

        on("usecase execution") {
            val test = (getLineUseCase.apply(Observable.just(request)) as Observable).test()

            it("returns a loading state with data and a error response") {
                test.values().shouldEqual(
                    listOf(
                        GetLineStatusResponse.Loading(line),
                        GetLineStatusResponse.Error(networkException)
                    )
                )
            }
        }
    }
})
