package org.kikermo.tube.network

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.kikermo.tube.domain.model.Line
import org.kikermo.tube.domain.model.LineStatus
import org.kikermo.tube.network.model.LineNetworkModel
import org.kikermo.tube.network.model.LineStatusNetworkModel
import org.kikermo.tube.network.service.TubeService
import org.kikermo.tube.rx.TestRxSchedulers
import java.util.*

@RunWith(JUnitPlatform::class)
class TubeGatewayImplTest : Spek({
    val testSchedulers = TestRxSchedulers()
    val mockTubeService by memoized { mock<TubeService>() }

    val tubeGatewayImpl by memoized {
        TubeGatewayImpl(
            rxSchedulers = testSchedulers,
            tubeService = mockTubeService
        )
    }
    val testDate = Date()

    val networkLineList = listOf(
        LineNetworkModel(
            id = "bakerloo",
            name = "Bakerloo",
            created = testDate,
            modified = testDate,
            lineStatuses = listOf(
                LineStatusNetworkModel(
                    id = 0, statusSeverity = 10, statusSeverityDescription = "Good Service"
                )
            )
        ),
        LineNetworkModel(
            id = "northern",
            name = "Northern line",
            created = testDate,
            modified = testDate,
            lineStatuses = listOf(
                LineStatusNetworkModel(
                    id = 1, statusSeverity = 5, statusSeverityDescription = "Severe Delays"
                )
            )
        )
    )

    val lineList = listOf(
        Line(
            id = "bakerloo",
            name = "Bakerloo",
            lastUpdated = testDate,
            status = LineStatus(10, "Good Service")
        ),
        Line(
            id = "northern",
            name = "Northern line",
            lastUpdated = testDate,
            status = LineStatus(5, "Severe Delays")
        )
    )

    given("a response containing a tube status list") {
        beforeEachTest {
            whenever(mockTubeService.getTubeStatus()).thenReturn(Single.just(networkLineList))
        }

        on("getTubeStatus") {
            val result = tubeGatewayImpl.getTubeLinesStatus().test()

            it("returns list of lines") {
                result.assertResult(lineList)
            }

            it("completes") {
                result.assertComplete()
            }
        }
    }
})
