package com.eakurnikov.common.ui.keyboard

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

/**
 * Created by eakurnikov on 2019-12-15
 */
class KeyboardEventManager(
    private val activity: AppCompatActivity
) {
    fun getEventObservable(): Observable<KeyboardEvent> =
        Observable.create<KeyboardEvent> { emitter: ObservableEmitter<KeyboardEvent> ->
            val rootView: View = activity.findViewById<View>(android.R.id.content)

            val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
                val rect: Rect = Rect().also { rootView.getWindowVisibleDisplayFrame(it) }

                if (rect.bottom < activity.resources.displayMetrics.heightPixels) {
                    emitter.onNext(KeyboardEvent.OPENED)
                } else {
                    emitter.onNext(KeyboardEvent.CLOSED)
                }
            }

            rootView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)

            emitter.setCancellable {
                rootView.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
            }
        }.distinctUntilChanged()
}