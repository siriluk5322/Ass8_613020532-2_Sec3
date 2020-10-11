package com.example.ass8_613020532_2_sec3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_insert.*
import kotlinx.android.synthetic.main.activity_main.*
import layout.Employee
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InsertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)


    }
    fun addEmployee(view: View) {
        val serv: EmployeeAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmployeeAPI::class.java)

        var radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        var selectedId : Int = radioGroup.checkedRadioButtonId;
        var radioButton : RadioButton = findViewById(selectedId);

        serv.insertEmp(
            edt_name.text.toString(),
            radioButton.text.toString(),
            edt_email.text.toString(),
            edt_salary.text.toString().toInt()).enqueue(object :Callback<Employee>{
            override fun onResponse(call: Call<Employee>, response: Response<Employee>) {
                if (response.isSuccessful()){
                    Toast.makeText(applicationContext,"SuccessFully Inserted",Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<Employee>, t: Throwable) {
                Toast.makeText(applicationContext,"Error onFailure "+ t.message,Toast.LENGTH_LONG).show()
            }
        })
    }
    fun cancel(view: View) {
        edt_name.text.clear()
        radioGroup.clearCheck()
        edt_email.text.clear()
        edt_salary.text.clear()
    }


}