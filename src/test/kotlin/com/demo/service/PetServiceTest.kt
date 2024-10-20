package com.demo.service

import com.demo.entity.Pet
import com.demo.repository.PetRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class PetServiceTest {
    private lateinit var petRepository: PetRepository
    private lateinit var petService: PetService

    @Nested
    inner class MockitoTests {
        @BeforeEach
        fun setUp() {
            petRepository = mock(PetRepository::class.java)
            petService = PetService(petRepository)
        }

        @Test
        fun `getAllPetNames should return list of pet names`() {
            val pets = listOf(Pet(id = "1", name = "Buddy"), Pet(id = "2", name = "Max"))
            `when`(petRepository.findAllPets()).thenReturn(pets)

            val result = petService.getAllPetNames()

            assertEquals(listOf("Buddy", "Max"), result)
            verify(petRepository).findAllPets()
        }

        @Test
        fun `removeAllPets should return true when pets exist`() {
            val pets = listOf(Pet(id = "1", name = "Buddy"))
            `when`(petRepository.findAllPets()).thenReturn(pets)

            val result = petService.removeAllPets()

            assertTrue(result)
            verify(petRepository).findAllPets()
            verify(petRepository).deletePet(pets[0])
        }

        @Test
        fun `getPetNameById should return pet name when pet exists`() {
            val pet = Pet(id = "1", name = "Buddy")
            `when`(petRepository.findPetById("1")).thenReturn(pet)

            val result = petService.getPetNameById("1")

            assertEquals("Buddy", result)
            verify(petRepository).findPetById("1")
        }

        @Test
        fun `getPetNameById should throw IllegalArgumentException when pet does not exist`() {
            `when`(petRepository.findPetById("1")).thenReturn(null)

            val exception =
                assertThrows<IllegalArgumentException> {
                    petService.getPetNameById("1")
                }

            assertEquals(PetService.PET_NOT_FOUND_MESSAGE, exception.message)
            verify(petRepository).findPetById("1")
        }

        @Test
        fun `addNewPet should add pet when pet ID does not exist`() {
            val pet = Pet(id = "1", name = "Buddy")
            `when`(petRepository.findPetById("1")).thenReturn(null)
            `when`(petRepository.addPet(pet)).thenReturn(true)

            val result = petService.addNewPet(pet)

            assertTrue(result)
            verify(petRepository).findPetById("1")
            verify(petRepository).addPet(pet)
        }

        @Test
        fun `getAllPets should return list of pets when pets are available`() {
            val pets = listOf(Pet(id = "1", name = "Buddy"))
            `when`(petRepository.findAllPets()).thenReturn(pets)

            val result = petService.getAllPets()

            assertEquals(pets, result)
            verify(petRepository).findAllPets()
        }

        @Test
        fun `getAllPets should throw IllegalStateException when no pets are available`() {
            `when`(petRepository.findAllPets()).thenReturn(emptyList())

            val exception =
                assertThrows<IllegalStateException> {
                    petService.getAllPets()
                }

            assertEquals("No pets available", exception.message)
            verify(petRepository).findAllPets()
        }

        @Test
        fun `updatePetDetails should update pet when pet exists`() {
            val pet = Pet(id = "1", name = "Buddy")
            val updatedPet = pet.copy(name = "Max")
            `when`(petRepository.findPetById("1")).thenReturn(pet)
            `when`(petRepository.updatePet(updatedPet)).thenReturn(true)

            val result = petService.updatePetDetails("1", "Max")

            assertTrue(result)
            verify(petRepository).findPetById("1")
            verify(petRepository).updatePet(updatedPet)
        }

        @Test
        fun `updatePetDetails should throw IllegalArgumentException when pet does not exist`() {
            `when`(petRepository.findPetById("1")).thenReturn(null)

            val exception =
                assertThrows<IllegalArgumentException> {
                    petService.updatePetDetails("1", "Max")
                }

            assertEquals(PetService.PET_NOT_FOUND_MESSAGE, exception.message)
            verify(petRepository).findPetById("1")
        }

        @Test
        fun `removePetById should remove pet when pet exists`() {
            val pet = Pet(id = "1", name = "Buddy")
            `when`(petRepository.findPetById("1")).thenReturn(pet)
            `when`(petRepository.deletePet(pet)).thenReturn(true)

            val result = petService.removePetById("1")

            assertTrue(result)
            verify(petRepository).findPetById("1")
            verify(petRepository).deletePet(pet)
        }

        @Test
        fun `removePetById should throw IllegalArgumentException when pet does not exist`() {
            `when`(petRepository.findPetById("1")).thenReturn(null)

            val exception =
                assertThrows<IllegalArgumentException> {
                    petService.removePetById("1")
                }

            assertEquals(PetService.PET_NOT_FOUND_MESSAGE, exception.message)
            verify(petRepository).findPetById("1")
        }
    }

    @Nested
    inner class MockKTests {
        @BeforeEach
        fun setUp() {
            petRepository = mockk<PetRepository>()
            petService = PetService(petRepository)
        }

        @Test
        fun `getPetNameById should return pet name when pet is found`() {
            every { petRepository.findPetById("1") } returns Pet("1", "Charlie")

            val petName = petService.getPetNameById("1")

            assertThat(petName).isEqualTo("Charlie")
        }

        @Test
        fun `addNewPet should add new pet successfully`() {
            val pet = Pet("2", "Max")
            every { petRepository.findPetById("2") } returns null
            every { petRepository.addPet(pet) } returns true

            val result = petService.addNewPet(pet)

            assertThat(result).isTrue()
            verify { petRepository.addPet(pet) }
        }

        @Test
        fun `updatePetDetails should update pet details`() {
            val pet = Pet("1", "Buddy")
            val updatedPet = pet.copy(name = "BuddyUpdated")

            every { petRepository.findPetById("1") } returns pet
            every { petRepository.updatePet(updatedPet) } returns true

            val result = petService.updatePetDetails("1", "BuddyUpdated")

            assertThat(result).isTrue()
            verify { petRepository.updatePet(updatedPet) }
        }

        @Test
        fun `removePetById should remove pet by ID`() {
            val pet = Pet("1", "Buddy")

            every { petRepository.findPetById("1") } returns pet
            every { petRepository.deletePet(pet) } returns true

            val result = petService.removePetById("1")

            assertThat(result).isTrue()
            verify { petRepository.deletePet(pet) }
        }

        @Test
        fun `getAllPetNames should get all pet names`() {
            val pets = listOf(Pet("1", "Buddy"), Pet("2", "Max"))
            every { petRepository.findAllPets() } returns pets

            val petNames = petService.getAllPetNames()

            assertThat(petNames).containsExactly("Buddy", "Max")
        }

        @Test
        fun `removeAllPets should remove all pets`() {
            val pets = listOf(Pet("1", "Buddy"), Pet("2", "Max"))
            every { petRepository.findAllPets() } returns pets
            every { petRepository.deletePet(any()) } returns true

            val result = petService.removeAllPets()

            assertThat(result).isTrue()
            verify { petRepository.deletePet(pets[0]) }
            verify { petRepository.deletePet(pets[1]) }
        }

        @Test
        fun `getPetNameById should throw exception when pet is not found`() {
            every { petRepository.findPetById("999") } returns null

            val exception =
                assertThrows<IllegalArgumentException> {
                    petService.getPetNameById("999")
                }

            assertThat(exception.message).isEqualTo("Pet not found")
        }
    }
}
