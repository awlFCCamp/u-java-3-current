package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/signup")
@AllArgsConstructor
public class SignupController {
    private final UserService userService;

    @GetMapping
    public String signupPage() {
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        String error = "";

        if (!userService.isUsernameAvailable(user.getUsername())) {
            error = "This username is already in our system.";
            model.addAttribute("signupError", error);
            redirectAttributes.addFlashAttribute("signupError", error);
            return "redirect:/signup";
        } else {
            try {
                userService.createUser(user);
                redirectAttributes.addFlashAttribute("signupSuccess", true);
                return "redirect:/login";
            } catch (Exception e) {
                error = "Signup exception occurred";
                System.out.println("There is error in the information you entered, try signup again or login");
                model.addAttribute("signupError", error);
                redirectAttributes.addFlashAttribute("signupError", error);
                return "redirect:/signup";
            }
        }

        }

    }
