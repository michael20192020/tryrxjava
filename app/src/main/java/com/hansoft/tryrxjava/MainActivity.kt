package com.hansoft.tryrxjava

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding.view.RxView
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers

//import rx.Subscriber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ninth();


    }


    fun first(view: View) {
        val theObservable = Observable.just("hello world!", "hello rxjava", "hello rxandroid")
        val theObserver: Observer<String> = object : Observer<String> {
            override fun onCompleted() {
                Log.d("aaa", "completed")
            }

            override fun onError(e: Throwable) {
                Log.d("aaa", "error")
            }

            override fun onNext(s: String) {
                Log.d("aaa", "data : $s Hello Michael")
            }
        }
        theObservable.subscribe(theObserver)
    }

    fun second(view: View) {
        val listObservable = Observable.from(arrayOf(1, 2, 3, 4, 5))
        listObservable.subscribe { integer -> Log.d("aaa", "data : $integer") }

        val strings = arrayOf("Hello", "good morning", "good afternoon")
        val observable: Observable<*> = Observable.from(strings)
        observable.subscribe{ strings -> Log.d("aaa", "data : $strings")}

        val theObservable = Observable.from(arrayOf("Tom", "Jack", "Ben"))
        val theObserver: Observer<String> = object : Observer<String> {
            override fun onCompleted() {
                Log.d("aaa", "completed")
            }

            override fun onError(e: Throwable) {
                Log.d("aaa", "error")
            }

            override fun onNext(s: String) {
                Log.d("aaa", "people : $s say : Hello Michael")
            }
        }
        theObservable.subscribe(theObserver)
    }


    fun third(view: View)
    {
        val observer: Observer<String> = object : Observer<String> {
            override fun onNext(s: String) {
                Log.i("aaa", "onNext execute : $s")
            }

            override fun onCompleted() {
                Log.i("aaa", "Completed execute")
            }

            override fun onError(e: Throwable) {
                Log.i("aaa", "onError : " + e.localizedMessage)
            }
        }

        val observable = Observable.create<String> { subscriber ->
            subscriber.onNext("English")
            subscriber.onNext("Math")
            subscriber.onNext("Art")
            subscriber.onCompleted()
        }

        observable.subscribe(observer)
    }

    fun fourth(view: View)
    {
        val listObservable = Observable.from(arrayOf(1, 2, 3, 4, 5))
        val listObservablenew = listObservable.map { integer -> integer * integer }

        val theObserver: Observer<Int> = object : Observer<Int> {
            override fun onCompleted() {
                Log.d("aaa", "completed")
            }

            override fun onError(e: Throwable) {
                Log.d("aaa", "error")
            }

            override fun onNext(s: Int) {
                Log.d("aaa", "data : $s")
            }
        }
        listObservablenew.subscribe(theObserver)
    }

    fun fifth(view: View)
    {
        val listObservable = Observable.from(arrayOf(1, 2, 3, 4, 5))
        val listObservablenew = listObservable
                .skip(2)
                .filter { integer -> integer % 2 == 0 }

        val theObserver: Observer<Int> = object : Observer<Int> {
            override fun onCompleted() {
                Log.d("aaa", "completed")
            }

            override fun onError(e: Throwable) {
                Log.d("aaa", "error")
            }

            override fun onNext(s: Int) {
                Log.d("aaa", "data : $s")
            }
        }
        listObservablenew.subscribe(theObserver)
    }

    fun fetchData(url: String?): String? {
        return url
    }

    fun sixth(view: View)
    {
        val asyncObservable = Observable.create<String> { subscriber ->
            try {
                val content = fetchData("www.sohu.com")
                subscriber.onStart()
                subscriber.onNext(content)
                subscriber.onCompleted()
            } catch (e: Exception) {
                subscriber.onError(e)
            }
        }

        asyncObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { s ->
                    Log.d("aaa", "async data: $s")
                    Toast.makeText(this@MainActivity, "async data : $s", Toast.LENGTH_SHORT).show()
                }
    }

    fun seventh(view: View)
    {
        val asyncObservable1 = Observable.create<String> { subscriber ->
            try {
                val content = fetchData("https://www.hansoft.com.au")
                subscriber.onStart()
                subscriber.onNext(content)
                subscriber.onCompleted()
            } catch (e: java.lang.Exception) {
                subscriber.onError(e)
            }
        }

        asyncObservable1
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { s ->
                    Log.d("aaa", "async data: $s")
                    Toast.makeText(this@MainActivity, "async data : $s", Toast.LENGTH_SHORT).show()
                }

        val asyncObservable2 = Observable.create<String> { subscriber ->
            try {
                val content = fetchData("https://api.github.com/orgs/octokit/repos")
                subscriber.onStart()
                subscriber.onNext(content)
                subscriber.onCompleted()
            } catch (e: java.lang.Exception) {
                subscriber.onError(e)
            }
        }

        asyncObservable2
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { s ->
                    Log.d("aaa", "async data: $s")
                    Toast.makeText(this@MainActivity, "async data : $s", Toast.LENGTH_SHORT).show()
                }


        val zippedObservable = Observable.zip(asyncObservable1, asyncObservable2) { firstString, secondString ->
            val result = """
                $firstString
                $secondString
                """.trimIndent()
            Log.d("aaa", "zipdata = $result")
            result
        }

        val theObserver: Observer<String> = object : Observer<String> {
            override fun onCompleted() {
                Log.d("aaa", "completed")
            }

            override fun onError(e: Throwable) {
                Log.d("aaa", "error")
            }

            override fun onNext(s: String) {
                Log.d("aaa", "data : zipdata = $s")
            }
        }
        zippedObservable.subscribe(theObserver)

    }

    fun eighth(view: View) {
        val asyncObservable1 = Observable.create<String> { subscriber ->
            try {
                val content = fetchData("www.google.cn")
                subscriber.onStart()
                subscriber.onNext(content)
                subscriber.onCompleted()
            } catch (e: java.lang.Exception) {
                subscriber.onError(e)
            }
        }
        asyncObservable1
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { s ->
                    Log.d("aaa", "async data: $s")
                    Toast.makeText(this@MainActivity, "async data : $s", Toast.LENGTH_SHORT).show()
                }
        val asyncObservable2 = Observable.create<String> { subscriber ->
            try {
                val content = fetchData("www.baidu.com")
                subscriber.onStart()
                subscriber.onNext(content)
                subscriber.onCompleted()
            } catch (e: java.lang.Exception) {
                subscriber.onError(e)
            }
        }
        asyncObservable2
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { s ->
                    Log.d("aaa", "async data: $s")
                    Toast.makeText(this@MainActivity, "async data : $s", Toast.LENGTH_SHORT).show()
                }
        val concatedObservable = Observable.concat(asyncObservable1, asyncObservable2)
        concatedObservable.subscribe { s ->
            Log.d("aaa", "concat data: $s")
            Toast.makeText(this@MainActivity, "concat data: $s", Toast.LENGTH_SHORT).show()
        }
        val theObserver: Observer<String> = object : Observer<String> {
            override fun onCompleted() {
                Log.d("aaa", "completed")
            }

            override fun onError(e: Throwable) {
                Log.d("aaa", "error")
            }

            override fun onNext(s: String) {
                Log.d("aaa", "data : concat data = $s")
            }
        }
        concatedObservable.subscribe(theObserver)
    }

    fun ninth() {
        val eventButton = findViewById<View>(R.id.ninthButton) as Button
        RxView.clicks(eventButton).subscribe(Action1<Void?> { Toast.makeText(this@MainActivity, "Ninth button is Clicked", Toast.LENGTH_SHORT).show() })
    }

}



