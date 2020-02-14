package org.kikermo.tube.utils

import android.view.View

fun View.show(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}
