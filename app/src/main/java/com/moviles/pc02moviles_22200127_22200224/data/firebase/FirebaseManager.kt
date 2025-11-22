package com.moviles.pc02moviles_22200127_22200224.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.moviles.pc02moviles_22200127_22200224.data.model.TeamModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseManager {

    private val db = FirebaseFirestore.getInstance()
    private val teamsCollection = db.collection("equipos")

    // Guardar equipo
    suspend fun saveTeam(team: TeamModel): Result<Unit> {
        return try {
            teamsCollection.add(team.toMap()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Obtener equipos en tiempo real
    fun getTeams(): Flow<List<TeamModel>> = callbackFlow {
        val listener = teamsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val teams = snapshot?.documents?.map { doc ->
                TeamModel(
                    id = doc.id,
                    nombreEquipo = doc.getString("nombreEquipo") ?: "",
                    anioFundacion = doc.getString("anioFundacion") ?: "",
                    nombreEstadio = doc.getString("nombreEstadio") ?: "",
                    urlImagenEquipo = doc.getString("urlImagenEquipo") ?: ""
                )
            } ?: emptyList()

            trySend(teams)
        }

        awaitClose { listener.remove() }
    }
}