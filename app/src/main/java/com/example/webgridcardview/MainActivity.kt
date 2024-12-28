package com.example.webgridcardview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.webgridcardview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val cites = listOf(
        Cite("Rambler", "https://www.rambler.ru/", R.drawable.rambler),
        Cite("VK", "https://vk.com/", R.drawable.vk),
        Cite("Mail", "https://mail.ru/", R.drawable.mail),
        Cite("Gismeteo", "https://www.gismeteo.ru/", R.drawable.gismeteo),
        Cite("Open AI", "https://openai.com/", R.drawable.openai),
        Cite("Yandex", "https://ya.ru/", R.drawable.yandex)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val adapter = object: BaseAdapter(){
            override fun getCount(): Int {
                return cites.size
            }

            override fun getItem(position: Int): Any {
                return cites[position]
            }

            override fun getItemId(p0: Int): Long {
                return 0
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var row = convertView
                if (row == null) {
                    val inflater = LayoutInflater.from(parent.context)
                    row = inflater.inflate(R.layout.grid_item, parent, false)
                }
                val cite = cites[position]
                row?.findViewById<TextView>(R.id.nameTV)?.text = cite.name
                row?.findViewById<ImageView>(R.id.iconIV)?.setImageResource(cite.icon)
                row?.tag = cite
                return row!!
            }
        }
        binding.apply {
            setSupportActionBar(toolbar)
            citesGV.adapter = adapter
            citesGV.setOnItemClickListener { _, _, position, _ ->
                val intent = Intent(this@MainActivity, PageActivity::class.java).apply {
                    putExtra(PageActivity.KEY_URI, cites[position].url)
                }
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_exit -> {
                moveTaskToBack(true)
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}

data class Cite(val name: String, val url: String, @DrawableRes val icon: Int)