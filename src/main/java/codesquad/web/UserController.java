package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (!maybeUser.isPresent()) {
            return "/user/login_failed";
        }

        User user = maybeUser.get();
        if (! user.matchPassword(password)) {
            return "/user/login_failed";
        }

        SessionUtil.setUser(session, user);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/profile";
    }

    @PostMapping
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User user, HttpSession session) {
        if (!SessionUtil.checkLogin(session)) {
            return "/user/login";
        }

        if(!SessionUtil.checkLoginUser(session, user)) {
            return "/user/update_failed";
        }

        User dbUser =  userRepository.findById(id).get();
        userRepository.save(dbUser.updateUser(user));
        return "redirect:/users";
    }
}
