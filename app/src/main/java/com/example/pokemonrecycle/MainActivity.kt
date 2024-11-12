package com.example.pokemonrecycle

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var pokemonAdapter: PokemonAdapter
    private val pokemonList = mutableListOf<PokemonDetails>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.pokemon_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        pokemonAdapter = PokemonAdapter(pokemonList)
        recyclerView.adapter = pokemonAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // Fetch data from the Pokémon API
        fetchPokemonData()
    }


    private fun fetchPokemonData() {
        val client = AsyncHttpClient()
        val url = "https://pokeapi.co/api/v2/pokemon?limit=100"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("Pokemon Success", "$json")
                try {
                    val resultsArray: JSONArray = json.jsonObject.getJSONArray("results")

                    // Clear pokemonList in case this method is called multiple times
                    pokemonList.clear()
                    for (i in 0 until resultsArray.length()) {
                        val pokemonObject = resultsArray.getJSONObject(i)
                        val name = pokemonObject.getString("name")
                        val detailsUrl = pokemonObject.getString("url")

                        // Fetch additional details, including the image URL
                        fetchPokemonDetails(name, detailsUrl)
                    }
                } catch (e: JSONException) {
                    Log.e("Parsing Error", "Error parsing JSON response", e)
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.e("Pokemon Error", errorResponse, throwable)
            }
        })
    }

    private fun fetchPokemonDetails(name: String, url: String) {
        val client = AsyncHttpClient()

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                try {
                    val jsonObject: JSONObject = json.jsonObject
                    val imageUrl = jsonObject.getJSONObject("sprites").getString("front_default")

                    // Add the Pokémon with name and image URL to the list
                    val pokemon = PokemonDetails(name, imageUrl)
                    pokemonList.add(pokemon)

                    // Notify the adapter of the data change
                    pokemonAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Log.e("Parsing Error", "Error parsing JSON response", e)
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                responseString: String?,
                throwable: Throwable?
            ) {
                Log.e("Pokemon Error", "Failed to fetch details for $name", throwable)
            }
        })
    }
}
