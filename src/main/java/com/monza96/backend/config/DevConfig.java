package com.monza96.backend.config;

import com.monza96.backend.domain.Classification;
import com.monza96.backend.domain.Objective;
import com.monza96.backend.domain.Project;
import com.monza96.backend.domain.ProjectUser;
import com.monza96.backend.domain.Role;
import com.monza96.backend.domain.Task;
import com.monza96.backend.domain.User;
import com.monza96.backend.domain.enums.ProjectAuthority;
import com.monza96.backend.repository.ClassificationRepository;
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
    private ClassificationRepository classificationRepository;
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
        System.out.println("Roles created------------------------------------");
        String userPassword = new BCryptPasswordEncoder().encode("123456");
        User u1 = userRepository.save(new User(null, "user1@mail.com", userPassword));
        User u2 = userRepository.save(new User(null, "user2@mail.com", userPassword));
        User u3 = userRepository.save(new User(null, "user3@mail.com", userPassword));
        User u4 = userRepository.save(new User(null, "user4@mail.com", userPassword));
        User u5 = userRepository.save(new User(null, "user5@mail.com", userPassword));
        User u6 = userRepository.save(new User(null, "user6@mail.com", userPassword));
        System.out.println("Users created------------------------------------");

        System.out.println("Project1: start creation------------------------------------");
        Project project1 = projectRepository.save(new Project(null, "Project 1", "Some tasks", null, null));

        System.out.println("Project1: add users------------------------------------");
        ProjectUser project1u2 = projectUserRepository.save(new ProjectUser(null, u2, project1, r2));
        ProjectUser project1u3 = projectUserRepository.save(new ProjectUser(null, u3, project1, r4));
        ProjectUser project1u4 = projectUserRepository.save(new ProjectUser(null, u4, project1, r5));

        System.out.println("Project1: add classifications------------------------------------");
        Classification project1class1 = classificationRepository.save(new Classification(null, "Pool", project1));
        Classification project1class2 = classificationRepository.save(new Classification(null, "Started", project1));
        Classification project1class3 = classificationRepository.save(new Classification(null, "Done", project1));

        System.out.println("Project1: add task1 with users------------------------------------");
        Task project1task1 = new Task(null,
                "Get addicted to heavy metal",
                "This task involves immersing oneself in the world of heavy metal music. " +
                        "It includes listening to a variety of heavy metal sub-genres, understanding " +
                        "the history and evolution of heavy metal, and appreciating the unique musical " +
                        "and lyrical elements that define this genre.",
                null,
                project1);

        project1task1.getProjectUsers().add(project1u2);
        project1task1.getProjectUsers().add(project1u3);

        project1task1.getClassifications().add(project1class1);
        project1task1.getClassifications().add(project1class2);
        project1task1.getClassifications().add(project1class3);
        project1task1 = taskRepository.save(project1task1);

        System.out.println("Project1: add objectives to task1------------------------------------");
        Objective project1task1o1 = new Objective(null,
                "Listen to Iron Maiden",
                "Listen to Iron Maiden's discography",
                project1task1,
                project1class1);
        Objective project1task1o2 = new Objective(null,
                "Listen to Metallica",
                "Listen to Metallica's discography until 2000",
                project1task1,
                project1class1);
        Objective project1task1o3 = new Objective(null,
                "Listen to Calcinha preta",
                "Listen to relax",
                project1task1,
                project1class2);
        objectiveRepository.saveAll(Arrays.asList(project1task1o1, project1task1o2, project1task1o3));

        System.out.println("Project1: add task2 with users------------------------------------");
        Task project1task2 = new Task(null, "Do nothing", "Do nothing all day", null, project1);
        project1task2.getProjectUsers().add(project1u2);
        project1task2.getProjectUsers().add(project1u4);
        taskRepository.save(project1task2);

        System.out.println("Project1: add objectives to task2------------------------------------");
        Objective project1task2o1 = new Objective(null, "Sleep", "Sleep all day", project1task2, project1class1);
        Objective project1task2o2 = new Objective(null, "Eat", "Eat all day", project1task2, project1class2);
        objectiveRepository.saveAll(Arrays.asList(project1task2o1, project1task2o2));

        System.out.println("Project1: add task3 with users------------------------------------");
        Task project1task3 = new Task(null, "Task 3", "Description 3", null, project1);
        project1task3.getProjectUsers().add(project1u4);
        taskRepository.saveAll(Arrays.asList(project1task1, project1task2, project1task3));
        System.out.println("Project 1 created------------------------------------");


        Project project2 = projectRepository.save(new Project(null, "Project 2", "No tasks", null, null));
        ProjectUser project2u1 = new ProjectUser(null, u1, project2, r2);
        ProjectUser project2u3 = new ProjectUser(null, u3, project2, r3);
        ProjectUser project2u4 = new ProjectUser(null, u4, project2, r5);
        ProjectUser project2u5 = new ProjectUser(null, u5, project2, r6);
        projectUserRepository.saveAll(Arrays.asList(project2u1, project2u3, project2u4, project2u5));
        System.out.println("Project 2 created------------------------------------");
    }
}
