package io.mcm.kotlinaspireassignment.repository

import io.mcm.kotlinaspireassignment.model.entity.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TeacherRepository : JpaRepository<Teacher, Int>, JpaSpecificationExecutor<Teacher> {
    fun findAllByJoiningDateBetween(joinDateMin: Date, joinDateMax: Date): MutableList<Teacher>
    fun findAllBySalaryBetweenAndAgeBetween(salaryMin: Long, salaryMax: Long, ageMin: Int, ageMax: Int): MutableList<Teacher>
}
