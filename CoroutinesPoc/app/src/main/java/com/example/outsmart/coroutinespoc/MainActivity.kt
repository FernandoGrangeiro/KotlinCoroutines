package com.example.outsmart.coroutinespoc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		concatAllInJob()
	}
	
	//Concatenando 2 tipos totamente diferentes de processos e só disparando a menssagem de sucesso no final
	private fun concatAllInJob() = runBlocking {
		val x = GlobalScope.async {
			var x = 0
			repeat(100) {
				x += 1
				Log.d("teste", "Task $x")
				delay(50L)
				
			}
			return@async x
		}
		val job = GlobalScope.launch {
			repeat(100) { i ->
				Log.d("teste", "Task 1 $i ...")
				delay(50L)
			}
		}
		
		joinAll(job, x)
		//getCompleted eu pego o resutado só depois que termina
		Log.d("teste", "Task 1" + x.getCompleted().toString())
	}
	
	private fun asyncReturn() {
		runBlocking {
			val x = GlobalScope.async {
				return@async 20
			}.await()
			print(x)
		}
	}
	
	//Soma de diversas coroutinas
	fun asyncMultiple() = runBlocking {
		val x = GlobalScope.async {
			var x = 0
			repeat(200) {
				x += 1
			}
			return@async x
		}.await()
		Log.d("teste", x.toString())
	}
	
	fun mutiProcess() = runBlocking {
		GlobalScope.launch {
			repeat(200) {
				Log.d("teste", "Task 1")
			}
		}
	}
	
	//Paralelo fazendo 2 coisas totalmente assincronas
	fun parallel() = runBlocking {
		val job = List(50) { position ->
			GlobalScope.launch {
				if (!(position > 3 && position % 2 == 0)) {
					Log.d("teste", "Task $position  ")
					delay(5000L)
				} else {
					var x = 0
					while (x <= 20) {
						x++
						Log.d("teste", "Task 1")
					}
				}
			}
		}
		job.forEach {
			it.join()
		}
		Log.d("teste", "Task 1")
	}
	
	fun concat() = runBlocking {
		//sampleStart
		var y = 0
		val job = GlobalScope.launch {
			repeat(100) { i ->
				y++
				Log.d("teste", "Task 1 $i ...")
				delay(500L)
			}
		}
		job.join()
		Log.d("teste", "Concatenou tudo")
	}
	
	//Paralelo
	fun parallelUi() = runBlocking {
		val job = List(20) { position ->
			launch {
				Log.d("teste", "Task 1 $position ")
				delay(500L)
			}
		}
		job.forEach {
			it.join()
		}
		Log.d("teste", "Finishi")
		
	}
	
	fun concatUi() = runBlocking {
		//sampleStart
		var y = 0
		val job = launch {
			repeat(100) { i ->
				y++
				Log.d("teste", "Task 1 $i ...")
				delay(500L)
			}
		}
		job.join()
		Log.d("teste", "Concatenou tudo")
	}
}