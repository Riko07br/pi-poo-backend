package com.monza96.backend.config;

import com.monza96.backend.domain.Objective;
import com.monza96.backend.domain.Project;
import com.monza96.backend.domain.ProjectUser;
import com.monza96.backend.domain.Role;
import com.monza96.backend.domain.Task;
import com.monza96.backend.domain.User;
import com.monza96.backend.domain.enums.ProjectAuthority;
import com.monza96.backend.repository.ObjectiveRepository;
import com.monza96.backend.repository.ProjectRepository;
import com.monza96.backend.repository.ProjectUserRepository;
import com.monza96.backend.repository.RoleRepository;
import com.monza96.backend.repository.TaskRepository;
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
    private ObjectiveRepository objectiveRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectUserRepository projectUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        //ADMIN and USER are different authorities than "ProjectAuthority" ones
        //Role r1 = roleRepository.save(new Role(null, ProjectAuthority.SUPER_ADMIN, "Super Admin", "Manage the system, has authority over everything"));
        Role r2 = roleRepository.save(new Role(null, ProjectAuthority.CREATOR, "Project Creator", "Create projects and manage them"));
        Role r3 = roleRepository.save(new Role(null, ProjectAuthority.ADMIN, "Project Admin", "Has almost the same authority as the creator, but can't delete the project"));
        Role r4 = roleRepository.save(new Role(null, ProjectAuthority.ORGANIZER, "Project Organizer", "TODO - Define the role"));
        Role r5 = roleRepository.save(new Role(null, ProjectAuthority.USER, "Project User", "TODO - Define the role"));
        Role r6 = roleRepository.save(new Role(null, ProjectAuthority.VIEWER, "Project Viewer", "TODO - Define the role"));

        String userPassword = new BCryptPasswordEncoder().encode("123456");
        User u1 = userRepository.save(new User(null, "user1@mail.com", userPassword));
        User u2 = userRepository.save(new User(null, "user2@mail.com", userPassword));
        User u3 = userRepository.save(new User(null, "user3@mail.com", userPassword));
        User u4 = userRepository.save(new User(null, "user4@mail.com", userPassword));
        User u5 = userRepository.save(new User(null, "user5@mail.com", userPassword));
        User u6 = userRepository.save(new User(null, "user6@mail.com", userPassword));

        Project p1 = projectRepository.save(new Project(null, "Project 1", "Some tasks", null, null));
        ProjectUser p1u2 = projectUserRepository.save(new ProjectUser(null, u2, p1, r2));
        ProjectUser p1u3 = projectUserRepository.save(new ProjectUser(null, u3, p1, r4));
        ProjectUser p1u4 = projectUserRepository.save(new ProjectUser(null, u4, p1, r5));

        Task p1t1 = new Task(null,
                "Get addicted to heavy metal",
                "This task involves immersing oneself in the world of heavy metal music. " +
                        "It includes listening to a variety of heavy metal sub-genres, understanding " +
                        "the history and evolution of heavy metal, and appreciating the unique musical " +
                        "and lyrical elements that define this genre.",
                null,
                p1);

        p1t1.getProjectUsers().add(p1u2);
        p1t1.getProjectUsers().add(p1u3);
        p1t1 = taskRepository.save(p1t1);

        Objective p1t1o1 = new Objective(null, "Listen to Iron Maiden", "Listen to Iron Maiden's discography", p1t1);
        Objective p1t1o2 = new Objective(null, "Listen to Metallica", "Listen to Metallica's discography until 2000", p1t1);
        Objective p1t1o3 = new Objective(null, "Listen to Calcinha preta", "Listen to relax", p1t1);
        objectiveRepository.saveAll(Arrays.asList(p1t1o1, p1t1o2, p1t1o3));

        Task p1t2 = new Task(null, "Do nothing", "Do nothing all day", null, p1);
        p1t2.getProjectUsers().add(p1u2);
        p1t2.getProjectUsers().add(p1u4);
        taskRepository.save(p1t2);

        Objective p1t2o1 = new Objective(null, "Sleep", "Sleep all day", p1t2);
        Objective p1t2o2 = new Objective(null, "Eat", "Eat all day", p1t2);
        objectiveRepository.saveAll(Arrays.asList(p1t2o1, p1t2o2));

        Task p1t3 = new Task(null, "Task 3", "Description 3", null, p1);
        p1t3.getProjectUsers().add(p1u4);
        taskRepository.saveAll(Arrays.asList(p1t1, p1t2, p1t3));

        Project p2 = projectRepository.save(new Project(null, "Project 2", "No tasks", null, null));
        ProjectUser p2u1 = new ProjectUser(null, u1, p2, r2);
        ProjectUser p2u3 = new ProjectUser(null, u3, p2, r3);
        ProjectUser p2u4 = new ProjectUser(null, u4, p2, r5);
        ProjectUser p2u5 = new ProjectUser(null, u5, p2, r6);
        projectUserRepository.saveAll(Arrays.asList(p2u1, p2u3, p2u4, p2u5));

    }
}
