package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    private List<Question> questionList = new ArrayList<>();

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("qnas", questionList);
        return "/qna/index";
    }

    @PostMapping("/qnas")
    public String create(Question question) {
        questionList.add(question);
        return "redirect:/";
    }

    @GetMapping("/qnas/{index}")
    public String show(@PathVariable int index, Model model) {
        model.addAttribute("qna", questionList.get(index));
        return "/qna/show";
    }

}
