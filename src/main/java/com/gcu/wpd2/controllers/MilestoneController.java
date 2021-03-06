package com.gcu.wpd2.controllers;

import com.gcu.wpd2.models.Milestone;
import com.gcu.wpd2.models.Project;
import com.gcu.wpd2.models.User;
import com.gcu.wpd2.services.MilestoneService;
import com.gcu.wpd2.services.ProjectService;
import com.gcu.wpd2.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class MilestoneController {
    @Autowired
    private MilestoneService milestoneService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/milestone/view/{milestoneId}", method = RequestMethod.GET)
    public ModelAndView getProjectDetailsPage(@PathVariable ObjectId milestoneId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("milestone",milestoneService.getByID(milestoneId));
        modelAndView.addObject("currentUser", getCurrentUser());
        modelAndView.setViewName("project/view");
        return  modelAndView;
    }
    @RequestMapping(value = "/project/view/{projectId}/milestone/create", method = RequestMethod.GET)
    public ModelAndView getCreateMilestonePage(@PathVariable ObjectId projectId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("projectId", projectId);
        modelAndView.addObject(new Milestone());
        modelAndView.addObject("currentUser", getCurrentUser());
        modelAndView.setViewName("milestone/create");
        return modelAndView;
    }

    @RequestMapping(value = "/project/view/{projectId}/milestone", method = RequestMethod.POST)
    public ModelAndView createMilestone(@Valid Milestone milestone, @PathVariable ObjectId projectId){
        ModelAndView modelAndView = new ModelAndView();
        Project project = projectService.getById(projectId);
        milestoneService.saveToProject(milestone, project);
        modelAndView.addObject("milestoneCreated", true);
        modelAndView.addObject("currentUser", getCurrentUser());
        modelAndView.setViewName("redirect:/project/view/" + projectId);
        return modelAndView;
    }
    @RequestMapping(value = "/project/view/{projectId}/milestone/delete/{milestoneId}", method = RequestMethod.GET)
    public ModelAndView getDeleteMilestonePage(@PathVariable ObjectId projectId, @PathVariable ObjectId milestoneId){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("milestoneIdentifier", milestoneId);
        modelAndView.addObject("projectIdentifier", projectId);
        modelAndView.addObject("milestoneTitle", milestoneService.getByID(milestoneId).getTitle());
        modelAndView.addObject("currentUser", getCurrentUser());
        modelAndView.setViewName("milestone/delete");
        return modelAndView;
    }

    @RequestMapping(value = "/project/view/{projectId}/milestone/delete/{milestoneId}", method = RequestMethod.DELETE)
    public ModelAndView deleteMilestone(@PathVariable ObjectId projectId, @PathVariable ObjectId milestoneId){
        ModelAndView modelAndView = new ModelAndView();
        Milestone milestone = milestoneService.getByID(milestoneId);
        milestoneService.deleteMilestoneFromProject(milestone, projectId);
        milestoneService.delete(milestone);
        modelAndView.addObject("milestoneDeleted", true);
        modelAndView.addObject("currentUser", getCurrentUser());
        modelAndView.setViewName("redirect:/project/view/" + projectId);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/project/view/{projectId}/milestone/edit/{milestoneId}", method = RequestMethod.GET)
    public boolean updateMilestoneStatus(@PathVariable ObjectId projectId, @PathVariable ObjectId milestoneId){
        milestoneService.updateProjectMilestoneStatus(projectId, milestoneId);
        return true;
    }

    User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByEmail(auth.getName());
    }
}
