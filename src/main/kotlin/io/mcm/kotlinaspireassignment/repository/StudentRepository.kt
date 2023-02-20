package io.mcm.kotlinaspireassignment.repository

import io.mcm.kotlinaspireassignment.model.entity.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface StudentRepository: JpaRepository<Student, Int>, JpaSpecificationExecutor<Student> {
}
