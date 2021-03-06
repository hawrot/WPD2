package com.gcu.wpd2.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document
public class Project {
  @Id
  private ObjectId id;
  private String title;
  private String description;
  private String startDate;
  private String endDate;
  private List<Milestone> milestones;
  private List<ObjectId> sharedWith;

  public Project() {
    this(null, "", "", "", "", new ArrayList<>(), new ArrayList<>());
  }

  public Project(ObjectId id, String name, String description, String starDate, String endDate) {
    this(id, name, description, starDate, endDate, new ArrayList<>(), new ArrayList<>());
  }

  public Project(ObjectId id, String title, String description, String starDate, String endDate, List<Milestone> milestones, List<ObjectId> sharedWith) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.startDate = starDate;
    this.endDate = endDate;
    this.milestones = milestones;
    this.sharedWith = sharedWith;
  }

  public ObjectId getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getStartDate() {
    return startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public List<Milestone> getMilestones() {

    return milestones;
  }
  public void addMilestones(Milestone milestone){
      this.milestones.add(milestone);
  }

  public List<ObjectId> getSharedWith() {
    return sharedWith;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public void setMilestones(List<Milestone> milestones) {
    this.milestones = milestones;
  }

  public void setSharedWith(List<ObjectId> sharedWith) {
    this.sharedWith = sharedWith;
  }

  public void addUserToSharedWith(User user) {
    this.sharedWith.add(user.getId());
  }

  @Override
  public String toString() {
    return "Project{" +
      "_id='" + id + '\'' +
      ", name='" + title + '\'' +
      ", description='" + description + '\'' +
      ", startDate=" + startDate +
      ", endDate=" + endDate +
      ", milestones=" + milestones +
      '}';
  }
}