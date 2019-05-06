package ui

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import android.widget.Toast
import habit.mvvm.koltlin1.R

@Suppress("UNREACHABLE_CODE")
class MainActivity : AppCompatActivity(), View.OnClickListener{

    lateinit var mTextView: TextView
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTextView=findViewById(R.id.textv) as TextView
        mTextView.text="asdfsdf"
        mTextView.setOnClickListener(this)
//        mTextView.setOnClickListener { v->
//
//        }

        window.navigationBarColor=ContextCompat.getColor(this, R.color.error_color_material_dark)
        window.statusBarColor=ContextCompat.getColor(this, R.color.error_color_material_dark)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.textv -> {
                Toast.makeText(this,"按下textView",Toast.LENGTH_SHORT).show()
                intent= Intent(this, MainActivity2::class.java)
                startActivity(intent)
            }
            else -> {

            }
        }
    }

}
