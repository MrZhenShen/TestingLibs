package com.demo.repository

import com.demo.entity.Pet

interface PetRepository {
    fun findPetById(id: String): Pet?

    fun addPet(pet: Pet): Boolean

    fun findAllPets(): List<Pet>

    fun updatePet(pet: Pet): Boolean

    fun deletePet(pet: Pet): Boolean
}
