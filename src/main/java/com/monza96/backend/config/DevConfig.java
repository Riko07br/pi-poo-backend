package com.monza96.backend.config;

import com.monza96.backend.domain.Project;
import com.monza96.backend.domain.ProjectUser;
import com.monza96.backend.domain.Role;
import com.monza96.backend.domain.User;
import com.monza96.backend.domain.enums.Authority;
import com.monza96.backend.repository.ProjectRepository;
import com.monza96.backend.repository.ProjectUserRepository;
import com.monza96.backend.repository.RoleRepository;
import com.monza96.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@Configuration
@Profile("dev")
public class DevConfig implements CommandLineRunner {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectUserRepository projectUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        Role r1 = roleRepository.save(new Role(null, Authority.SUPER_ADMIN, "Super Admin", "Manage the system, has authority over everything"));
        Role r2 = roleRepository.save(new Role(null, Authority.PROJECT_CREATOR, "Project Creator", "Create projects and manage them"));
        Role r3 = roleRepository.save(new Role(null, Authority.PROJECT_ADMIN, "Project Admin", "Has almost the same authority as the creator, but can't delete the project"));
        Role r4 = roleRepository.save(new Role(null, Authority.PROJECT_ORG, "Project Organizer", "TODO - Define the role"));
        Role r5 = roleRepository.save(new Role(null, Authority.PROJECT_USER, "Project User", "TODO - Define the role"));
        Role r6 = roleRepository.save(new Role(null, Authority.PROJECT_VIEWER, "Project Viewer", "TODO - Define the role"));

        String userPassword = new BCryptPasswordEncoder().encode("123456");
        User u1 = userRepository.save(new User(null, "user1@mail.com", userPassword));
        User u2 = userRepository.save(new User(null, "user2@mail.com", userPassword));
        User u3 = userRepository.save(new User(null, "user3@mail.com", userPassword));
        User u4 = userRepository.save(new User(null, "user4@mail.com", userPassword));


        Project p1 = projectRepository.save(new Project(null, "Project 1", "Description 1", null, null));
        //Project p2 = new Project(null, "Project 2", "Description 2");
        //Project p3 = new Project(null, "Project 3", "Description 3");

        //projectRepository.saveAll(Arrays.asList(p1, p2, p3));
        ProjectUser pu1 = new ProjectUser(null, u1, p1, r1);
        ProjectUser pu2 = new ProjectUser(null, u2, p1, r2);
        ProjectUser pu3 = new ProjectUser(null, u3, p1, r5);
        ProjectUser pu4 = new ProjectUser(null, u4, p1, r5);

        projectUserRepository.saveAll(Arrays.asList(pu1, pu2, pu3, pu4));

    }
}
