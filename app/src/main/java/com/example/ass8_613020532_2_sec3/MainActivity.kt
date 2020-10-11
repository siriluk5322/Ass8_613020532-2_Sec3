package com.example.ass8_613020532_2_sec3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import layout.Employee
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var employeeList = arrayListOf<Employee>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.adapter = EmployeeAdapter(this.employeeList, applicationContext)
        recycler_view.layoutManager = LinearLayoutManager(applicationContext)

    }

    override fun onResume() {
        super.onResume()
        callEmployeedata()
    }
    fun callEmployeedata(){
        employeeList.clear()
        val serv: EmployeeAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmployeeAPI::class.java)

        serv.retrieveEmployee()
            .enqueue(object : Callback<List<Employee>> {
                override fun onResponse(
                    call: Call<List<Employee>>,
                    response: Response<List<Employee>>
                ) {
                    response.body()?.forEach {
                        employeeList.add(Employee(it.emp_name,it.emp_gender,it.emp_email,it.emp_salary))
                    }

                    recycler_view.adapter = EmployeeAdapter(employeeList, applicationContext)
                }

                override fun onFailure(call: Call<List<Employee>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })
    }



    fun add(view: View) {
        val intent = Intent(this@MainActivity,InsertActivity::class.java)
        startActivity(intent)
    }
}