package com.chaliez.alma.test.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.PrimaryKey
import androidx.room.Query
import com.chaliez.alma.test.data.model.MarvelHero
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "marvel_heroes")
data class MarvelHeroRow(
    @PrimaryKey
    val uid: Int,
    val name: String,
    val description: String = "",
    val thumbnail: String = "",
    @ColumnInfo(name = "is_part_of_squad")
    val isPartOfSquad: Boolean = false
)

/**
 * Map MarvelHeroRow to domain entities
 */
fun List<MarvelHeroRow>.asDomainModel(): List<MarvelHero> =
    map { it.asDomainModel() }

fun MarvelHeroRow.asDomainModel(): MarvelHero =
    MarvelHero(
        id = this.uid,
        name = this.name,
        description = this.description,
        thumbnail = this.thumbnail,
        isPartOfSquad = this.isPartOfSquad
    )

/**
 * Map domain entities to MarvelHeroRow
 */
fun List<MarvelHero>.asDatabaseModel(): List<MarvelHeroRow> =
    map { it.asDatabaseModel() }

fun MarvelHero.asDatabaseModel(): MarvelHeroRow =
    MarvelHeroRow(
        uid = this.id,
        name = this.name,
        description = this.description,
        thumbnail = this.thumbnail,
        isPartOfSquad = this.isPartOfSquad
    )

@Dao
interface MarvelHeroDao {

    @Query("SELECT * FROM marvel_heroes ORDER BY uid DESC LIMIT 10")
    fun getMarvelHeroes(): Flow<List<MarvelHeroRow>>

    @Query("SELECT * FROM marvel_heroes WHERE uid=:uid")
    fun getMarvelHero(uid: Int): Flow<MarvelHeroRow>

    @Query("SELECT * FROM marvel_heroes WHERE is_part_of_squad=1")
    fun getMarvelHeroesSquad(): Flow<List<MarvelHeroRow>>

    @Insert(onConflict = REPLACE)
    suspend fun insertMarvelHero(item: MarvelHeroRow)

    @Query("UPDATE marvel_heroes SET is_part_of_squad=:isPartOfSquad WHERE uid=:uid")
    suspend fun updateIsPartOfSquad(uid: Int, isPartOfSquad: Boolean)

    @Delete
    suspend fun delete(item: MarvelHeroRow)

    @Query("DELETE FROM marvel_heroes")
    suspend fun deleteAll()
}
