package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/qnas")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("qnas", questionRepository.findAll());
        return "index";
    }

    @PostMapping
    public String create(Question question, HttpSession session) {
        if (!SessionUtil.checkLogin(session)) {
            return "/user/login";
        }
        question.setWriter(SessionUtil.getUser(session));
        questionRepository.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("qna", questionRepository.findById(id).get());
        return "/qna/show";
    }

    @GetMapping("/form")
    public String showForm(Model model, HttpSession session) {
        if (!SessionUtil.checkLogin(session)) {
            return "/user/login";
        }
        model.addAttribute("user", SessionUtil.getUser(session));
        return "/qna/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable("id") Question question, Model model, HttpSession session) {
        if (!SessionUtil.checkLogin(session)) {
            return "/user/login";
        }

        if (!question.matchWriter(SessionUtil.getUserId(session))) {
            return "/qna/update_failed";
        }

        model.addAttribute("qna", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question question) {
        Question updatedQuestion = questionRepository.findById(id).get().updateQuestion(question);
        questionRepository.save(updatedQuestion);
        return "redirect:/qnas/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!SessionUtil.checkLogin(session)) {
            return "/user/login";
        }

        Question question = questionRepository.findById(id).get();
        if (!question.matchWriter(SessionUtil.getUserId(session))) {
            return "/user/login";
        }

        questionRepository.deleteById(id);
        return "redirect:/qnas";
    }

}
