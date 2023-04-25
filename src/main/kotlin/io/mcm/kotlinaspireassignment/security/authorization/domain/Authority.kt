package io.mcm.kotlinaspireassignment.security.authorization.domain

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int = 0

    @Builder.Default
    var permission: String = ""

    @ManyToMany(mappedBy = "authorities", cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    val roles: Set<Role> = mutableSetOf()
}