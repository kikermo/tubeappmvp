package org.kikermo.tube.domain.usecase.gettubestatus

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
class GetTubeStatusUseCaseTest : Spek({
    val mockTubeGateway by memoized { mock<TubeGateway>() }
    val mockTubeStore by memoized { mock<TubeStore>() }

    val getTubeUseCase by memoized {
        GetTubeStatusUseCase(
            tubeGateway = mockTubeGateway,
            tubeStore = mockTubeStore
        )
    }

    val request = GetTubeStatusRequest
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

    on("usecase execution") {
        (getTubeUseCase.apply(Observable.just(request)) as Observable).test()

        it("gets the data from the gateway") {
            verify(mockTubeGateway).getTubeLinesStatus()
        }
    }
    given("that the gateway return a valid tube status list") {
        beforeEachTest {
            whenever(mockTubeGateway.getTubeLinesStatus()).thenReturn(Single.just(lineList))
            whenever(mockTubeStore.updateTubeStatus(any())).thenReturn(Completable.complete())
            whenever(mockTubeStore.getTubeStatus()).thenReturn(Maybe.empty())
        }

        on("usecase execution") {
            val test = (getTubeUseCase.apply(Observable.just(request)) as Observable).test()

            it("returns a loading and a successful response") {
                test.values().shouldEqual(
                    listOf(
                        GetTubeStatusResponse.Loading(null),
                        GetTubeStatusResponse.LinesFetchedSuccess(lineList)
                    )
                )
            }
            it("stores the result on local db") {
                verify(mockTubeStore).updateTubeStatus(lineList)
            }
        }
    }

    given("that the gateway returns an error, but there is some cached data") {
        val networkException = Exception("network error")
        beforeEachTest {
            whenever(mockTubeGateway.getTubeLinesStatus()).thenReturn(Single.error(networkException))
            whenever(mockTubeStore.getTubeStatus()).thenReturn(Maybe.just(lineList))
        }

        on("usecase execution") {
            val test = (getTubeUseCase.apply(Observable.just(request)) as Observable).test()

            it("returns a loading and a successful response") {
                test.values().shouldEqual(
                    listOf(
                        GetTubeStatusResponse.Loading(lineList),
                        GetTubeStatusResponse.Error(networkException)
                    )
                )
            }
        }
    }
})
