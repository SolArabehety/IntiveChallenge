package com.jbsolutions.iwoapp.ui.activities

import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Sol Arabehety on 5/24/2018.
 */
open class BaseActivity : AppCompatActivity() {
    private val disposable = CompositeDisposable()

    public override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    fun addDisposable(disposable: Disposable?  = null, disposables: CompositeDisposable? = null) {
        disposable?.let { this.disposable.add(it) }
        disposables?.let { this.disposable.addAll(it) }
    }

}