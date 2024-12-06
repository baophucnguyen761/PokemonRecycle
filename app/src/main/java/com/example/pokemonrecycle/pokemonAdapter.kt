package com.example.pokemonrecycle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokemonAdapter(private val pokemonList: List<PokemonDetails>) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pokemonImage: ImageView = view.findViewById(R.id.pokemon_image)
        val pokemonName: TextView = view.findViewById(R.id.pokemon_name)
        val pokemonType: TextView = view.findViewById(R.id.pokemon_type) // New TextView for type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.poke_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        // Set the Pokémon name and type
        holder.pokemonName.text = pokemon.name
        holder.pokemonType.text = pokemon.type

        // Load the image with Glide
        Glide.with(holder.itemView.context)
            .load(pokemon.imageUrl) // Load Pokémon image URL
            .centerCrop()
            .into(holder.pokemonImage)
    }

    override fun getItemCount() = pokemonList.size
}
