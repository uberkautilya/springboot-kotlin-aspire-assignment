package io.mcm.kotlinaspireassignment.security.authorization.domain

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.*

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0

    var name: String = ""

    @ManyToMany(mappedBy = "roles")
    val users: Set<User> = mutableSetOf()

    @ManyToMany(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinTable(name= "role_authority",
        joinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")],
        inverseJoinColumns = [JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID")])
    var authorities: Set<Authority> = mutableSetOf()
}