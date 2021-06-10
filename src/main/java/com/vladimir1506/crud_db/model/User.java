package com.vladimir1506.crud_db.model;

import java.util.List;
import java.util.stream.Collectors;

public class User {
    Long id;
    String firstName;
    String lastName;
    List<Post> posts;
    Region region;
    Role role;

    public User(Long id, String firstName, String lastName, List<Post> posts, Region region, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
        this.region = region;
        this.role = role;
    }


    public Role getRole() {
        return role;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String toString() {
        String writePosts = null;
        String regionName = null;
        if (this.posts != null) {
            List<Long> posts = this.posts.stream().map(Post::getId).collect(Collectors.toList());
            writePosts = posts.toString().replaceAll(" ", "");
        }
        if (this.region != null) {
            regionName = region.getName();
        }
        return "\n" + id + ". " + firstName + ", " + lastName + ", " + writePosts + ", " + regionName + ", " + role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
