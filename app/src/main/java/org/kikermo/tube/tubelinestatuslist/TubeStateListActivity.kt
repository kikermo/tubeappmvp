package org.kikermo.tube.tubelinestatuslist

import android.os.Bundle
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_tube_status.*
import org.kikermo.tube.R
import org.kikermo.tube.base.BaseMVPActivity
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
}
