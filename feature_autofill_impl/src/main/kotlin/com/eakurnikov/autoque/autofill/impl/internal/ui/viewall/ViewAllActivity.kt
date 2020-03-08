package com.eakurnikov.autoque.autofill.impl.internal.ui.viewall

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.AutofillPayload
import com.eakurnikov.autoque.autofill.api.dependencies.data.model.getAutofillPayload
import com.eakurnikov.autoque.autofill.impl.R
import com.eakurnikov.autoque.autofill.impl.di.components.AutofillServiceComponent
import com.eakurnikov.autoque.autofill.impl.internal.data.model.FillDataDto
import com.eakurnikov.autoque.autofill.impl.internal.domain.clientapp.AppInfoProvider
import com.eakurnikov.autoque.autofill.impl.internal.viewmodel.viewall.ViewAllViewModel
import com.eakurnikov.common.data.Resource
import com.google.android.material.bottomsheet.BottomSheetBehavior
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.faf_bottom_sheet_view_all.*
import javax.inject.Inject

/**
 * Created by eakurnikov on 2020-01-16
 */
@TargetApi(Build.VERSION_CODES.O)
class ViewAllActivity : AppCompatActivity(), ViewAllUi, View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var viewModel: ViewAllViewModel

    @Inject
    lateinit var appInfoProvider: AppInfoProvider

    override val activityContext: Activity = this@ViewAllActivity

    lateinit var adapter: ViewAllAdapter

    private var autofillPayload: AutofillPayload? = null

    @ColorInt
    private var collapsedColor: Int? = null

    @ColorInt
    private var expandedColor: Int? = null

    private var collapsedDrawable: Drawable? = null

    private var expandedDrawable: Drawable? = null

    private val onBottomSheetEvent = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                finish()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            if (slideOffset == 1.0f) {
                expand()
            } else {
                collapse()
            }
        }
    }

    private val onSearch = object : TextWatcher {

        override fun afterTextChanged(text: Editable?) {
            setClearSearchVisibility(text)
            text?.let { viewModel.onSearch(it.toString()) }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    }

//    private val onScroll = object : RecyclerView.OnScrollListener() {
//
////        private var scrollDist = 0
////        private var isVisible = true
//
//        private var isUp: Boolean = false
//
//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//
////            if (isVisible && scrollDist > MINIMUM) {
////                hide()
////                scrollDist = 0;
////                isVisible = false;
////            } else if (!isVisible && scrollDist < -MINIMUM) {
////                show();
////                scrollDist = 0;
////                isVisible = true;
////            }
////
////            if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
////                scrollDist += dy;
////            }
//
//            when {
//                dy > 0 && isUp -> {
//                    faf_view_all_shadow.visibility = View.VISIBLE
//                    isUp = false
////                    faf_view_all_search_bar
////                            .animate()
////                            .translationY(-faf_view_all_search_bar.height.toFloat() /*+ fabMargin*/)
////                            .setInterpolator(DecelerateInterpolator(2f))
////                            .withEndAction {
////                                faf_view_all_search_bar.visibility = View.GONE
////                            }
////                            .start()
//                }
//                dy < 0 && !isUp -> {
//                    faf_view_all_shadow.visibility = View.INVISIBLE
//                    isUp = true
////                    faf_view_all_search_bar
////                            .animate()
////                            .translationY(0f)
////                            .setInterpolator(DecelerateInterpolator(2f))
////                            .withEndAction {
////                                faf_view_all_search_bar.visibility = View.VISIBLE
////                            }
////                            .start()
//                }
//                else -> Unit
//            }
//        }
//    }

    private val onFillDataEntities = object : DisposableObserver<Resource<List<FillDataDto>>>() {

        override fun onNext(resource: Resource<List<FillDataDto>>) {
            when (resource) {
                is Resource.Success -> {
                    when (resource.data.size) {
                        0 -> showEmptyList()
                        else -> showList(resource.data)
                    }
                }
                is Resource.Loading -> showLoading()
                is Resource.Error -> showError(resource.message)
            }
        }

        override fun onComplete() {
            dispose()
        }

        override fun onError(error: Throwable) {
            showError(error.message ?: "Error while loading fill data")
            dispose()
        }
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.faf_activity_view_all)

        AutofillServiceComponent
            .initAndGet(Unit)
            .inject(this)

        viewModel = ViewModelProviders
            .of(this, viewModelFactory)
            .get(ViewAllViewModel::class.java)

        autofillPayload = intent.getAutofillPayload()

        initUi()
        subscribe()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onAbleToShow(this, autofillPayload)
    }

    override fun onDestroy() {
        super.onDestroy()
        onFillDataEntities.dispose()
    }

    override fun onClick(view: View?) {
        view ?: return
        viewModel.onSelected(this, faf_view_all_list.getChildLayoutPosition(view))
    }

    private fun initUi() {
        adapter = ViewAllAdapter(
            appInfoProvider,
            resources.getDrawable(R.drawable.faf_ic_person, null),
            this
        )

        collapsedColor = resources.getColor(R.color.transparent, null)
        expandedColor = resources.getColor(R.color.white, null)

        collapsedDrawable = resources.getDrawable(R.drawable.faf_top_rounded_white_rectangle, null)
        expandedDrawable = ColorDrawable(expandedColor!!)

        with(window) {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = collapsedColor!!
        }

        BottomSheetBehavior
            .from(faf_view_all_bottom_sheet_root)
            .setBottomSheetCallback(onBottomSheetEvent)

        faf_view_all_search.addTextChangedListener(onSearch)

        faf_view_all_search_clear.setOnClickListener { faf_view_all_search.setText("") }

        faf_view_all_list.apply {
            setHasFixedSize(true)
//            addOnScrollListener(onScroll)
            layoutManager = LinearLayoutManager(this@ViewAllActivity)
            adapter = this@ViewAllActivity.adapter
        }

        faf_view_all_close.setOnClickListener { finish() }
    }

    private fun subscribe() {
        viewModel.getFillDataSubject().subscribe(onFillDataEntities)
    }

    private fun showEmptyList() {
        faf_view_all_progress_bar.visibility = View.GONE
        faf_view_all_empty_title.visibility = View.VISIBLE
        faf_view_all_empty_description.visibility = View.VISIBLE
        faf_view_all_search.visibility = View.VISIBLE
        faf_view_all_list.visibility = View.GONE
    }

    private fun showList(dtos: List<FillDataDto>) {
        faf_view_all_progress_bar.visibility = View.GONE
        faf_view_all_empty_title.visibility = View.GONE
        faf_view_all_empty_description.visibility = View.GONE
        faf_view_all_search.visibility = View.VISIBLE
        faf_view_all_list.visibility = View.VISIBLE

        adapter.apply {
            data = dtos
            notifyDataSetChanged()
        }
    }

    private fun showLoading() {
        faf_view_all_progress_bar.visibility = View.VISIBLE
        faf_view_all_empty_title.visibility = View.GONE
        faf_view_all_empty_description.visibility = View.GONE
        faf_view_all_search.visibility = View.VISIBLE
        faf_view_all_list.visibility = View.GONE
    }

    private fun showError(message: String) {
        faf_view_all_progress_bar.visibility = View.GONE
        faf_view_all_empty_title.visibility = View.VISIBLE
        faf_view_all_empty_description.visibility = View.VISIBLE
        faf_view_all_search.visibility = View.GONE
        faf_view_all_list.visibility = View.GONE
    }

    private fun expand() {
        with(window) {
            statusBarColor = expandedColor!!
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        faf_view_all_draggable.visibility = View.INVISIBLE
        faf_view_all_bottom_sheet_root.background = expandedDrawable
        faf_view_all_close.visibility = View.VISIBLE
//        faf_view_all_close
//                .animate()
//                .alpha(1f)
//                .setInterpolator(DecelerateInterpolator(2f))
//                .start()
    }

    private fun collapse() {
        with(window) {
            statusBarColor = collapsedColor!!
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
        faf_view_all_draggable.visibility = View.VISIBLE
        faf_view_all_bottom_sheet_root.background = collapsedDrawable
        faf_view_all_close.visibility = View.INVISIBLE
    }

    private fun setClearSearchVisibility(text: Editable?) {
        when {
            text == null -> Unit
            text.isEmpty() -> faf_view_all_search_clear.visibility = View.INVISIBLE
            else -> faf_view_all_search_clear.visibility = View.VISIBLE
        }
    }
}

/*

TODO:
+ elevation
+ show all items with bigger size
+ search border
+ remove status bar animation
+ correct text view when nothing found
+ correct icons
+ remover x in search
+ expand bottom sheet when keyboard is up

- hide search and show shadow while scrolling
- entity sections
- handle landscape mode
 */