package com.kgisl.JpaOneToMany;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MainApp {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("examplePU");
        EntityManager em = emf.createEntityManager();

        try {
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            // Create some sample users and posts
            User user1 = new User();
            user1.setName("John Doe");

            User user2 = new User();
            user2.setName("Jane Smith");

            Post post1 = new Post();
            post1.setTitle("First Post");
            post1.setUser(user1);

            Post post2 = new Post();
            post2.setTitle("Second Post");
            post2.setUser(user1);

            Post post3 = new Post();
            post3.setTitle("Third Post");
            post3.setUser(user2);

            List<Post> posts1 = new ArrayList<>();
            posts1.add(post1);
            posts1.add(post2);
            user1.setPosts(posts1);

            List<Post> posts2 = new ArrayList<>();
            posts2.add(post3);
            user2.setPosts(posts2);

            // Persist the objects
            em.persist(user1);
            em.persist(user2);
            em.persist(post1);
            em.persist(post2);
            em.persist(post3);

            tx.commit();

            // Retrieve a user and their associated posts
            User retrievedUser = em.find(User.class, 1L);
            if (retrievedUser != null) {
                System.out.println("User: " + retrievedUser.getName());
                for (Post post : retrievedUser.getPosts()) {
                    System.out.println("Post: " + post.getTitle());
                }
            }
        } finally {
            em.close();
            emf.close();
        }
    }
}
