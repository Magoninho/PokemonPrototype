package com.example.pokemonprototype

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

class MainActivity : AppCompatActivity() {

    private fun revealPokemon(pokemonImageView: ImageView, revealTextView: TextView) {
        pokemonImageView.setColorFilter(Color.argb(0, 255, 255, 255))
        revealTextView.visibility = View.VISIBLE
    }

    private fun unrevealPokemon(pokemonImageView: ImageView, revealTextView: TextView) {
        pokemonImageView.visibility = View.INVISIBLE
        pokemonImageView.setColorFilter(Color.argb(255, 0, 0, 0))
        revealTextView.visibility = View.INVISIBLE
    }

    fun fetchPokemon() {
        val pokemonApiCall = RetrofitHelper.getInstance().create(PokeApiService::class.java)

        val randomId = (1..20).random()
        val callback = pokemonApiCall.getPokemon(randomId)
        val pokemonImageView: ImageView = findViewById(R.id.pokemonImageView)

        val revealTextView: TextView = findViewById(R.id.revealTextView)

        // Unrevealing first
        unrevealPokemon(pokemonImageView, revealTextView)

        callback.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    pokemonImageView.visibility = View.VISIBLE
                    Picasso.get().load(data?.sprites?.other?.home?.front_default).into(pokemonImageView);

                    revealTextView.text = data?.name

                    pokemonImageView.setOnClickListener {
                        revealPokemon(pokemonImageView, revealTextView)
                    }

                } else {
                    println("não funcionou >:(")
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                println(t)
                println("FALHA")
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fetchPokemon()

        val regenerateButton: Button = findViewById(R.id.regenerateButton)
        regenerateButton.setOnClickListener {
            fetchPokemon()
        }

    }
}