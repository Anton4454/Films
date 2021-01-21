package com.example.films

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.album_layout.view.*

class FilmAdapter(private val films: Response?) :
    RecyclerView.Adapter<FilmAdapter.PhotoHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(
            R.layout.album_layout,
            parent,
            false
        )

        return PhotoHolder(view)
    }

    override fun getItemCount(): Int = films?.results?.size!!

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val itemFilm = films?.results?.get(position)
        holder.bindPhoto(itemFilm, position)
    }

    class PhotoHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private lateinit var image: String


        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val context = itemView.context
            /*val showPhotoIntent = Intent(context, PhotoPageActivity::class.java)
            showPhotoIntent.putExtra(PHOTO_KEY, stringUrl)
            context.startActivity(showPhotoIntent)*/
        }

        companion object {
            val FILM_KEY = "FILM"
        }

        fun bindPhoto(filmItem: ResultsItem?, position: Int){
            image =
                "https://themoviedb.org/t/p/w600_and_h900_face" + filmItem?.posterPath
            view.setBackgroundResource(R.drawable.white_border)
            Glide
                .with(view.context)
                .load(
                    image
                )
                .override(400, 600)
                .into(view.filmPhoto)

            view.title.text = filmItem?.title
            var genres : String = ""
            for (item in filmItem?.genreIds!!){
                if (genres.equals("")){
                    genres += getGenre(item!!)
                }
                else
                    genres += ", " + getGenre(item!!)
            }
            view.genre.text = genres
            view.overview.text = filmItem?.overview
            view.voteAverage.text = filmItem?.voteAverage.toString()
            view.voteCount.text = filmItem?.voteCount.toString()

        }

        fun getGenre(id: Int): String {
            when (id) {
                28 -> return "боевик"
                12 -> return "приключения"
                16 -> return "мультфильм"
                35 -> return "комедия"
                80 -> return "криминал"
                99 -> return "документальный"
                18 -> return "драма"
                10751 -> return "семейный"
                14 -> return "фэнтези"
                36 -> return "история"
                27 -> return "ужасы"
                10402 -> return "музыка"
                9648 -> return "детектив"
                10749 -> return "мелодрама"
                878 -> return "фантастика"
                10770 -> return "телевизионный фильм"
                53 -> return "триллер"
                10752 -> return "военный"
                37 -> return "вестерн"
                else -> return "lox"
            }
        }
    }
}