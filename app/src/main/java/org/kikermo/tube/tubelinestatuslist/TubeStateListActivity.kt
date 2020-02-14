package org.kikermo.tube.tubelinestatuslist

import android.os.Bundle
import android.widget.Toast
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_tube_status.*
import org.kikermo.tube.R
import org.kikermo.tube.base.BaseMVPActivity
import org.kikermo.tube.domain.model.Line
import org.kikermo.tube.utils.show
import javax.inject.Inject

class TubeStateListActivity :
    BaseMVPActivity<TubeStateListView, TubeStateListPresenter>(), TubeStateListView {

    @Inject
    lateinit var adapter: TubeStatusAdapter

    @Inject
    lateinit var tubeStateListPresenter: TubeStateListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tube_status)
        setupAdapter()
    }

    fun setupAdapter() {
        tube_status_list_recyclerview.adapter = adapter
    }

    override fun providePresenter() = tubeStateListPresenter

    override fun provideView() = this

    override fun showLoading(show: Boolean) {
        tube_status_list_loading_view.show(show)

        // Equivale a:
//        if(show) {
//            tube_status_list_recyclerview.visibility = View.VISIBLE
//        } else {
//            tube_status_list_recyclerview.visibility = View.GONE
//        }
    }

    override fun showData(tubeStatus: List<Line>) {
        adapter.addLineStates(tubeStatus)
    }

    override fun showError(show: Boolean) {
        if(show) {
            Toast.makeText(this, R.string.general_error, Toast.LENGTH_LONG).show()
        }
    }
}
