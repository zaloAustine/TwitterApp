package com.zalocoders.twitterapp.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@Throws(InterruptedException::class)
fun <T> LiveData<T>.getValueBlocking(): T? {
	var value: T? = null
	val latch = CountDownLatch(1)
	val innerObserver = Observer<T> {
		value = it
		latch.countDown()
	}
	observeForever(innerObserver)
	latch.await(2, TimeUnit.SECONDS)
	return value
}

fun <T> convertJsonStringToObject(
		jsonString: String,
		clazz: Class<T>): T =
		Gson().fromJson(jsonString, clazz)