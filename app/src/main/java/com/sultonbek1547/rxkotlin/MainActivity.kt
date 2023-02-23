package com.sultonbek1547.rxkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sultonbek1547.rxkotlin.adapter.MyUserAdapter
import com.sultonbek1547.rxkotlin.database.AppDatabase
import com.sultonbek1547.rxkotlin.database.User
import com.sultonbek1547.rxkotlin.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var appDatabase: AppDatabase
    lateinit var rvAdapter: MyUserAdapter

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        appDatabase = AppDatabase.getInstance(this)
        rvAdapter = MyUserAdapter(appDatabase)
        binding.myRv.adapter = rvAdapter


        appDatabase.myUserDao().getAllUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Consumer<List<User>>{
                override fun accept(t: List<User>?) {
                    rvAdapter.submitList(t)
                }
            })

        /** Adding new USER */
        binding.btnSave.setOnClickListener {
            val user = User()
            user.name=binding.edtName.text.toString()
            user.number=binding.edtNumber.text.toString()
            appDatabase.myUserDao().addUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{it->
                    Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
                }

        }


    }
}