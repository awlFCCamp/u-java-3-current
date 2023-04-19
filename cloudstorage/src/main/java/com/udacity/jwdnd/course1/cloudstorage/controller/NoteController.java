package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notes")
@AllArgsConstructor
public class NoteController {
    private NoteService noteService;
    private UserService userService;

    @GetMapping
    public String getAllUserIdNotes(Model model, Authentication authentication){
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        model.addAttribute("allNotes", noteService.getAllUserIdNotes(user.getUserId()));
        return "home";

    }

    @PostMapping
    public String createUpdateNote(Note note, Model model, Authentication authentication, RedirectAttributes redirectAttributes){
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        boolean isNoteNotCreated = note.getNoteId() == null;

        if (isNoteNotCreated) {
            if(note.getNoteDescription().length()>1000){
                String error="";
                error = "Your note description size exceeds the limit. Can't be stored";
                model.addAttribute("noteError", error);
                redirectAttributes.addFlashAttribute("noteError", error);
                return "redirect:/error";
            }else {
                noteService.createNote(note, user.getUserId());
            }
        } else {
            if(note.getNoteDescription().length()>1000){
                String error="";
                error = "Your note description size exceeds the limit. Can't be stored";
                model.addAttribute("noteError", error);
                redirectAttributes.addFlashAttribute("noteError", error);
                return "redirect:/error";}
            else {
                noteService.updateNote(note);
            }
        }

        model.addAttribute("result", "success");
        return "result";
    }


    @GetMapping("/delete")
    public String deleteNote(@RequestParam Long noteId,Model model){
        try {
            noteService.deleteNote(noteId);
            model.addAttribute("result", "success");
            return "result";
        }catch(Exception ex){
            model.addAttribute("result", "error");
            model.addAttribute("message", "exception occurred");
            return "result";
        }
    }
}
