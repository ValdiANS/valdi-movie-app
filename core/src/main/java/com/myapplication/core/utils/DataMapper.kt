package com.myapplication.core.utils

import com.myapplication.core.data.source.local.entity.MovieEntity
import com.myapplication.core.data.source.remote.response.MovieDetailResponse
import com.myapplication.core.data.source.remote.response.MovieItemResponse
import com.myapplication.core.domain.model.BelongsToCollection
import com.myapplication.core.domain.model.GenresItem
import com.myapplication.core.domain.model.MovieDetail
import com.myapplication.core.domain.model.MovieItem
import com.myapplication.core.domain.model.ProductionCompaniesItem
import com.myapplication.core.domain.model.ProductionCountriesItem
import com.myapplication.core.domain.model.SpokenLanguagesItem

object DataMapper {
    fun mapResponseToDomain(input: List<MovieItemResponse>): List<MovieItem> {
        val movieList = ArrayList<MovieItem>()

        input.map {
            val movieItem = MovieItem(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                isFavorite = false
            )

            movieList.add(movieItem)
        }

        return movieList
    }

    fun mapResponseToEntities(input: List<MovieItemResponse>, type: String): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()

        input.map {
            val movieEntity = MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                type = type,
                isFavorite = false,
            )

            movieList.add(movieEntity)
        }

        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<MovieItem> =
        input.map {
            MovieItem(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                voteAverage = it.voteAverage,
                isFavorite = it.isFavorite,
            )
        }

    fun mapEntityToDomain(input: MovieEntity): MovieItem = MovieItem(
        id = input.id,
        title = input.title,
        overview = input.overview,
        posterPath = input.posterPath,
        backdropPath = input.backdropPath,
        releaseDate = input.releaseDate,
        voteAverage = input.voteAverage,
        isFavorite = input.isFavorite,
    )

    fun mapMovieDetailResponseToDomain(input: MovieDetailResponse): MovieDetail {
        return MovieDetail(
            originalLanguage = input.originalLanguage,
            imdbId = input.imdbId,
            video = input.video,
            title = input.title,
            backdropPath = input.backdropPath,
            revenue = input.revenue,
            genres = input.genres?.let {
                it.map { genre ->
                    GenresItem(
                        name = genre?.name ?: "",
                        id = genre?.id ?: 0,
                    )
                }
            } ?: emptyList(),
            popularity = input.popularity,
            productionCountries = input.productionCountries?.let {
                it.map { productionCountriesItem ->
                    ProductionCountriesItem(
                        iso31661 = productionCountriesItem?.iso31661 ?: "",
                        name = productionCountriesItem?.name ?: "",
                    )
                }
            },
            id = input.id,
            voteCount = input.voteCount,
            budget = input.budget,
            overview = input.overview,
            originalTitle = input.originalTitle,
            runtime = input.runtime,
            posterPath = input.posterPath,
            originCountry = input.originCountry,
            spokenLanguages = input.spokenLanguages?.map {
                SpokenLanguagesItem(
                    name = it?.name ?: "",
                    iso6391 = it?.iso6391 ?: "",
                    englishName = it?.englishName ?: "",
                )
            },
            productionCompanies = input.productionCompanies?.let {
                it.map {
                    ProductionCompaniesItem(
                        logoPath = it?.logoPath ?: "",
                        name = it?.name ?: "",
                        id = it?.id ?: 0,
                        originCountry = it?.originCountry ?: "",
                    )
                }
            },
            releaseDate = input.releaseDate,
            voteAverage = input.voteAverage,
            belongsToCollection = input.belongsToCollection?.let {
                BelongsToCollection(
                    backdropPath = it.backdropPath,
                    name = it.name,
                    id = it.id,
                    posterPath = it.posterPath,
                )
            },
            tagline = input.tagline,
            adult = input.adult,
            homepage = input.homepage,
            status = input.status,
        )
    }
}