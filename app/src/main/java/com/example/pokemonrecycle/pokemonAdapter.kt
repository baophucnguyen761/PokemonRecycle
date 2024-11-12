package com.example.pokemonrecycle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonrecycle.R

class PokemonAdapter(private val pokemonList: List<PokemonDetails>) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonImage: ImageView = view.findViewById(R.id.pokemon_image)
        val pokemonName: TextView = view.findViewById(R.id.pokemon_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.poke_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        // Set the Pokémon name
        holder.pokemonName.text = pokemon.name

        // Load the image with Glide
        Glide.with(holder.itemView.context)
            .load(pokemon.imageUrl) // Make sure `imageUrl` is a valid URL string for the Pokémon image
            .centerCrop()
            .into(holder.pokemonImage)
    }

    override fun getItemCount() = pokemonList.size
}

