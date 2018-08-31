package bms.kotlinplayground

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //disposable declaration
    private var disposable: Disposable? = null

    //retrofit build init
    private val wikiApiServe by lazy {
        WikiApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //search button listener
        btn_search.setOnClickListener {
            if (edit_search.text.toString().isNotEmpty()) {
                beginSearch(edit_search.text.toString())
            }
        }

    }

    //fn to search wikipedia for given string
    private fun beginSearch(searchString: String) {
        disposable = wikiApiServe.
                hitCountCheck("query", "json", "search", searchString)//web service
                .subscribeOn(Schedulers.io())//job sent to scheduler
                .observeOn(AndroidSchedulers.mainThread())//on job completion go to main thread
                .subscribe(
                        { result -> txt_search_result.text = "${result.query.searchinfo
                                .totalhits} result found" },
                        { error ->
                            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                            Log.e("TAG", error.toString())
                        }
                )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()//disposing disposable to avoid memory leaks
    }


}
