package org.kikermo.tube.persistence

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Maybe
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import org.kikermo.tube.domain.model.Line
import org.kikermo.tube.domain.model.LineStatus
import org.kikermo.tube.persistence.model.LineDBModel
import org.kikermo.tube.persistence.model.fromDomainModel
import org.kikermo.tube.rx.TestRxSchedulers
import java.util.*

@RunWith(JUnitPlatform::class)
class TubeStoreImplTest : Spek({
    val testSchedulers = TestRxSchedulers()
    val mockLineDao by memoized { mock<LineDao>() }

    val tubeStore by memoized {
        TubeStoreImpl(mockLineDao, testSchedulers)
    }

    val lineList = listOf(
        Line(
            id = "bakerloo",
            name = "Bakerloo",
            status = LineStatus(10, "Good Service")
        ),
        Line(
            id = "northern",
            name = "Northern line",
            status = LineStatus(5, "Severe Delays")
        )
    )

    val dbLineList = listOf(
        LineDBModel(
            id = "bakerloo",
            name = "Bakerloo",
            severity = 10,
            description = "Good Service"
        ),
        LineDBModel(
            id = "northern",
            name = "Northern line",
            severity = 5,
            description = "Severe Delays"
        )
    )

    on("line list updated") {
        tubeStore.updateTubeStatus(lineList).test()
        lineList.forEach {
            it("calls the dao") {
                verify(mockLineDao).insertLine(it.fromDomainModel())
            }
        }
    }

    given("database contains data ") {
        beforeEachTest {
            whenever(mockLineDao.getAllLines()).thenReturn(Maybe.just(dbLineList))
            whenever(mockLineDao.getLine(any())).thenReturn(Maybe.just(dbLineList.first()))
        }

        on("get all line statuses") {
            val result = tubeStore.getTubeStatus().test()

            it("return lines as expected") {
                result.assertResult(lineList)
            }
        }

        on("get single line status") {
            val result = tubeStore.getLineStatus("bakerloo").test()

            it("return single line as expected") {
                result.assertResult(lineList.first())
            }
        }
    }

    given("database doesn't contain data") {
        beforeEachTest {
            whenever(mockLineDao.getAllLines()).thenReturn(Maybe.empty())
            whenever(mockLineDao.getLine(any())).thenReturn(Maybe.empty())
        }

        on("get all line statuses") {
            val result = tubeStore.getTubeStatus().test()

            it("returns empty list") {
                result.assertComplete()
            }
        }

        on("get single line status") {
            val result = tubeStore.getLineStatus("bakerloo").test()

            it("returns no line") {
                result.assertComplete()
            }
        }
    }
})
