package io.mcm.kotlinaspireassignment.repository

import io.mcm.kotlinaspireassignment.model.entity.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.Date

@Repository
interface TeacherRepository: JpaRepository<Teacher, Int>, JpaSpecificationExecutor<Teacher> {
    fun findAllBySalaryAndJoiningDateBetween(salary: Long, joinDateMin: Date, joinDateMax: Date): MutableList<Teacher>
}
