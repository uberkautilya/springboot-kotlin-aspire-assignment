package io.mcm.kotlinaspireassignment.security.springguru

import io.mcm.kotlinaspireassignment.security.authorization.domain.Authority
import io.mcm.kotlinaspireassignment.security.authorization.domain.Role
import io.mcm.kotlinaspireassignment.security.authorization.domain.User
import io.mcm.kotlinaspireassignment.security.springguru.repository.AuthorityRepository
import io.mcm.kotlinaspireassignment.security.springguru.repository.RoleRepository
import io.mcm.kotlinaspireassignment.security.springguru.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserDataLoader : CommandLineRunner {
    @Autowired
    private lateinit var authorityRepository: AuthorityRepository
    @Autowired
    private lateinit var roleRepository: RoleRepository
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    override fun run(vararg args: String?) {
        saveSecurityDetails()
    }

    private fun saveSecurityDetails() {
        val readPrivate = authorityRepository.save(Authority().apply { this.permission = "private.read" })
        val readPublic = authorityRepository.save(Authority().apply { this.permission = "public.read" })

        val readCourse = authorityRepository.save(Authority().apply { this.permission = "course.read" })
        val createCourse = authorityRepository.save(Authority().apply { this.permission = "course.create" })
        val updateCourse = authorityRepository.save(Authority().apply { this.permission = "course.update" })
        val deleteCourse = authorityRepository.save(Authority().apply { this.permission = "course.delete" })

        val readStudent = authorityRepository.save(Authority().apply { this.permission = "student.read" })
        val createStudent = authorityRepository.save(Authority().apply { this.permission = "student.create" })
        val updateStudent = authorityRepository.save(Authority().apply { this.permission = "student.update" })
        val deleteStudent = authorityRepository.save(Authority().apply { this.permission = "student.delete" })

        val readTeacher = authorityRepository.save(Authority().apply { this.permission = "teacher.read" })
        val createTeacher = authorityRepository.save(Authority().apply { this.permission = "teacher.create" })
        val updateTeacher = authorityRepository.save(Authority().apply { this.permission = "teacher.update" })
        val deleteTeacher = authorityRepository.save(Authority().apply { this.permission = "teacher.delete" })

        val readDepartment = authorityRepository.save(Authority().apply { this.permission = "department.read" })
        val createDepartment = authorityRepository.save(Authority().apply { this.permission = "department.create" })
        val updateDepartment = authorityRepository.save(Authority().apply { this.permission = "department.update" })
        val deleteDepartment = authorityRepository.save(Authority().apply { this.permission = "department.delete" })

        val adminRole = Role().apply {
            this.name = "ROLE_admin"; this.authorities = mutableSetOf(
            readPrivate, readPublic,
            readCourse, createCourse, updateCourse, deleteCourse,
            readStudent, createStudent, updateStudent, deleteStudent,
            readTeacher, createTeacher, updateTeacher, deleteTeacher,
            readDepartment, createDepartment, updateDepartment, deleteDepartment
        )
        }
        val adminRolePersisted = roleRepository.save(adminRole)
        val readOnlyRole = Role().apply {
            //Roles by convention begin with 'ROLE_', while authorities can be any string
            this.name = "ROLE_readonly"; this.authorities = mutableSetOf(
            readPrivate, readPublic,
            readCourse, readStudent, readTeacher, readDepartment
        )
        }
        val readOnlyRolePersisted = roleRepository.save(readOnlyRole)
        val publicRole = Role().apply {
            this.name = "ROLE_public"; this.authorities = mutableSetOf(
            readPublic
        )
        }
        val publicRolePersisted = roleRepository.save(publicRole)
        userRepository.save(
            User().apply {
                this.username = "uberkautilya"; this.setPassword(passwordEncoder.encode("admin"));this.roles =
                mutableSetOf(adminRolePersisted)
            }
        )
        userRepository.save(
            User().apply {
                this.username = "readonly"; this.setPassword(passwordEncoder.encode("readonly"));this.roles =
                mutableSetOf(readOnlyRolePersisted)
            }
        )
        userRepository.save(
            User().apply {
                this.username = "public"; this.setPassword(passwordEncoder.encode("password")); this.roles =
                mutableSetOf(publicRolePersisted)
            }
        )
        println("{userRepository.findAll()}: ${userRepository.findAll()}")
    }
}