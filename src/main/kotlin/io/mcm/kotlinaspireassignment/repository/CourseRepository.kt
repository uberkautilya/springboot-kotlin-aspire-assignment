package io.mcm.kotlinaspireassignment.repository

import io.mcm.kotlinaspireassignment.model.entity.Course
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface CourseRepository: JpaRepository<Course, Int>, JpaSpecificationExecutor<Course> {
    override fun findAll(specification: Specification<Course>?): MutableList<Course>
    override fun findAll(spec: Specification<Course>?, pageable: Pageable): Page<Course>
}
