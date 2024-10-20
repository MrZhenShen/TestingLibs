package com.demo.service

import com.demo.entity.Pet
import com.demo.repository.PetRepository

class PetService(
    private val petRepository: PetRepository,
) {
    companion object {
        const val PET_NOT_FOUND_MESSAGE = "Pet not found"
    }

    fun getAllPetNames(): List<String> = petRepository.findAllPets().map { it.name }

    fun removeAllPets(): Boolean {
        val pets = petRepository.findAllPets()
        pets.forEach { petRepository.deletePet(it) }
        return pets.isNotEmpty()
    }

    fun getPetNameById(id: String): String {
        val pet = petRepository.findPetById(id) ?: throw IllegalArgumentException(PET_NOT_FOUND_MESSAGE)
        return pet.name
    }

    fun addNewPet(pet: Pet): Boolean {
        if (petRepository.findPetById(pet.id) != null) {
            throw IllegalArgumentException("Pet with the given ID already exists")
        }

        return petRepository.addPet(pet)
    }

    fun getAllPets(): List<Pet> {
        val pets = petRepository.findAllPets()
        if (pets.isEmpty()) {
            throw IllegalStateException("No pets available")
        }

        return pets
    }

    fun updatePetDetails(
        id: String,
        newName: String,
    ): Boolean {
        val pet = petRepository.findPetById(id) ?: throw IllegalArgumentException(PET_NOT_FOUND_MESSAGE)
        val updatedPet = pet.copy(name = newName)
        return petRepository.updatePet(updatedPet)
    }

    fun removePetById(id: String): Boolean {
        val pet = petRepository.findPetById(id) ?: throw IllegalArgumentException(PET_NOT_FOUND_MESSAGE)
        return petRepository.deletePet(pet)
    }
}
