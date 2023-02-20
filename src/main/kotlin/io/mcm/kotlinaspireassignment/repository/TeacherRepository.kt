package io.mcm.kotlinaspireassignment.repository

import io.mcm.kotlinaspireassignment.model.entity.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface TeacherRepository: JpaRepository<Teacher, Int>, JpaSpecificationExecutor<Teacher> {

}
