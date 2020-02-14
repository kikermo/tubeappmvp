package org.kikermo.tube.tubelinestatuslist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.item_line.view.*
import org.kikermo.tube.R
import org.kikermo.tube.domain.model.Line
import org.kikermo.tube.utils.toLineColour
import org.kikermo.tube.utils.toSeverityColour
import javax.inject.Inject

class TubeStatusAdapter @Inject constructor(context: Context) :
    RecyclerView.Adapter<ProductListViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    val lines = mutableListOf<Line>()

    private val lineClickedSubject = PublishSubject.create<Line>()

    val lineClickedObservable: Observable<Line>
        get() = lineClickedSubject

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val view = layoutInflater.inflate(R.layout.item_line, parent, false)
        return ProductListViewHolder(view, lineClickedSubject)
    }

    override fun getItemCount() = lines.size

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val line = lines[position]
        holder.onBind(line)
    }

    fun addLineStates(lines: List<Line>) {
        this.lines.clear()
        this.lines.addAll(lines)
        this.notifyDataSetChanged()
    }
}

class ProductListViewHolder(
    private val view: View,
    private val lineClickedSubject: Subject<Line>
) : RecyclerView.ViewHolder(view) {

    fun onBind(line: Line) {
        view.item_line_title_textview.text = line.name
        view.item_line_status_textview.text = line.status?.description ?: ""
        view.setBackgroundColor(line.toLineColour())
        view.item_line_status_view.setBackgroundColor(line.toSeverityColour())

        view.setOnClickListener { lineClickedSubject.onNext(line) }
    }
}
