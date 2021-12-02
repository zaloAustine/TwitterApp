package com.zalocoders.twitterapp.base

import com.google.common.io.Resources
import java.io.File
import java.net.HttpURLConnection
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

open class RequestDispatcher : Dispatcher() {
	override fun dispatch(request: RecordedRequest): MockResponse {
		
		return when {
			request.path!!.contains("recent?", true) -> {
				MockResponse()
						.setResponseCode(HttpURLConnection.HTTP_OK)
						.setBody(getJson("json/response.json"))
			}
			
			else -> {
				throw IllegalArgumentException("Unknown path ${request.path}")
			}
		}
	}
	
	
	private fun getJson(path: String): String {
		val uri = Resources.getResource(path)
		val file = File(uri.path)
		
		return String(file.readBytes())
	}
}